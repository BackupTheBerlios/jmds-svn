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
		ORB orb = ORB.init(args, null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		ForumImpl forum = new ForumImpl("Forum de Test", "GG");
		
		byte [] objectID = rootPOA.activate_object(forum);
		Object obj = rootPOA.id_to_reference(objectID);
		
        //Création du service d'annuaire
        Object ns = orb.resolve_initial_references("NameService");
        NamingContextExt nc = NamingContextExtHelper.narrow(ns) ;
        
        NameComponent[] name=null;
        NamingContext ctxNC = null;
        
        try
        {
             ctxNC = nc.bind_new_context(nc.to_name("CTX"));
        }
        catch (AlreadyBound e) {
            Object objTemp = nc.resolve(nc.to_name("CTX"));
            if (objTemp instanceof NamingContext) {
                nc.rebind(nc.to_name("CTX"), objTemp);
            }
            else throw e;
        }
        name = nc.to_name("CTX/forumGG");
        
        try {
            nc.bind(name,obj);
        } 
        catch (AlreadyBound e) {
            e.printStackTrace();
            nc.rebind(name,obj);
        }
        
		rootPOA.the_POAManager().activate();
		orb.run();
        
        System.out.println("Server ready");
	}
}
