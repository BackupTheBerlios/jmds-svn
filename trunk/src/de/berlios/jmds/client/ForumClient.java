/*
 * Created on 1 oct. 2004
 */
package de.berlios.jmds.client;

import java.io.*;
import java.util.Date;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.umlv.ir3.corba.sguincha.td2.Forum;
import fr.umlv.ir3.corba.sguincha.td2.ForumHelper;
import fr.umlv.ir3.corba.sguincha.td2.Message;


/**
 * @author jguers
 */
public class ForumClient
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException
	{

		/* initialisation de l'ORB */
		Properties prop = new Properties ();
		prop.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.client.core.ClientORBInitializer" , "");
		ORB orb = ORB.init (args , prop);
		/* Récupération de l'id de l'objet servant */
		BufferedReader buffer = new BufferedReader (new FileReader ("ObjectRef"));
		String ior = buffer.readLine ();

		BufferedReader in = new BufferedReader (new InputStreamReader (System.in));

		/* Création d'un objet Forum */
		Forum forumProxy = ForumHelper.narrow (orb.string_to_object (ior));
		while (true)
		{
			System.out.println ("Que voulez-vous faire ?");
			System.out.println ("a - ajouter un message");
			System.out.println ("v - voir les messages");
			System.out.println ("l - lire un message");
			System.out.println ("r - retirer un message");

			int choix = System.in.read ();
			in.readLine ();
			switch (choix)
			{
				case 'a' :
				{
					/*System.out.print ("Votre nom: ");
					String auth = in.readLine ();
					System.out.print ("Titre du message: ");
					String title = in.readLine ();
					System.out.print ("Message: ");
					String mess = in.readLine ();
					forumProxy.postMessage (new Message (title, auth, (new Date ()).toString (), mess));*/
					forumProxy.postMessage (new Message ("toto", "titi", (new Date ()).toString (), "tutu"));
				}
					break;

				case 'l' :
				{
					System.out.print ("Titre du message: ");
					String title = in.readLine ();
					Message m = forumProxy.getMessage (title);
					System.out.println (m.author + " a écrit à " + m.date.toString ());
					System.out.println ("Titre: " + m.title);
					System.out.println (m.body + "\n");
				}
					break;

				case 'r' :
				{
					System.out.print ("Titre du message: ");
					String title = in.readLine ();
				}
					break;

				case 'v' :
				{
					Message [] tab = forumProxy.getMessages ();

					for (int i = 0; i < tab.length; i++)
					{
						System.out.println (tab [i].author + "\t" + tab [i].date + "\t" + tab [i].title + "\n");
					}
				}
			}
		}
	}
}
