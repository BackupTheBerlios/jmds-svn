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

import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.*;

/** 
 * @author Sébastien GUINCHARD
 * 
 */
public class ServerInter extends org.omg.CORBA.LocalObject implements ServerRequestInterceptor
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3689630311277277232L;

	//----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//    
    
    /**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request_service_contexts(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request_service_contexts (ServerRequestInfo ri) throws ForwardRequest
	{
		String sData;
        System.out.println ("ServerInter.receive request_SC: " + ri.operation ());
        try
        {
            ServiceContext sc = ri.get_request_service_context (0);
            sData = new String (sc.context_data);
            System.out.println(sData);
        }catch (org.omg.CORBA.BAD_PARAM bad_param)
        {
            System.err.println("Pas de service contexte");
        }
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.receive request: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_reply(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_reply (ServerRequestInfo ri)
	{
		/*ServiceContext sc = ri.get_request_service_context (0);
		sc.context_data = "titi".getBytes ();
		ri.add_reply_service_context (sc , true);*/
		System.out.println ("ServerInter.send_reply: " + ri.operation ());

	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_exception(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_exception (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.send_exception: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_other(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_other (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.send_other: " + ri.operation ());

	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#name()
	 */
	public String name ()
	{
		return "Mon Intercepteur Serveur";
	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#destroy()
	 */
	public void destroy ()
	{
	}
}
