/* 
 * File    : RightsManager.java
 * Created : 22 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.server;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * @author Denis
 *
 */
public class RightsManager {
	
	/** The security configuration **/
	private XMLConfiguration securityConfig;
	
	/** The rights configuration **/
	private XMLConfiguration rightsConfig;
	
	/** The security config file **/
	private static final String SECURITY_FILE = "security.xml";
	
	/** The rigts config file **/
	private static final String RIGHTS_FILE = "rights.xml";
	
	/** The singleton instance * */
	private static RightsManager INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private RightsManager ()
	{
		try
		{
			XMLConfiguration.setDelimiter('\n');
			this.securityConfig = new XMLConfiguration (SECURITY_FILE);
			this.rightsConfig = new XMLConfiguration (RIGHTS_FILE);				
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
	 * To get the singleton instance of LDAP
	 * 
	 * @return the singleton instance of LDAP
	 */
	public static RightsManager getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new RightsManager ();
		
		return INSTANCE;
	}
	
	/**
	 * @param szUserId 		the user who would like to access to server objects
	 * @param szIOR			the reference of object
	 * @param szFonction	the fonction of object
	 * @return				the valid or refuse access 
	 */
	public boolean canUse (String szUserId, String szIOR, String szFonction)
	{
		// TODO
		return false;		
	}
}