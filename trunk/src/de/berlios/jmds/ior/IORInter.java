/*
 * Created on 18 févr. 2005
 * by Sébastien GUINCHARD
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.ior;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableInterceptor.IORInfo;
import org.omg.PortableInterceptor.IORInterceptor;

import com.sun.corba.se.impl.oa.poa.POACurrent;


/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author Sébastien GUINCHARD
 */
public class IORInter extends org.omg.CORBA.LocalObject implements IORInterceptor
{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3257852086392993079L;

	/**
	 * @see org.omg.PortableInterceptor.IORInterceptorOperations#establish_components(org.omg.PortableInterceptor.IORInfo)
	 */
	public void establish_components (IORInfo info)
	{
		try {
			// TODO Auto-generated method stub
			POACurrent current = (POACurrent) _orb().resolve_initial_references("POACurrent");
			
			//current.get_POA().reference_to_id(info.)
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#name()
	 */
	public String name ()
	{
		return "My IOR Interceptor";
	}

	/**
	 * @see org.omg.PortableInterceptor.InterceptorOperations#destroy()
	 */
	public void destroy ()
	{
		// TODO Auto-generated method stub

	}
}
