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

import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ForwardRequest;

/**
 * @author Sébastien GUINCHARD
 * 
 */
public class ClientInter extends org.omg.CORBA.LocalObject implements
        ClientRequestInterceptor {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3618417146430765111L;

    //----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//
    
    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_request(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void send_request(ClientRequestInfo ri) throws ForwardRequest {
        // ServiceContext sc = new ServiceContext ();
        // byte[] scData = "toto".getBytes();
        // sc.context_data = scData;
        // ri.add_request_service_context (sc, true);
        System.out.println("ClientInter.send request: " + ri.operation()
                + " number: " + ri.request_id());
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
        System.out.println("ClientInter.receive reply: " + ri.operation()
                + " number: " + ri.request_id());
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
