/*
 * Created on 1 oct. 2004
 */
package fr.umlv.ir3.corba.forum;

import java.io.*;

import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

/**
 * @author jguers
 */
public class ForumServer {

	public static void main(String[] args) throws InvalidName, ServantAlreadyActive, ObjectNotActive, WrongPolicy, IOException, AdapterInactive, NotFound, AlreadyBound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
        
        java.util.Properties props = System.getProperties();
//        props.put( "org.omg.CORBA.ORBClass", "org.openorb.CORBA.ORB" );
//        props.put( "org.omg.CORBA.ORBSingletonClass", "org.openorb.CORBA.ORBSingleton" );
//        props.put( "verbose", "5" );
        props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.ServerORBInitializer" , "");

		ORB orb = ORB.init(args, props);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		ForumImpl forum = new ForumImpl("Forum de Test", "GG");
		
		byte [] objectID = rootPOA.activate_object(forum);
		Object obj = rootPOA.id_to_reference(objectID);
		
        //Création du service d'annuaire
        Object ns = orb.resolve_initial_references("NameService");
        NamingContextExt nc = NamingContextExtHelper.narrow(ns) ;
        
        NameComponent[] name = nc.to_name("jmdsForum");
        
        nc.rebind(name,obj);
        
		rootPOA.the_POAManager().activate();
        
        System.out.println("Server ready");
        
		orb.run();
	}
}
