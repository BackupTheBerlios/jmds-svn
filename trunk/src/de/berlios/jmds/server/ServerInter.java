/*
 * File : SCManager.java Created : 18 févr. 2005
 * ======================================= JMDS PROJECT
 * ("http://jmds.berlios.de") =======================================
 */

package de.berlios.jmds.server;

import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.omg.PortableInterceptor.ServerRequestInterceptor;

/**
 * @author Sébastien GUINCHARD
 */
public class ServerInter extends org.omg.CORBA.LocalObject implements ServerRequestInterceptor
{

	private int userID;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 3689630311277277232L;

	// ----------------------------------------------------------//
	// ------------------- PUBLIC METHODS -----------------------//
	// ----------------------------------------------------------//

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request_service_contexts(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request_service_contexts (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.receive_request_SC request: " + ri.request_id());
		ServiceContext sc = ri.get_request_service_context(0);
		byte[] scDecode = SCManager.getInstance().decode (sc.context_data);
        
        System.out.println("Target= "+ ri.orb_id());
		System.out.println (scDecode);
        
        if (!RightsManager.getInstance ().canUse (userID + "" , ri.orb_id() , ri.operation ()))
        {
            throw new SecurityException("User can not access to this object"); 
        }
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.receive request: " + ri.request_id());
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_reply(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_reply (ServerRequestInfo ri)
	{
        System.out.println("ServerInter.send_reply request: " + ri.request_id());
        int id = ri.request_id();
        byte[] tabId = Integer.toString(id).getBytes();
        
		byte [] scData = SCManager.getInstance().code(tabId);
        ServiceContext sc = new ServiceContext ();
        sc.context_data = scData;

		ri.add_reply_service_context(sc , true);
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_exception(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_exception (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.send_exception: " + ri.operation());
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_other(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_other (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.send_other: " + ri.operation());

	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#name()
	 */
	public String name ()
	{
		return "JMDS Intercepteur Serveur";
	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#destroy()
	 */
	public void destroy ()
	{
        System.out.println("Server Security Interceptor killed !!");   
    }
}
