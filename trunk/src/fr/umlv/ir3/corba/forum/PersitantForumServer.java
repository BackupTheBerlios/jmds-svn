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
public class PersitantForumServer {

	/**
	 * @param args
	 * @throws InvalidName
	 * @throws ServantAlreadyActive
	 * @throws ObjectNotActive
	 * @throws WrongPolicy
	 * @throws IOException
	 * @throws AdapterInactive
	 * @throws NotFound
	 * @throws AlreadyBound
	 * @throws CannotProceed
	 * @throws InvalidName
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws InvalidPolicy
	 * @throws AdapterAlreadyExists
	 */
	public static void main(String[] args) throws InvalidName, ServantAlreadyActive, ObjectNotActive, WrongPolicy, IOException, AdapterInactive, NotFound, AlreadyBound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, AdapterAlreadyExists, InvalidPolicy {
        
        java.util.Properties props = System.getProperties();
//        props.put( "org.omg.CORBA.ORBClass", "org.openorb.CORBA.ORB" );
//        props.put( "org.omg.CORBA.ORBSingletonClass", "org.openorb.CORBA.ORBSingleton" );
//        props.put( "verbose", "5" );
        props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.ServerORBInitializer" , "");
        props.put ("com.sun.CORBA.POA.ORBPersistentServerPort" , "12345");
        props.put ("com.sun.CORBA.POA.ORBServerId" , "1");
        props.put ("org.omg.CORBA.ORBInitialPort" , "1234");

		ORB orb = ORB.init(args, props);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		Policy[] policies = {
				rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_SERVANT_MANAGER), 
				rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
				rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT)
		};
		
		POA forumPOA = rootPOA.create_POA("forumPOA", rootPOA.the_POAManager(), policies);
		rootPOA.the_POAManager().activate();
				
		ForumImpl forum = new ForumImpl("Forum de Test", "GG");
		
		byte [] objectID = forumPOA.activate_object(forum);		
		Object obj = forumPOA.id_to_reference(objectID);
        System.out.println(objectID);
        System.out.println(orb.object_to_string(obj));
        //Création du service d'annuaire
        NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
        
        NameComponent[] name = nc.to_name("jmdsForum");
        nc.rebind(name,obj);
        
        System.out.println("Server ready");
        
		orb.run();
	}
}
