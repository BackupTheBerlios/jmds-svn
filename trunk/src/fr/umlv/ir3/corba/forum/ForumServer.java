package fr.umlv.ir3.corba.forum;

import java.io.FileNotFoundException;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

public class ForumServer {
  public static void main(String args[]) throws ServantAlreadyActive,
      ObjectNotActive, WrongPolicy, FileNotFoundException,
      AdapterInactive, org.omg.CORBA.ORBPackage.InvalidName, 
      NotFound, CannotProceed, InvalidName {

    //  Initialisation de l'ORB
    ORB orb = ORB.init(args, null);

    // Récupération de référence de l'adaptateur d'objets racine
    POA rootPOA = POAHelper.narrow(
        orb.resolve_initial_references("RootPOA"));

    // Initialisation de l'objet servant
    ForumImpl servant = new ForumImpl("java","forax");

    // Enregistrement du servant
    byte[] servantId = rootPOA.activate_object(servant);

    // Récupération de la référence du servant
    Forum forum = ForumHelper.narrow(
        rootPOA.id_to_reference(servantId));
    
    // Enregistrement dans le service de nommage
    NamingContextExt context = NamingContextExtHelper.narrow(
        orb.resolve_initial_references("NameService"));
    NameComponent[] name = context.to_name("Forum");
    context.rebind(name,forum);
    
    // Activation des servants
    rootPOA.the_POAManager().activate();

    System.out.println("Server is ready");

    // Mise en attente des requètes
    orb.run();
  }
}
