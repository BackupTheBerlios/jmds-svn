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

	private int					userID;
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
		System.out.println ("ServerInter.receive request_SC: " + ri.operation ());
		ServiceContext sc = ri.get_request_service_context (0);
		SCManager scManager = SCManager.getInstance ();
		userID = scManager.decode (sc.context_data);
		System.out.println (userID);
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.receive request: " + ri.operation ());
		if (!RightsManager.getInstance ().canUse (userID + "" , new String (ri.object_id ()) , ri.operation ()))
		{
			//throw new SecurityException("User can not access to this object"); 
		}
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_reply(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_reply (ServerRequestInfo ri)
	{
		ServiceContext sc = ri.get_request_service_context (0);
		SCManager scManager = SCManager.getInstance ();

		byte [] tabByteSC = new Integer (ri.request_id ()).toString ().getBytes ();
		//byte [] tabByteSC = scManager.code (tabId);
		ri.add_reply_service_context (sc , true);
		System.out.println ("ServerInter.send_reply: " + tabByteSC);
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
