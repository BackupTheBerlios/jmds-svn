package fr.umlv.ir3.corba.forum;

import java.util.Date;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class ForumClient {

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
  public static void main(String[] args) throws Exception { 
    try {
      ORB orb = ORB.init(args,null);
      NamingContextExt context = NamingContextExtHelper.narrow(
          orb.resolve_initial_references("NameService"));
      NameComponent[] name = context.to_name("Forum");
      Forum forum = ForumHelper.narrow(context.resolve(name));
     
      Any a = orb.create_any();
      AudioMessageHelper.insert(a,
          new AudioMessage("Titre1","Auteur1",
              (new Date()).toString(),"audio.wav"));
      forum.postMessage("Titre1",a);

      TextMessageHelper.insert(a,
          new TextMessage("Titre2","Auteur2",
              (new Date()).toString(),"file.txt"));
      forum.postMessage("Titre2",a);

      System.out.println("MessageList");
      Any[] messages = forum.messageList();
      for(int i = 0; i<messages.length; i++) {
        printMessage(messages[i]);
      }
    } catch (Reject e){
      System.out.println(e.message);
    }
  }
}