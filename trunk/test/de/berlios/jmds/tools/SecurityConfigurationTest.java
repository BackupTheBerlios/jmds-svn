/*
 * Created on 24 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.berlios.jmds.tools;

/**
 * @author Denis
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SecurityConfigurationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SecurityConfiguration securityConf = SecurityConfiguration.getInstance();
		
		System.out.println("Key server : " + securityConf.getServerKey());
		System.out.println("Key client 2 : " + securityConf.getClientKey("2"));
		System.out.println("Key groupe client 1 : " + securityConf.getclientGroup("1"));
	}
}
