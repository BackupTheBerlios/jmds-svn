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
public class Ping extends Applet {

    private final static byte CLA_PING = (byte) 0x80;
    private final static byte ECHO_REQUEST = (byte) 0x10;
    private final static byte ECHO_REPLY = (byte) 0x30;
    private final static short BUFFER_LENGTH = (short) 255;

    private byte[] buffer;
    private byte messLength;

    /**
     * Constructeur par défaut
     */
    protected Ping() {
        buffer = new byte[BUFFER_LENGTH];
        messLength = (byte) 0;
        register();
    }

    /**
     * @see javacard.framework.Applet#install(byte[], short, byte)
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new Ping();
    }

    /**
     * Méthide appelée à l'arrivée d'une trame APDU
     * 
     * @param apdu
     * @throws ISOException
     * 
     * @see javacard.framework.Applet#process(javacard.framework.APDU)
     */
    public void process(APDU apdu) throws ISOException {
        byte[] tmpBuffer = apdu.getBuffer();

        // CLA à 0x80
        if (tmpBuffer[ISO7816.OFFSET_CLA] == CLA_PING) {
            switch (tmpBuffer[ISO7816.OFFSET_INS]) {
            case ECHO_REQUEST:
                messLength = (byte)(apdu.setIncomingAndReceive());
//                if ((byteRead > (byte) BUFFER_LENGTH) || (byteRead <= (byte) 0))
//                    ISOException.throwIt(ISO7816.SW_WRONG_DATA);
                for (byte i = (byte) 0; i < messLength; i++) {
                    buffer[i] = tmpBuffer[ISO7816.OFFSET_CDATA + i];
                }
                break;

            case ECHO_REPLY:
                // Le = taille de la réponse attendue
                short Le = apdu.setOutgoing();
                // Verification que Le >= Lc (taille des données envoyées)
                if (Le < messLength)
                    ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
                apdu.setOutgoingLength((short) messLength);
                apdu.sendBytesLong(buffer, (short) 0, (short) messLength);
                break;
            }
        }
    }
}
