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

import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ForwardRequest;

import slb.iop.slbException;

/**
 * @author Sébastien GUINCHARD
 * 
 */
public class ClientInter extends org.omg.CORBA.LocalObject implements
        ClientRequestInterceptor {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private final static long serialVersionUID = 3618417146430765111L;

    //----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//
    
    /**
     * @throws slbException 
     * @throws SecurityException 
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_request(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void send_request(ClientRequestInfo ri) throws ForwardRequest, SecurityException{
        System.out.println("ClientInter.send_request request: " + ri.request_id());
        System.out.println("target : " + ri.target());
        System.out.println("target : " + ri.operation());
        int id = ri.request_id();
        byte[] tabId = Integer.toString(id).getBytes();
        
        try {
            byte[] tabByteSC = SCAppletClient.code(tabId);
        	ServiceContext sc = new ServiceContext ();
            sc.context_data = tabByteSC;
            ri.add_request_service_context(sc,true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (slbException e) {
            throw new SecurityException("Impossible de coder le message: erreur de communication avec la carte", e);
        }
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_poll(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void send_poll(ClientRequestInfo ri) {
        System.out.println("ClientInter.send_poll request: " + ri.request_id());
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_reply(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void receive_reply(ClientRequestInfo ri) {
        System.out.println("ClientInter.receive_reply request: " + ri.request_id());
        
        //ServiceContext sc = ri.get_reply_service_context(0);
        //ServiceContext sc = null;
        try {
        	
            //short[] scDecode = SCAppletClient.decode (sc.context_data);
            //System.out.println (scDecode);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_exception(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void receive_exception(ClientRequestInfo ri) throws ForwardRequest {
        System.out.println("ClientInter.receive request: "+ ri.request_id());
    }

    /**
     * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_other(org.omg.PortableInterceptor.ClientRequestInfo)
     */
    public void receive_other(ClientRequestInfo ri) throws ForwardRequest {
        System.out.println("ClientInter.receive request: " + ri.request_id());
    }

    /**
     * @see org.omg.PortableInterceptor.InterceptorOperations#name()
     */
    public String name() {
        return "JMDS intercepteur";
    }

    /**
     * @see org.omg.PortableInterceptor.InterceptorOperations#destroy()
     */
    public void destroy() {
        System.out.println("Client Security Interceptor killed !!");
    }
}
