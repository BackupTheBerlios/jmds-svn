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
import javacard.security.KeyBuilder;
import javacard.security.RSAPublicKey;
import javacard.security.RandomData;
import javacardx.crypto.Cipher;

/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author Jérôme GUERS
 */
public class SCAppletWithKey extends Applet {

    private final static byte CLA_SECURITY = (byte) 0x69;
    
    private final static byte INS_CODE = (byte) 0x10;
    private final static byte INS_DECODE = (byte) 0x20;
    private final static byte INS_SET_USER = (byte) 0x30;
    private final static byte INS_SET_SERVERKEY_MOD = (byte) 0x44;
    private final static byte INS_SET_SERVERKEY_EXP = (byte) 0x66;
    
    private final static short SESSIONS_KEY_LENGTH = (short) 5;
    private final static short USER_ID_LENGTH = (short) 10;

    private RandomData random;
    private RSAPublicKey serverKey;
    private byte[] sessionKey;
    private byte[] userID = null;
    private Cipher cipher;
    
    /**
     * Constructeur par défaut
     */
    protected SCAppletWithKey() {
        sessionKey = new byte[SESSIONS_KEY_LENGTH];
        
        serverKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, true);
        cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
        random = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
        register();
    }

    /**
     * @see javacard.framework.Applet#install(byte[], short, byte)
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new SCAppletWithKey();
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
                case INS_SET_SERVERKEY_EXP : setSeverKeyExp(apdu); break;
                case INS_SET_SERVERKEY_MOD : setServerKeyMod(apdu); break;
            }
        }
    }
    
    private void encode(APDU apdu)
    {
        if(userID == null)
            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
        if(!serverKey.isInitialized())
            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
        
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        byte[] code = new byte[(short)(byteRead + SESSIONS_KEY_LENGTH + USER_ID_LENGTH)];
        
        random.generateData(sessionKey, (short)0, SESSIONS_KEY_LENGTH);
        
        for (short i = 0; i < SESSIONS_KEY_LENGTH; i++) {
            code[i] = sessionKey[i];
        }

        for (short i = 0; i < USER_ID_LENGTH; i++) {
            code[(short)(i + SESSIONS_KEY_LENGTH)] = userID[i];
        }

        for (short i = 0; i < byteRead; i++) {
            code[(short)(i + SESSIONS_KEY_LENGTH + USER_ID_LENGTH)] = inBuffer[(short) (i + ISO7816.OFFSET_CDATA)];
        }
        
        cipher.init(serverKey, Cipher.MODE_ENCRYPT);
        short byteWrite = cipher.doFinal(code, (short)0, (short) (byteRead + SESSIONS_KEY_LENGTH + USER_ID_LENGTH), code, (short)0);
        
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < byteWrite)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

        // indicate the number of bytes in the data field
        apdu.setOutgoingLength(byteWrite); 
        // at offset 0 send 128 byte of data in the buffer
        apdu.sendBytesLong(code, (short)0, (short)byteWrite);
    }
    
    private void decode(APDU apdu)
    {
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();

        short cleInd = 0;
        byte []resByte = new byte[byteRead];
        for(short i = 0 ; i < byteRead ; i++ ) {
            resByte[i] = (byte)(inBuffer[(short)(i + ISO7816.OFFSET_CDATA)] ^ sessionKey[cleInd]);
            cleInd = (short)(((short)(cleInd + 1)) % SESSIONS_KEY_LENGTH);
        }
    }

    /** 
     * DOCME
     *
     * @param apdu 
     */
    private void setServerKeyMod(APDU apdu) {
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        
        serverKey.setModulus(inBuffer, ISO7816.OFFSET_CDATA, byteRead);
    }

    /** 
     * DOCME
     * 
     * @param apdu 
     */
    private void setSeverKeyExp(APDU apdu) {
        short byteRead = apdu.setIncomingAndReceive();
        byte[] inBuffer = apdu.getBuffer();
        
        serverKey.setExponent(inBuffer, ISO7816.OFFSET_CDATA, byteRead);
    }

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
    }
}
