/* 
 * File    : SCManager.java
 * Created : 22 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.server;

import de.berlios.jmds.tools.SecurityConfiguration;


/**
 * @author Denis
 *
 */
public class SCManager {
	/** The security configuration **/
	private SecurityConfiguration securityConfig = SecurityConfiguration.getInstance();
	
	/** The singleton instance * */
	private static SCManager INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private SCManager ()
	{
		// TODO Implante le contructeur
	}
	
	//----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//

	
	/**
	 * To get the singleton instance of SCManager
	 * 
	 * @return the singleton instance of SCManager
	 */
	public static SCManager getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new SCManager ();
		
		return INSTANCE;
	}
	
	/**
	 * @param RequestId as id of the request message
	 * @return The array byte which contains the encoding servec context
	 */
	public byte[] code (byte[] RequestId)
	{
		// TODO
		return null;
	}
	
	/**
	 * @param SC as the message service context
	 * @return the request id of the message
	 */
	public int decode (byte[] SC)
	{
		// TODO
		return 0;		
	}
}
