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

import de.berlios.jmds.common.AppletManager;
import de.berlios.jmds.tools.Convertor;
import slb.iop.slbException;

/**
 * @author Denis
 * 
 */
public class SCAppletClient {

    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte INS_CODE = (byte) 0x10;
    private final static byte INS_DECODE = (byte) 0x20;
    
    // ----------------------------------------------------------//
    // ------------------- PUBLIC METHODS -----------------------//
    // ----------------------------------------------------------//
    /**
     * @param requestId
     *            as id of the request message
     * @return The array byte which contains the encoding servec context
     * @throws slbException 
     * @throws SecurityException 
     */
    public static byte[] code(byte[] requestId) throws SecurityException, slbException {
        int[] body = Convertor.stringToIntArray("555555");
        byte[] code = Convertor.shortArrayToByteArray(AppletManager.sendCardAPDU(CLA_SECURITY, INS_CODE, 0, 0, body, 0x40));
        return code;
    }

    /**
     * @param SC
     *            as the message service context
     * @return the request id of the message
     * @throws slbException 
     * @throws SecurityException 
     */
    public static byte[] decode(byte[] SC) throws SecurityException, slbException {
        int[] body = Convertor.stringToIntArray("555555");
        byte[] decode = Convertor.shortArrayToByteArray(AppletManager.sendCardAPDU(CLA_SECURITY, INS_DECODE, 0, 0, body, 0x40));
        return decode;
    }
}
