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

import java.util.Iterator;

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
			securityConfig = new XMLConfiguration ();
			securityConfig.setFileName(SECURITY_FILE);
			securityConfig.load();
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
	public String getServerKey()
	{
		return securityConfig.getString("server[@key]");	
	}
	
	/**
	 * @param szClientId the client identifiant
	 * @return			the client key
	 */
	public String getClientKey (String szClientId)
	{
		return getAttributByClientId (szClientId, "key");		
	}
	
	/**
	 * @param szClientId 	the client identifiant
	 * @return				the group client
	 */
	public String getclientGroup (String szClientId)
	{
		return getAttributByClientId (szClientId, "group");	
	}
	
	/**
	 * @param szClientId  	the client identifiant
	 * @param szAttribute 	the attribute search
	 * @return				the  search attribute value
	 */
	private String getAttributByClientId (String szClientId, String szAttribute){				
		Iterator iter = securityConfig.getList("clients.client[@id]").iterator();
		int i = 0;
		while(iter.hasNext()){
			String id = (String) iter.next();
			if (id.compareTo(szClientId)==0)
				return securityConfig.getString("clients.client("+i+")[@"+szAttribute+"]");;
			i++;
		}
		System.out.println(szClientId);
		return null;
	}
}
