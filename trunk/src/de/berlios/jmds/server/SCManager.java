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


/**
 * @author Denis
 *
 */
public class SCManager {
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
	 * To get the singleton instance of LDAP
	 * 
	 * @return the singleton instance of LDAP
	 */
	public static SCManager getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new SCManager ();
		
		return INSTANCE;
	}
	
	/**
	 * @param iRequest_ID as id of the request message
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
