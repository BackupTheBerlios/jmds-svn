/* 
 * File    : SCManager.java
 * Created : 18 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.clients;

import slb.iop.slbException;
import de.berlios.jmds.client.SCAppletClient;
import de.berlios.jmds.tools.Convertor;

/**
 * DOCME
 * @author Jérôme GUERS
 * 
 */
public class TestSCApllet{

    public static void main(String[] args) {
        SCAppletClient appletClient = SCAppletClient.getInstance();
        
        byte[] code = {(short)0x55, (short)0x55,(short)0x55,(short)0x55,(short)0x55,};
        
        try {
            code = appletClient.code(code);
            System.out.println(Convertor.ByteArrayToSpacedHexString(code));
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (slbException e) {
            e.printStackTrace();
        } 
        System.exit(0);
    }
}

