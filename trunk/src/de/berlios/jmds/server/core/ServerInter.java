/*
 * Created on 18 févr. 2005 TODO To change the template for this generated file
 * go to Window - Preferences - Java - Code Style - Code Templates
 */
package de.berlios.jmds.server.core;

import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.omg.PortableInterceptor.ServerRequestInterceptor;


/**
 * @author sguincha TODO To change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerInter extends org.omg.CORBA.LocalObject implements ServerRequestInterceptor
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request_service_contexts(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request_service_contexts (ServerRequestInfo ri) throws ForwardRequest
	{
		ServiceContext sc = ri.get_request_service_context (0);
		String sData = new String (sc.context_data);
		System.out.println ("ServerInter.receive request_SC: " + sData);
		sc.context_data = "titi".getBytes ();
		ri.add_reply_service_context (sc , true);

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
		ServiceContext sc = ri.get_request_service_context (0);
		sc.context_data = "titi".getBytes ();
		ri.add_reply_service_context (sc , true);
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
