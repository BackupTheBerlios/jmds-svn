/* 
 * File    : SCManager.java
 * Created : 18 f�vr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.clients;

import slb.iop.slbException;
import de.berlios.jmds.client.SCAppletClient;
import de.berlios.jmds.server.SCManager;
import de.berlios.jmds.server.UserManagerAppletClient;

/**
 * DOCME
 * @author J�r�me GUERS
 * 
 */
public class TestSCApplet{

    public static void main(String[] args) throws SecurityException, slbException
    {
        UserManagerAppletClient.setUser("jmds1".getBytes());
        UserManagerAppletClient.setServerKey("0123456789".getBytes());        
        
        byte[] code = "test".getBytes();
        
        System.out.println("Origine: " + new String(code));
        code = SCAppletClient.code(code);
        
        System.out.println("Cod�e: " + new String(code));
        code = SCManager.getInstance().decode(code);
        System.out.println("D�cod�e: " + new String(code));
        System.out.println("Reste � parser le userID et le requestID pour v�rifier");
        System.exit(0);
    }
}

