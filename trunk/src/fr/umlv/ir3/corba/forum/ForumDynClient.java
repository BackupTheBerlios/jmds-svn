package fr.umlv.ir3.corba.forum;

import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;

public class ForumDynClient {
  public static void main(String[] args) throws Exception {
    // Initialisation de l'ORB
    ORB orb = ORB.init(args, null);
    // R�cup�ration du service de nommage
    NamingContextExt context = NamingContextExtHelper.narrow(orb
        .resolve_initial_references("NameService"));
    // R�cup�ration de l'objet forum
    Object o = context.resolve_str("Forum");
    // Construction d'un objet requ�te vide
    Request request = o._request("getMessage");
    // Sp�cification du type de retour de la requ�te
    request.set_return_type(orb.get_primitive_tc(TCKind.tk_any));
    // Sp�cification de l'argument
    Any arg = request.add_named_in_arg("title");
    arg.insert_string("Titre1");
    // Sp�cification du type de l'exception
    ExceptionList exceptionList = request.exceptions();
    exceptionList.add(RejectHelper.type());
    // Appel synchrone de la requ�te
    request.invoke();
//    // Appel synchrone diff�r�
//    request.send_deferred();
//    while (!request.poll_response()) {
//      Thread.sleep(1000);
//    }
//    request.get_response();
    // R�cup�ration de l'environnement
    Environment env = request.env();
    // Si pas d'exception
    if (env.exception() == null) {
      // R�cup�ration du r�sultat
      Any result = request.return_value();
      printMessage(result.extract_any());
      return;
    }    
    // Extraction des exceptions
    Any ex = ((UnknownUserException)env.exception()).except;
    if (ex.type().equals(RejectHelper.type())) {
      throw RejectHelper.extract(ex);
    } else {
      throw env.exception();
  	}
  }

  public static void printMessage(Any a) throws Reject {
    if (a == null) {
      System.out.println(a);
      return;
    }
    if (a.type().equal(AudioMessageHelper.type())) {
      AudioMessage m = AudioMessageHelper.extract(a);
      System.out.println("Message audio: ");
      System.out.println("\tTitre : "+m.title);
      System.out.println("\tAuteur : "+m.author);
      System.out.println("\tDate : "+m.date);
      System.out.println("\tTexte : "+m.body);
      return;
    }
    if (a.type().equal(TextMessageHelper.type())) {
      TextMessage m = TextMessageHelper.extract(a);
      System.out.println("Message texte : ");
      System.out.println("\tTitre : "+m.title);
      System.out.println("\tAuteur : "+m.author);
      System.out.println("\tDate : "+m.date);
      System.out.println("\tTexte : "+m.body);
      return;
    }
    throw new Reject("Unknown message type.");
  }
}