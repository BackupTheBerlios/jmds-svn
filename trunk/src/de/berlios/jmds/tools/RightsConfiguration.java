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
public class RightsConfiguration {
	/** The security config xml file **/
	private XMLConfiguration rightsConfig = null;
	
	/** The rigths config file **/
	private static final String RIGHTS_FILE = "rights.xml";
	
	/** The singleton instance * */
	private static RightsConfiguration INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private RightsConfiguration ()
	{
		try
		{
			XMLConfiguration.setDelimiter('\n');
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
	 * To get the singleton instance of RightsConfiguration
	 * 
	 * @return the singleton instance of RightsConfiguration
	 */
	public static RightsConfiguration getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new RightsConfiguration ();
		
		return INSTANCE;
	}
	
	/**
	 * @param iClientId 	the identifiant of client
	 * @param szGroup		the group of client
	 * @param szFonction	the fonction
	 * @return 				the permission of access (true or false)
	 */
	public boolean checkFonctionClientAccess(int iClientId, String szGroup, String szFonction)
	{
		//TODO
		return false;
	}
	
	/**
	 * @param szIOR			the ior reference of object
	 * @param szFonction	the the fonction of objetc
	 * @return				the add status (true validate add and false indicate the fonction exist)
	 */
	public boolean addIORFonction (String szIOR, String szFonction)
	{
		//TODO
		return false;
	}
	
	/**
	 * To save the configuration file
	 *
	 */
	public void save ()
	{
		try
		{
			rightsConfig.save ();
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
	}
}
