/*
 * Created on 18 févr. 2005
 * by Sébastien GUINCHARD
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.client;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author Sébastien GUINCHARD
 */
public class ClientORBInitializer extends LocalObject implements ORBInitializer {

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
        ClientInter client = new ClientInter();
        try {
            info.add_client_request_interceptor(client);
        } catch (DuplicateName e) {
            e.printStackTrace();
        }
    }
}
