/*
 * Created on 1 oct. 2004
 */
package fr.umlv.ir3.corba.forum;

import java.io.*;
import java.util.Date;

import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CORBA.UnknownUserException;
import org.omg.CosNaming.*;

/**
 * @author jguers
 */
public class ForumClient {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        java.util.Properties props = System.getProperties();
        // props.put( "org.omg.CORBA.ORBClass", "org.openorb.CORBA.ORB" );
        // props.put("org.omg.CORBA.ORBSingletonClass","org.openorb.CORBA.ORBSingleton" );
        // props.put( "verbose", "5" );
        props.put("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.client.ClientORBInitializer", "");

        ORB orb = ORB.init(args, props);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // Création du service d'annuaire
        Object ns = orb.resolve_initial_references("NameService");
        NamingContextExt nc = NamingContextExtHelper.narrow(ns);

        NameComponent[] name = null;
        try {
            name = nc.to_name("jmdsForum");
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
            e2.printStackTrace();
        }

        Object objProxy = nc.resolve(name);

        Forum forumProxy = ForumHelper.narrow(objProxy);

        while (true) {
            System.out.println("Que voulez-vous faire ?");
            System.out.println("a - ajouter un message");
            System.out.println("v - voir les messages");
            System.out.println("l - lire un message");
            System.out.println("r - retirer un message");

            int choix = System.in.read();
            in.readLine();
            switch (choix) {
            case 'a': {
                System.out
                        .println("Quel type de message voulez-vous poster ?\n1: Texte\n2: Audio");
                int type = Integer.parseInt(in.readLine());
                System.out.print("Votre nom: ");
                String auth = in.readLine();
                System.out.print("Titre du message: ");
                String title = in.readLine();
                System.out.print("Message: ");
                String mess = in.readLine();

                Any any = orb.create_any();

                if (type == 1) {
                    TextMessage message = new TextMessage(title, auth,
                            (new Date()).toString(), mess);
                    TextMessageHelper.insert(any, message);
                } else {
                    System.out.print("Parole: ");
                    String paroles = in.readLine();
                    AudioMessage message = new AudioMessage(title, auth,
                            (new Date()).toString(), mess, paroles);
                    AudioMessageHelper.insert(any, message);
                }
                try {
                    forumProxy.postMessage(title, any);
                } catch (Reject e) {
                    System.out
                            .println("Message non ajouté pour cette raison:\n"
                                    + e.message);
                }

            }
                break;

            case 'l': {
                System.out.print("Titre du message: ");
                String title = in.readLine();

                Any lany;
                /*
                 * V1 try { lany = forumProxy.getMessage(title);
                 * 
                 * TypeCode tc = lany.type();
                 * 
                 * if(tc.equal(AudioMessageHelper.type())) { AudioMessage m =
                 * AudioMessageHelper.extract(lany); System.out.println("Message
                 * Audio"); System.out.println(m.author + " a écrit à " +
                 * m.date.toString()); System.out.println("Titre: " + m.title);
                 * System.out.println(m.body); System.out.println(m.paroles +
                 * "\n"); } else { TextMessage m =
                 * TextMessageHelper.extract(lany); System.out.println("Message
                 * Texte"); System.out.println(m.author + " a écrit à " +
                 * m.date.toString()); System.out.println("Titre: " + m.title);
                 * System.out.println(m.body + "\n"); } } catch (Reject e) {
                 * System.out.println("Message non trouvé pour cette raison:\n" +
                 * e.message); }
                 */

                // V2
                // Construction d'un objet requête vide
                Request request = forumProxy._request("getMessage");
                // Spécification du type de retour de la requête
                request.set_return_type(orb.get_primitive_tc(TCKind.tk_any));
                // Spécification de l'argument
                Any arg = request.add_named_in_arg("title");
                arg.insert_string(title);
                // Spécification du type de l'exception
                ExceptionList exceptionList = request.exceptions();
                exceptionList.add(RejectHelper.type());
                // Appel synchrone de la requête
                request.invoke();

                // Récupération de l'environnement
                Environment env = request.env();
                // Si pas d'exception
                if (env.exception() == null) {
                    // Récupération du résultat
                    lany = request.return_value().extract_any();
                    TypeCode tc = lany.type();

                    if (tc.equal(AudioMessageHelper.type())) {
                        AudioMessage m = AudioMessageHelper.extract(lany);
                        System.out.println("Message Audio");
                        System.out.println(m.author + " a écrit à "
                                + m.date.toString());
                        System.out.println("Titre: " + m.title);
                        System.out.println(m.body);
                        System.out.println(m.paroles + "\n");
                    } else {
                        TextMessage m = TextMessageHelper.extract(lany);
                        System.out.println("Message Texte");
                        System.out.println(m.author + " a écrit à "
                                + m.date.toString());
                        System.out.println("Titre: " + m.title);
                        System.out.println(m.body + "\n");
                    }
                } else {
                    // Extraction des exceptions
                    Any ex = ((UnknownUserException) env.exception()).except;
                    if (ex.type().equals(RejectHelper.type())) {
                        System.out
                                .println("Message non trouvé pour cette raison:\n"
                                        + RejectHelper.extract(ex).message);
                    } else {
                        throw env.exception();
                    }
                }
            }
                break;

            case 'r': {
                System.out.print("Titre du message: ");
                String title = in.readLine();
                try {
                    forumProxy.removeMessage(title);
                } catch (Reject e) {
                    System.out
                            .println("Message non retiré pour cette raison:\n"
                                    + e.message);
                }
            }
                break;

            case 'v': {
                Any[] tab = forumProxy.getMessages();

                TypeCode tc = null;

                for (int i = 0; i < tab.length; i++) {
                    tc = tab[i].type();

                    if (tc.equal(AudioMessageHelper.type())) {
                        AudioMessage m = AudioMessageHelper.extract(tab[i]);
                        System.out.println("Message audio: " + m.author + "\t"
                                + m.date + "\t" + m.title + "\n");
                    } else {
                        TextMessage m = TextMessageHelper.extract(tab[i]);
                        System.out.println("Message texte: " + m.author + "\t"
                                + m.date + "\t" + m.title + "\n");
                    }
                }
            }
            }
        }
    }
}
