/*
 * Created on 18 f�vr. 2005
 * by S�bastien GUINCHARD
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.client;

import org.omg.PortableInterceptor.*;

/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author S�bastien GUINCHARD
 */
public class ClientInter extends org.omg.CORBA.LocalObject implements
        ClientRequestInterceptor {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_request(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void send_request(ClientRequestInfo ri) throws ForwardRequest {
        // ServiceContext sc = new ServiceContext ();
        // byte[] scData = "toto".getBytes();
        // sc.context_data = scData;
        // ri.add_request_service_context (null, true);
        System.out.println("ClientInter.send request: " + ri.operation());
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_poll(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void send_poll(ClientRequestInfo ri) {
        System.out.println("ClientInter.send poll: " + ri.operation());
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_reply(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void receive_reply(ClientRequestInfo ri) {
        System.out.println("ClientInter.receive reply: " + ri.operation());
        // ServiceContext sc = ri.get_reply_service_context(0);
        // String sData = new String(sc.context_data);
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_exception(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void receive_exception(ClientRequestInfo ri) throws ForwardRequest {
        System.out.println("ClientInter.receive exception: ");
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_other(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void receive_other(ClientRequestInfo ri) throws ForwardRequest {
        System.out.println("ClientInter.receive other: " + ri.operation());
    }

    /**
     * @see org.omg.PortableInterceptor.InterceptorOperations#name()
     */
    public String name() {
        return "Mon intercepteur";
    }

    /**
     * @see org.omg.PortableInterceptor.InterceptorOperations#destroy()
     */
    public void destroy() {
    }

}
