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
//import javacard.security.RandomData;

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
    private final static byte INS_DECODE = (byte) 0x20;
    private final static byte INS_SET_USER = (byte) 0x30;
    private final static byte INS_SET_SERVERKEY = (byte) 0x50;
    
    private final static short KEY_LENGTH = (short) 5;
    private final static short USER_ID_LENGTH = (short) 10;

//    private RandomData random;
    private byte[] userID = null;
    private byte[] serverKey;
//    private byte[] sessionKey;
    private boolean userIDInit = false;
    private boolean serverKeyInit = false;
//    private boolean sessionKeyInit = false;

    
    /**
     * Constructeur par défaut
     */
    protected SCApplet() {
        userID = new byte[USER_ID_LENGTH];
        serverKey = new byte[KEY_LENGTH];
//        sessionKey = new byte[KEY_LENGTH];
//        random = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
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
        byte[] inBuffer = apdu.getBuffer();

        if (inBuffer[ISO7816.OFFSET_CLA] == CLA_SECURITY) {
            switch (inBuffer[ISO7816.OFFSET_INS]) {
                case INS_CODE: encode(apdu); break;
                case INS_DECODE: decode(apdu); break;
                    
                case INS_SET_USER : setUserID(apdu); break;
                case INS_SET_SERVERKEY : setServerKey(apdu); break;
            }
        }
        else
            ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
    }
    
    private void encode(APDU apdu)
    {
        if(userIDInit == false)
            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
        if(serverKeyInit == false)
            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
        
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        byte[] code = new byte[(short)(byteRead + KEY_LENGTH + USER_ID_LENGTH)];
        
        short Le = apdu.setOutgoing();
        if (Le < (short)(code.length))
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

//        random.generateData(sessionKey, (short)0, KEY_LENGTH);
//        sessionKeyInit = true;
//        for (short i = 0; i < KEY_LENGTH; i++) {
//            code[i] = sessionKey[i];
//        }

        for (short i = 0; i < USER_ID_LENGTH; i++) {
            code[(short)(i + KEY_LENGTH)] = userID[i];
        }

        for (short i = 0; i < byteRead; i++) {
            code[(short)(i + KEY_LENGTH + USER_ID_LENGTH)] = inBuffer[(short) (i + ISO7816.OFFSET_CDATA)];
        }
        
        crypt(inBuffer, (short)ISO7816.OFFSET_CDATA, byteRead, code, (short)0, serverKey);

        apdu.setOutgoingLength((short)code.length); 
        apdu.sendBytesLong(code, (short)0, (short)code.length);
    }
    
    /** 
     * DOCME
     *
     * @param apdu 
     */
    private void decode(APDU apdu) {
        if(serverKeyInit == false)
            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);

//        if(sessionKeyInit == false)
//            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
        
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        byte[] code = new byte[byteRead];
        
        short Le = apdu.setOutgoing();
        if (Le < (short)(code.length))
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        
        crypt(inBuffer, (short)ISO7816.OFFSET_CDATA, byteRead, code, (short)0, serverKey);
//        sessionKeyInit = false;
        
        apdu.setOutgoingLength((short)code.length); 
        apdu.sendBytesLong(code, (short)0, (short)code.length);
    }
    
    /** 
     * DOCME
     *
     * @param apdu 
     */
    private void setServerKey(APDU apdu) {
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        
        if(byteRead != KEY_LENGTH)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        
        for (short i = 0; i < byteRead; i++) {
            serverKey[i] = inBuffer[(short)(ISO7816.OFFSET_CDATA + i)];
        }
        serverKeyInit = true;    }

    /** 
     * DOCME
     *
     * @param apdu 
     */
    private void setUserID(APDU apdu) {
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        
        if(byteRead != USER_ID_LENGTH)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        
        for (short i = 0; i < byteRead; i++) {
            userID[i] = inBuffer[(short)(ISO7816.OFFSET_CDATA + i)];
        }
        userIDInit = true;
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
    private void crypt(byte[] inBuffer, short inOffset, short inLength, byte[] outBuffer, short outOffset, byte[] key) {
        short cleInd = 0;
        for(short i = 0 ; i < inLength ; i++ ) {
            outBuffer[(short)(i + outOffset)] = (byte)(inBuffer[(short)(i + inOffset)] ^ key[cleInd]);
            cleInd = (short)(((short)(cleInd + 1)) % KEY_LENGTH);
        }
    }
}
