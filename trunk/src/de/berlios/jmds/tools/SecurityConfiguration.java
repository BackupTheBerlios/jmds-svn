/* 
 * File    : SCManager.java
 * Created : 23 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.tools;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * @author Denis
 *
 */
public class SecurityConfiguration {
	/** The security config xml file **/
	private XMLConfiguration securityConfig = null;
	
	/** The security config file **/
	private static final String SECURITY_FILE = "security.xml";

	/** The singleton instance * */
	private static SecurityConfiguration INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private SecurityConfiguration ()
	{
		try
		{
			XMLConfiguration.setDelimiter('\n');
			this.securityConfig = new XMLConfiguration (SECURITY_FILE);
		}
		catch (ConfigurationException e)
		{
			throw new RuntimeException (e);
		}
	}
	
	//----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//

	
	/**
	 * To get the singleton instance of SecurityConfiguration
	 * 
	 * @return the singleton instance of SecurityConfiguration
	 */
	public static SecurityConfiguration getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new SecurityConfiguration ();
		
		return INSTANCE;
	}
	
	/**
	 * @return the server key
	 */
	public byte[] getServerKey()
	{
		// TODO
		return null;	
	}
	
	/**
	 * @param iClientId the client identifiant
	 * @return			the client key
	 */
	public byte[] getClientKey (int iClientId)
	{
		//TODO
		return null;		
	}
	
	/**
	 * @param iClientId the client identifiant
	 * @return			the group client
	 */
	public byte[] getclientGroup (int iClientId)
	{
		// TODO
		return null;		
	}
}
