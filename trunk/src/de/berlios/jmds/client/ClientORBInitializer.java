/* 
 * File    : SCManager.java
 * Created : 18 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.client;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

/**
 * Initializer witch allow to attach a interceptor on client
 * @author Sébastien GUINCHARD
 * 
 */
public class ClientORBInitializer extends LocalObject implements ORBInitializer {

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
        ClientInter client = new ClientInter();
        try {
            info.add_client_request_interceptor(client);
        } catch (DuplicateName e) {
            e.printStackTrace();
        }
    }
}
