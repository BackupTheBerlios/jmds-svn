/* 
 * File    : SCManager.java
 * Created : 22 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.server;

import de.berlios.jmds.tools.SecurityConfiguration;

/**
 * @author Denis
 * 
 */
public class SCManager {
    /** The security configuration * */
    private SecurityConfiguration securityConfig = SecurityConfiguration.getInstance();

    /** The singleton instance * */
    private static SCManager INSTANCE = null;
    
    private byte[] serverKey;

    // ----------------------------------------------------------//
    // ------------------- CONSTRUCTORS -------------------------//
    // ----------------------------------------------------------//

    /**
     * Constructor
     * 
     */
    private SCManager() {
        setDefaultServerKey();
    }

    // ----------------------------------------------------------//
    // ------------------- PUBLIC METHODS -----------------------//
    // ----------------------------------------------------------//
    /**
     * To get the singleton instance of SCManager
     * 
     * @return the singleton instance of SCManager
     */
    public static SCManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SCManager();

        return INSTANCE;
    }
    
    public void setServerKey(byte[] key) {
        this.serverKey = key;
    }

    public void setDefaultServerKey() {
        setServerKey(securityConfig.getServerKey().getBytes());
    }

    /**
     * @param RequestId
     *            as id of the request message
     * @return The array byte which contains the encoding servec context
     */
    public byte[] code(byte[] sc) {
        if(serverKey == null)
            throw new SecurityException("Clé de serveur non initialisée");
        
        byte[] code = new byte[sc.length];
        crypt(sc, 0, sc.length, code, 0, serverKey);
        
        return code;        
    }

    /**
     * @param SC
     *            as the message service context
     * @return the request id of the message
     */
    public byte[] decode(byte[] sc) {
        if(serverKey == null)
            throw new SecurityException("Clé de serveur non initialisée");

        byte[] code = new byte[sc.length];
        crypt(sc, 0, sc.length, code, 0, serverKey);
        
        return code;        
    }

    /**
     * DOCME
     * 
     * @param inBuffer
     * @param inOffset
     * @param inLength
     * @param outBuffer
     * @param outOffset
     */
    private void crypt(byte[] inBuffer, int inOffset, int inLength,byte[] outBuffer, int outOffset, byte[] key) {
        int cleInd = 0;
        for (int i = 0; i < inLength; i++) {
            outBuffer[i + outOffset] = (byte) (inBuffer[i + inOffset] ^ key[cleInd]);
            cleInd = (cleInd + 1) % key.length;
        }
    }
}
