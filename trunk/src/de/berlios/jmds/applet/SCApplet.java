/*
 * Created on 8 févr. 2005
 * by Jérôme GUERS
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.applet;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISOException;
import javacard.framework.ISO7816;
import javacard.security.RandomData;

/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author Jérôme GUERS
 */
public class SCApplet extends Applet {

    private final static byte CLA_SECURITY = (byte) 0x69;
    
    private final static byte INS_CODE = (byte) 0x10;
    private final static byte INS_GET_CODE = (byte) 0x11;
    private final static byte INS_DECODE = (byte) 0x30;
    private final static byte INS_GET_DECODE = (byte) 0x33;
    private final static byte INS_SET_USER_KEY = (byte) 0x77;
    
    private final static short BUFFER_LENGTH = (short) 0x20;

    private final RandomData randomer;
    
    private byte[] buffer;
    private byte[] code;
    private byte key;

    
    /**
     * Constructeur par défaut
     */
    protected SCApplet() {
        buffer = new byte[BUFFER_LENGTH];
        code = new byte[BUFFER_LENGTH];
        randomer = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
        key = (byte)0;
        register();
    }

    /**
     * @see javacard.framework.Applet#install(byte[], short, byte)
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new SCApplet();
    }

    /**
     * Méthode appelée à l'arrivée d'une trame APDU
     * 
     * @param apdu
     * @throws ISOException
     * 
     * @see javacard.framework.Applet#process(javacard.framework.APDU)
     */
    public void process(APDU apdu) throws ISOException {
        buffer = apdu.getBuffer();

        if (buffer[ISO7816.OFFSET_CLA] == CLA_SECURITY) {
            switch (buffer[ISO7816.OFFSET_INS]) {
                case INS_CODE:
                    encode(apdu);
                    break;
                
                case INS_DECODE:
                    break;
                    
                case INS_SET_USER_KEY:
                    break;
                    
                default:
                    ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
            }
        }
        else
            ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
    }
    
    private void encode(APDU apdu)
    {
        short messLength = apdu.setIncomingAndReceive();
        randomer.generateData(code, (short) 0, BUFFER_LENGTH);
        for (short i = 0; i < messLength; i++) {
            code[(short)(i + key)] = buffer[(short)(i + ISO7816.OFFSET_CDATA)];
        }
        
        short Le = apdu.setOutgoing();
        // Verification que Le >= Lc (taille des données envoyées)
        if (Le < BUFFER_LENGTH)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        apdu.setOutgoingLength(BUFFER_LENGTH);
        apdu.sendBytesLong(code, (short) 0, BUFFER_LENGTH);
    }
}
