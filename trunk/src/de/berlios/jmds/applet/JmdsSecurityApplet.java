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

	private static byte[] SC;
	
    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte ENCODEUR = (byte) 0x10;
    //private final static byte DECODEUR = (byte) 0x30;
    private final static short BUFFER_LENGTH = (short) 255;

    private byte[] buffer;
    private byte messLength;
    
    /**
     * Constructeur par défaut
     */
    protected JmdsSecurityApplet() {
        buffer = new byte[BUFFER_LENGTH];
        SC[0] = (byte)'d';
        SC[1] = (byte)'e';
        SC[2] = (byte)'n';
        SC[3] = (byte)'i';
        SC[4] = (byte)'s';
        SC[5] = (byte)'e';
        
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
        if (tmpBuffer[ISO7816.OFFSET_CLA] == CLA_SECURITY) {
            switch (tmpBuffer[ISO7816.OFFSET_INS]) {
 /*           case ENCODEUR:
            	apdu.setOutgoingLength((short) messLength);
            	apdu.sendBytesLong(SC, (short) 0, (short) messLength);
            	
            	messLength = (byte)(apdu.setIncomingAndReceive());
//                if ((byteRead > (byte) BUFFER_LENGTH) || (byteRead <= (byte) 0))
//                    ISOException.throwIt(ISO7816.SW_WRONG_DATA);
                for (byte i = (byte) 0; i < messL ength; i++) {
                    buffer[i] = tmpBuffer[ISO7816.OFFSET_CDATA + i];
                }
                break;
*/
            case ENCODEUR:
//            	//Affectration du service context au buffer de la trame
//            	buffer = SC;
//                // Le = taille de la réponse attendue
//            	messLength = (byte)(apdu.setIncomingAndReceive());
//            	short Le = apdu.setOutgoing();
//                // Verification que Le >= messLength (taille des données envoyées)
//                if (Le < messLength)
//                    ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
                apdu.setOutgoingLength((short) SC.length);
                apdu.sendBytesLong(SC, (short) 0, (short) SC.length);
                break;
            }
        }
    }
}
