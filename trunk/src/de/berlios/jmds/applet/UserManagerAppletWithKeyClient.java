/*
 * Created on 1 mars 2005
 * by Jérôme GUERS
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.applet;

import slb.iop.slbException;
import de.berlios.jmds.common.AppletManager;
import de.berlios.jmds.tools.Convertor;

/**
 * DOCME
 * 
 * @author Jérôme GUERS
 */
public class UserManagerAppletWithKeyClient {

    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte INS_SET_USER = (byte) 0x30;
    private final static byte INS_SET_SERVERKEY_MOD = (byte) 0x44;
    private final static byte INS_SET_SERVERKEY_EXP = (byte) 0x66;
    
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
     * @param userId
     * 
     * @return
     * @throws slbException 
     * @throws SecurityException 
     */
    public static void setServerKeyModulus(byte[] mod) throws SecurityException, slbException {
        AppletManager.sendCardAPDU(CLA_SECURITY, INS_SET_SERVERKEY_MOD, 0, 0, Convertor.ByteArrayToIntArray(mod), 0);
    }

    /**
     * @param mod
     * 
     * @return
     * @throws slbException 
     * @throws SecurityException 
     */
    public static void setServerKeyExponent(byte[] exp) throws SecurityException, slbException {
        AppletManager.sendCardAPDU(CLA_SECURITY, INS_SET_SERVERKEY_EXP, 0, 0, Convertor.ByteArrayToIntArray(exp), 0);
    }
}
