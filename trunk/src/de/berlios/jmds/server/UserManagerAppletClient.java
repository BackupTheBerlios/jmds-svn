/*
 * Created on 1 mars 2005
 * by J�r�me GUERS
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.server;

import slb.iop.slbException;
import de.berlios.jmds.common.AppletManager;
import de.berlios.jmds.tools.Convertor;

/**
 * DOCME
 * 
 * @author J�r�me GUERS
 */
public class UserManagerAppletClient {

    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte INS_SET_USER = (byte) 0x30;
    private final static byte INS_SET_SERVERKEY = (byte) 0x50;
    
    // ----------------------------------------------------------//
    // ------------------- PUBLIC METHODS -----------------------//
    // ----------------------------------------------------------//
    /**
     * @param userId
     * 
     * @return
     * @throws slbException 
     * @throws SecurityException 
     */
    public static void setUser(byte[] userId) throws SecurityException, slbException {
        AppletManager.sendCardAPDU(CLA_SECURITY, INS_SET_USER, 0, 0, Convertor.ByteArrayToIntArray(userId), 0);
    }

    /**
     * @param key
     * 
     * @return
     * @throws slbException 
     * @throws SecurityException 
     */
    public static void setServerKey(byte[] key) throws SecurityException, slbException {
        AppletManager.sendCardAPDU(CLA_SECURITY, INS_SET_SERVERKEY, 0, 0, Convertor.ByteArrayToIntArray(key), 0);
    }
}

