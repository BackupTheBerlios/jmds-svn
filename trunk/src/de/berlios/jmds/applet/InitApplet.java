/* 
 * File    : SCManager.java
 * Created : 18 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.applet;

import slb.iop.slbException;
import de.berlios.jmds.applet.UserManagerAppletClient;
import de.berlios.jmds.client.SCAppletClient;
import de.berlios.jmds.server.SCManager;

/**
 * DOCME
 * @author Jérôme GUERS
 * 
 */
public class InitApplet{

    public static void main(String[] args) throws SecurityException, slbException
    {
        UserManagerAppletClient.setUser("jmds1".getBytes());
        UserManagerAppletClient.setServerKey("0123456789".getBytes());        
    }
}

