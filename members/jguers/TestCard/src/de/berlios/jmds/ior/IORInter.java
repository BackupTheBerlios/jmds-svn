/*
 * Created on 18 févr. 2005 TODO To change the template for this generated file
 * go to Window - Preferences - Java - Code Style - Code Templates
 */
package de.berlios.jmds.ior;

import org.omg.PortableInterceptor.IORInfo;
import org.omg.PortableInterceptor.IORInterceptor;


/**
 * @author sguincha TODO To change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class IORInter extends org.omg.CORBA.LocalObject implements IORInterceptor
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see org.omg.PortableInterceptor.IORInterceptorOperations#establish_components(org.omg.PortableInterceptor.IORInfo)
	 */
	public void establish_components (IORInfo info)
	{
		// TODO Auto-generated method stub

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
