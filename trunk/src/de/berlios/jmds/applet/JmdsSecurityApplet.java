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

/**
 * Première classe de test d'une applet JavaCard Renvoie juste les données
 * envoyées
 * 
 * @version 0.1
 * 
 * @author Jérôme GUERS
 */
public class JmdsSecurityApplet extends Applet {

    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte CODE = (byte) 0x10;
    private final static byte GET_CODE = (byte) 0x20;
    private final static byte DECODE = (byte) 0x30;
    private final static byte GET_DECODE = (byte) 0x40;
    private final static short BUFFER_LENGTH = (short) 255;

    private byte[] buffer;
    private byte[] code;
    private short messLength;
    
    /**
     * Constructeur par défaut
     */
    protected JmdsSecurityApplet() {
        buffer = new byte[BUFFER_LENGTH];
        code = new byte[BUFFER_LENGTH];
        messLength = (byte) 0;
        register();
    }

    /**
     * @see javacard.framework.Applet#install(byte[], short, byte)
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new JmdsSecurityApplet();
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
        byte[] buffer = apdu.getBuffer();

        // CLA à 69
        if (buffer[ISO7816.OFFSET_CLA] == CLA_SECURITY) {
            switch (buffer[ISO7816.OFFSET_INS]) {
                case CODE:
                    encode(apdu);
                    break;
                
                case GET_CODE:
                    getEncode(apdu);
                    break;

                case DECODE:
                    break;
                    
                case GET_DECODE:
                    break;
            }
        }
    }
    
    private void encode(APDU apdu)
    {
        messLength = apdu.setIncomingAndReceive();
        for (int i = 0; i < messLength; i++) {
            code[i] = (byte)(buffer[i + ISO7816.OFFSET_CDATA] & 0x10);
        }
    }
    
    private void getEncode(APDU apdu)
    {
        if(apdu.setOutgoing() < messLength)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        
        apdu.sendBytesLong(code, (short)0, messLength);
    }
}
