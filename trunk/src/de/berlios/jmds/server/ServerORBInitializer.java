/*
 * Created on 18 févr. 2005
 * by Sébastien GUINCHARD
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.server;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.*;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author Sébastien GUINCHARD
 */
public class ServerORBInitializer extends LocalObject implements ORBInitializer {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

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
        // ClientInter client = new ClientInter();
        ServerInter server = new ServerInter();
        // IORInter ior = new IORInter();
        try {
            // info.add_client_request_interceptor(client);
            info.add_server_request_interceptor(server);
            // info.add_ior_interceptor(ior);
        } catch (DuplicateName e) {
            e.printStackTrace();
        }
    }
}
