/*
 * Created on 18 févr. 2005 go to Window - Preferences - Java - Code Style -
 * Code Templates
 */
package de.berlios.jmds.client.core;

import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ForwardRequest;

import com.sun.corba.se.spi.ior.iiop.GIOPVersion;


/**
 * @author sguincha go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class ClientInter extends org.omg.CORBA.LocalObject implements ClientRequestInterceptor
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_request(org.omg.PortableInterceptor.ClientRequestInfo)
	 */
	public void send_request (ClientRequestInfo ri) throws ForwardRequest
	{
		GIOPVersion version;
		ServiceContext sc = new ServiceContext ();
		sc.context_data = new byte [65];

		ri.add_request_service_context (sc , true);
		System.out.println ("ClientInter.send request: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#send_poll(org.omg.PortableInterceptor.ClientRequestInfo)
	 */
	public void send_poll (ClientRequestInfo ri)
	{
		System.out.println ("ClientInter.send poll: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_reply(org.omg.PortableInterceptor.ClientRequestInfo)
	 */
	public void receive_reply (ClientRequestInfo ri)
	{
		System.out.println ("ClientInter.receive reply: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_exception(org.omg.PortableInterceptor.ClientRequestInfo)
	 */
	public void receive_exception (ClientRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ClientInter.receive exception: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.ClientRequestInterceptorOperations#receive_other(org.omg.PortableInterceptor.ClientRequestInfo)
	 */
	public void receive_other (ClientRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ClientInter.receive other: " + ri.operation ());
	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#name()
	 */
	public String name ()
	{
		return "Mon intercepteur";
	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#destroy()
	 */
	public void destroy ()
	{
	}

}
