/* 
 * File    : SCManager.java
 * Created : 22 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.client;

/**
 * @author Denis
 * 
 */
public class SCAppletClient {
	
	/** The singleton instance * */
	private static SCAppletClient INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private SCAppletClient ()
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
	public static SCAppletClient getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new SCAppletClient ();
		
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
	public byte[] decode (byte[] SC)
	{
		// TODO
		return SC;			
	}
}
