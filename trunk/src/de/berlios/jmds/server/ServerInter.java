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
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import de.berlios.jmds.tools.MyParseServiceContext;

/**
 * Allow to intercept message send or receive by server. 
 * So before send message on orb this objet code the service context.
 * And before receive message by server this objet decode the service context and check access to object by client.
 * @author Sébastien GUINCHARD
 */
public class ServerInter extends org.omg.CORBA.LocalObject implements ServerRequestInterceptor
{

	private int userID;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 3689630311277277232L;

	POA rootPOA;
	
	
	// ----------------------------------------------------------//
	// ------------------- PUBLIC METHODS -----------------------//
	// ----------------------------------------------------------//
	
	/**
	 * @param rootPOA the rootPOA use by the server
	 */
	public ServerInter(POA rootPOA) {
		this.rootPOA = rootPOA;
	}
	
	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request_service_contexts(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request_service_contexts (ServerRequestInfo ri) throws ForwardRequest
	{
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#receive_request(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void receive_request (ServerRequestInfo ri) throws ForwardRequest
	{
		System.out.println ("ServerInter.receive request: " + ri.request_id());		
		org.omg.CORBA.Object ior = null;
		try {
			ServiceContext sc = ri.get_request_service_context(0);
			byte[] scDecode = SCManager.getInstance().decode (sc.context_data);
			
			// Vérifie si le decodage c bien passe 
			if (!MyParseServiceContext.findRequestID(scDecode).equals(Integer.toString(ri.request_id()).getBytes()))
			{
            		throw new SecurityException("User don't use validated key"); 
        	}			

			// Recuperation de la reference de l'objet
			String[] szAdapters = ri.adapter_name();
			String szAdapterName = szAdapters[szAdapters.length - 1];
			POA poa =  rootPOA.find_POA(szAdapterName, false);
			ior = poa.id_to_reference(ri.object_id());
			
			// Valide l'acces du user à la méthode de l'objet
			if (!RightsManager.getInstance ().canUse (MyParseServiceContext.findClientID(scDecode).toString()  , ior.toString(), ri.operation ()))
        		{
            		throw new SecurityException("User can not access to this object"); 
        		}
		} catch (AdapterNonExistent e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ObjectNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}

	/**
	 * @see org.omg.PortableInterceptor.ServerRequestInterceptorOperations#send_other(org.omg.PortableInterceptor.ServerRequestInfo)
	 */
	public void send_other (ServerRequestInfo ri) throws ForwardRequest
	{
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
	public void destroy() {		
	}
}
