/*
 * Created on 1 oct. 2004
 */
package fr.umlv.ir3.corba.forum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 * @author jguers
 */
public class SlimForumClient {

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
        props.put("org.omg.CORBA.ORBInitialPort" , "1234");
        
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

        Any any = orb.create_any();
        TextMessage message = new TextMessage("titi", "toto",(new Date()).toString(), "tutu");
        TextMessageHelper.insert(any, message);
        try {
        	forumProxy.postMessage("titi", any);
        } catch (Reject e) {
            System.out.println("Message non ajouté pour cette raison:\n" + e.message);
        }
        
    }
}
