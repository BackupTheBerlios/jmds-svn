/*
 * Created on 1 oct. 2004
 */
package fr.umlv.ir3.corba.forum;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

/**
 * @author jguers
 */
public class FindObject {

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

        //POACurrent objects = (POACurrent) orb.resolve_initial_references("POACurrent");
        String[] objects = orb.list_initial_services();
        System.out.println(objects);
        for (int i = 0 ; i<objects.length; i++)
        	System.out.println(objects[i]);
        
        POA object = (POA) orb.resolve_initial_references("RootPOA");
        object.the_POAManager().activate();
        System.out.println(object.get_servant_manager());
        //Object obj = object.id_to_reference(object.id());
		//System.out.println(obj);
    }
}
