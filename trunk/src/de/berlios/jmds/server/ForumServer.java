/*
 * Created on 1 oct. 2004
 */
package de.berlios.jmds.server;

import java.io.*;
import java.util.Properties;

import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

import de.berlios.jmds.server.core.ForumImpl;



/**
 * @author jguers
 */
public class ForumServer
{

	/**
	 * @param args
	 * @throws InvalidName
	 * @throws ServantAlreadyActive
	 * @throws ObjectNotActive
	 * @throws WrongPolicy
	 * @throws IOException
	 * @throws AdapterInactive
	 */
	public static void main (String [] args) throws InvalidName, ServantAlreadyActive, ObjectNotActive, WrongPolicy, IOException, AdapterInactive
	{
		/* Initialisation de l'ORB */
/*		Properties prop = new Properties ();
		prop.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.core.ServerORBInitializer" , "");
		ORB orb = ORB.init (args , prop);
*/
		/*Initilisation de JAC ORB*/
		java.util.Properties props = new java.util.Properties();
		props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.core.ServerORBInitializer" , "");
		props.put("jacorb.implname","StandardNS");
		ORB orb = org.omg.CORBA.ORB.init(args, props);
		
		/* Récupération de référence de l'adaptateur d'objet racine */
		POA rootPOA = POAHelper.narrow (orb.resolve_initial_references ("RootPOA"));

		/* Création de l'objet SERVANT */
		ForumImpl forum = new ForumImpl ("Forum de Test", "GG");

		/* Enregistrement et activation de l'objet servant dans le POA */
		byte [] objectID = rootPOA.activate_object (forum);

		/* Récupération de la référence du servant */
		Object obj = rootPOA.id_to_reference (objectID);

		/* Enregistrement de l'ID de l'objet dans un fichier */
		FileOutputStream file = new FileOutputStream ("ObjectRef");
		file.write (orb.object_to_string (obj).getBytes ());

		/* Activation du POA */
		rootPOA.the_POAManager ().activate ();

		/* Lancement de l'ORB */
		System.out.println ("Server started");
		orb.run ();

	}
}
