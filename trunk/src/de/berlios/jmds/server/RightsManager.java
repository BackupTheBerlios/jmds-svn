/* 
 * File    : RightsManager.java
 * Created : 22 f�vr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.server;

import de.berlios.jmds.tools.RightsConfiguration;
import de.berlios.jmds.tools.SecurityConfiguration;

/**
 * Manage the rigths on server to check access of server object
 * 
 * @author Denis
 *
 */
public class RightsManager {
	
	/** The security configuration **/
	private SecurityConfiguration securityConfig 	= SecurityConfiguration.getInstance();
	
	/** The rights configuration **/
	private RightsConfiguration rightsConfig		= RightsConfiguration.getInstance();
	
	/** The singleton instance * */
	private static RightsManager INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private RightsManager (){
     rightsConfig = RightsConfiguration.getInstance();
     securityConfig = SecurityConfiguration.getInstance();
    }
	
	//----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//

	
	/**
	 * To get the singleton instance of RightsManager
	 * 
	 * @return the singleton instance of RightsManager
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
		String szGroupClient = securityConfig.getclientGroup(szUserId);
		//return rightsConfig.checkFonctionGroupAccess(szGroupClient, szIOR, szFonction);		
		return rightsConfig.checkObjectGroupAccess(szGroupClient, szIOR);		
	}
}