/* 
 * File    : SCManager.java
 * Created : 18 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.server;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidName;
import org.omg.PortableServer.POAHelper;

/**
 * Initializer witch allow to attach a interceptor on server
 * @author Sébastien GUINCHARD
 * 
 */
public class ServerORBInitializer extends LocalObject implements ORBInitializer {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

	//----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//
  
    /**
     * @see org.omg.PortableInterceptor.ORBInitializerOperations#pre_init(org.omg.PortableInterceptor.ORBInitInfo)
     */
    public void pre_init(ORBInitInfo info) {
        System.out.println("MonORBInitializer.pre_init: ORB ID: " + info);
    }

    /**
     * @see org.omg.PortableInterceptor.ORBInitializerOperations#post_init(org.omg.PortableInterceptor.ORBInitInfo)
     */
    public void post_init(ORBInitInfo info) {
        System.out.println("MonORBInitializer.post_init: ORB ID: " + info);
        try {
        	ServerInter server = new ServerInter(POAHelper.narrow(info.resolve_initial_references("RootPOA")));
        	info.add_server_request_interceptor(server);
        } catch (DuplicateName e) {
            e.printStackTrace();
        } catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
