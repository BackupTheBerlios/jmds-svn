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
    public final static byte[] code(byte[] requestId) throws SecurityException, slbException {
        int[] intRequestId = Convertor.ByteArrayToIntArray(requestId);
       // short[] tmpCode = AppletManager.sendCardAPDU(CLA_SECURITY, INS_CODE, 0, 0, intRequestId, 0x40);
        //byte[] code = Convertor.ShortArrayToByteArray(tmpCode);
        //return code;
        return requestId;
    }

    /**
     * @param SC
     *            as the message service context
     * @return the request id of the message
     * @throws slbException 
     * @throws SecurityException 
     */
    public final static short[] decode(byte[] sc) throws SecurityException, slbException {
        int[] intRequestId = Convertor.ByteArrayToIntArray(sc);
        return AppletManager.sendCardAPDU(CLA_SECURITY, INS_DECODE, 0, 0, intRequestId, 0x40);
    }
}
