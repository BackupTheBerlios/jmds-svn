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
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;
import javacardx.crypto.*;

/**
 * DOCME
 * 
 * @version 0.1
 * 
 * @author Jérôme GUERS
 */
public class SCAppletWithKey extends Applet {

    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte CODE = (byte) 0x10;
    private final static byte DECODE = (byte) 0x30;

    private final static short BUFFER_LENGTH = (short) 255;

    private byte[] buffer;
    private byte[] privateCode;
    private byte[] code;
    private short messLength;
    
    private RSAPrivateKey userKey;
    private RSAPublicKey serverKey;
    private RSAPrivateKey sessionKey;
    private Cipher cipher;
    
//  Private Key definition
    static byte[] rsaPrivateKey = //Note this is 338 bytes long and is a 1024 bit CRT formatted Private key
          {
          (byte)0xC2, (byte)0x01, (byte)0x05,
          (byte)0xC2, (byte)0x41, (byte)0x00,
          (byte)0xC5, (byte)0xD7, (byte)0x20, (byte)0x28, (byte)0xFA, (byte)0xA7, (byte)0x91, (byte)0x55, 
          (byte)0x23, (byte)0xE2, (byte)0x0D, (byte)0xE4, (byte)0x28, (byte)0x7C, (byte)0x65, (byte)0xB7,
          (byte)0x18, (byte)0x59, (byte)0xD9, (byte)0x0D, (byte)0xBA, (byte)0xE7, (byte)0xCF, (byte)0x6A, 
          (byte)0xF1, (byte)0xE3, (byte)0x10, (byte)0xC3, (byte)0x7E, (byte)0x48, (byte)0x0D, (byte)0xBC,
          (byte)0x76, (byte)0x7B, (byte)0x04, (byte)0x86, (byte)0xB6, (byte)0x7F, (byte)0xCD, (byte)0x6C, 
          (byte)0x84, (byte)0x1A, (byte)0x0B, (byte)0x86, (byte)0xB9, (byte)0x96, (byte)0xAA, (byte)0x83,
          (byte)0x68, (byte)0x63, (byte)0x3C, (byte)0x4D, (byte)0x43, (byte)0x84, (byte)0xB8, (byte)0x6D, 
          (byte)0x48, (byte)0xAA, (byte)0xC4, (byte)0xC9, (byte)0x1B, (byte)0x50, (byte)0x47, (byte)0x49,
          (byte)0xC2, (byte)0x41, (byte)0x00,
          (byte)0xC9, (byte)0x5E, (byte)0x4A, (byte)0x08, (byte)0x0E, (byte)0xDC, (byte)0xA5, (byte)0x45, 
          (byte)0x90, (byte)0x1C, (byte)0x52, (byte)0xF8, (byte)0x3E, (byte)0xB0, (byte)0x6B, (byte)0x8F,
          (byte)0xCF, (byte)0xEA, (byte)0xA8, (byte)0xF5, (byte)0x1E, (byte)0xAD, (byte)0xD3, (byte)0x82, 
          (byte)0x52, (byte)0x30, (byte)0x43, (byte)0x04, (byte)0x9C, (byte)0x25, (byte)0x63, (byte)0x87,
          (byte)0x37, (byte)0x20, (byte)0x64, (byte)0x3F, (byte)0x16, (byte)0xB0, (byte)0x50, (byte)0xCC, 
          (byte)0x38, (byte)0x06, (byte)0x1B, (byte)0xDF, (byte)0xE6, (byte)0x78, (byte)0xC3, (byte)0x99,
          (byte)0xF7, (byte)0xC4, (byte)0x3F, (byte)0x95, (byte)0x81, (byte)0xE0, (byte)0x77, (byte)0x5C, 
          (byte)0xA3, (byte)0x78, (byte)0xB8, (byte)0x9C, (byte)0x8D, (byte)0x9B, (byte)0x46, (byte)0x5D,
          (byte)0xC2, (byte)0x41, (byte)0x00,
          (byte)0x7F, (byte)0xDE, (byte)0x17, (byte)0x36, (byte)0xDA, (byte)0x18, (byte)0x1E, (byte)0xEF, 
          (byte)0xD0, (byte)0x50, (byte)0xBD, (byte)0x2C, (byte)0x57, (byte)0xBE, (byte)0x10, (byte)0x7B,
          (byte)0x08, (byte)0x7D, (byte)0xC6, (byte)0xC7, (byte)0xF5, (byte)0x76, (byte)0xA2, (byte)0x59, 
          (byte)0x35, (byte)0x7D, (byte)0xEA, (byte)0xB0, (byte)0x73, (byte)0xE6, (byte)0x51, (byte)0xEB,
          (byte)0xD3, (byte)0x07, (byte)0xDD, (byte)0x73, (byte)0x56, (byte)0x8B, (byte)0x76, (byte)0x46, 
          (byte)0x3F, (byte)0xAE, (byte)0x0A, (byte)0xB9, (byte)0xA3, (byte)0xE3, (byte)0x0D, (byte)0x71,
          (byte)0xFB, (byte)0xFE, (byte)0x55, (byte)0x02, (byte)0xF6, (byte)0xB6, (byte)0x4D, (byte)0xC2, 
          (byte)0x79, (byte)0x64, (byte)0xFD, (byte)0x28, (byte)0xBB, (byte)0x13, (byte)0x23, (byte)0xB2,
          (byte)0xC2, (byte)0x41, (byte)0x00,
          (byte)0xA3, (byte)0x8D, (byte)0xA3, (byte)0xED, (byte)0x9C, (byte)0xC2, (byte)0x18, (byte)0xB8, 
          (byte)0x9D, (byte)0x10, (byte)0x8D, (byte)0x51, (byte)0x58, (byte)0x52, (byte)0xF6, (byte)0xB7,
          (byte)0xB5, (byte)0xEE, (byte)0xD9, (byte)0x2C, (byte)0xAB, (byte)0x9E, (byte)0x65, (byte)0xEF, 
          (byte)0xD0, (byte)0x86, (byte)0x59, (byte)0xDE, (byte)0x73, (byte)0xB0, (byte)0x57, (byte)0x82,
          (byte)0xBD, (byte)0x24, (byte)0x17, (byte)0xEA, (byte)0xD2, (byte)0x46, (byte)0xB7, (byte)0x69, 
          (byte)0x85, (byte)0x90, (byte)0x0E, (byte)0x85, (byte)0x53, (byte)0x3A, (byte)0x06, (byte)0x3E,
          (byte)0xDA, (byte)0x76, (byte)0x67, (byte)0x6C, (byte)0xAC, (byte)0x6B, (byte)0xB5, (byte)0x17, 
          (byte)0xCB, (byte)0x62, (byte)0x39, (byte)0x8A, (byte)0xD4, (byte)0x04, (byte)0xBA, (byte)0xD9,
          (byte)0xC2, (byte)0x41, (byte)0x00,
          (byte)0x44, (byte)0x03, (byte)0x03, (byte)0xB0, (byte)0x1B, (byte)0x0C, (byte)0xED, (byte)0x09, 
          (byte)0x44, (byte)0xB6, (byte)0x3C, (byte)0x53, (byte)0xBA, (byte)0x20, (byte)0xAE, (byte)0x03,
          (byte)0xA1, (byte)0xAE, (byte)0xD9, (byte)0x28, (byte)0x09, (byte)0x17, (byte)0x9E, (byte)0xC3, 
          (byte)0x7A, (byte)0x6C, (byte)0xF0, (byte)0x85, (byte)0xC3, (byte)0x13, (byte)0x61, (byte)0xBD,
          (byte)0x4E, (byte)0xA2, (byte)0x33, (byte)0x19, (byte)0x97, (byte)0xD9, (byte)0x2F, (byte)0x40, 
          (byte)0xFA, (byte)0x7F, (byte)0x1D, (byte)0xB5, (byte)0x0E, (byte)0xCB, (byte)0xA5, (byte)0x0D,
          (byte)0x00, (byte)0xC1, (byte)0x18, (byte)0xD4, (byte)0xAF, (byte)0x4C, (byte)0x18, (byte)0x24, 
          (byte)0x82, (byte)0xD6, (byte)0x08, (byte)0x4C, (byte)0x60, (byte)0x0B, (byte)0x9C, (byte)0xC5
          };

//  Public Key definition
    static byte[] rsaPublicKey = //Note this is 140 bytes long and is a modulus exponent formatted 1024 bit Public key
          {
          (byte)0xC1, (byte)0x01, (byte)0x05,
          (byte)0xC0, (byte)0x81, (byte)0x00,
          (byte)0x9B, (byte)0x9E, (byte)0xC6, (byte)0x74, (byte)0x65, (byte)0x5A, (byte)0xBC, (byte)0xBA, 
          (byte)0xC6, (byte)0xB0, (byte)0x5D, (byte)0x3C, (byte)0x24, (byte)0x3C, (byte)0x90, (byte)0x59,
          (byte)0x7D, (byte)0x5B, (byte)0x6D, (byte)0x03, (byte)0x7B, (byte)0xAD, (byte)0x9A, (byte)0xB4, 
          (byte)0x58, (byte)0xFE, (byte)0x80, (byte)0x22, (byte)0xEC, (byte)0xF0, (byte)0xAB, (byte)0x84,
          (byte)0xF9, (byte)0x07, (byte)0x84, (byte)0x91, (byte)0xE2, (byte)0x08, (byte)0xA6, (byte)0x9B, 
          (byte)0xED, (byte)0xD7, (byte)0x45, (byte)0xC9, (byte)0xDD, (byte)0xBF, (byte)0xF8, (byte)0x7A,
          (byte)0x8B, (byte)0xE1, (byte)0x17, (byte)0xCD, (byte)0x3D, (byte)0xD3, (byte)0x6F, (byte)0xB2, 
          (byte)0x59, (byte)0x0C, (byte)0x0E, (byte)0x47, (byte)0x0B, (byte)0x8E, (byte)0x83, (byte)0xC5, 
          (byte)0x0F, (byte)0xAF, (byte)0xD8, (byte)0x21, (byte)0x0C, (byte)0xD1, (byte)0x0B, (byte)0xB4, 
          (byte)0x24, (byte)0x8D, (byte)0x66, (byte)0xAC, (byte)0x93, (byte)0xA3, (byte)0xE4, (byte)0x61, 
          (byte)0xEF, (byte)0x26, (byte)0x50, (byte)0x1C, (byte)0x30, (byte)0xED, (byte)0x73, (byte)0xF1, 
          (byte)0x92, (byte)0xD8, (byte)0x2C, (byte)0xC6, (byte)0x38, (byte)0xD4, (byte)0x6D, (byte)0x81, 
          (byte)0x48, (byte)0x2B, (byte)0xCC, (byte)0x42, (byte)0xF8, (byte)0x60, (byte)0x61, (byte)0xAD, 
          (byte)0x8D, (byte)0x7F, (byte)0x8D, (byte)0x6D, (byte)0x87, (byte)0xBF, (byte)0x7D, (byte)0x1C, 
          (byte)0x61, (byte)0x2B, (byte)0xC0, (byte)0x42, (byte)0x47, (byte)0xDB, (byte)0xDD, (byte)0xC9, 
          (byte)0x3F, (byte)0x07, (byte)0x23, (byte)0xE1, (byte)0x3D, (byte)0xDA, (byte)0xDB, (byte)0x85, 
          (byte)0xC0, (byte)0x04, (byte)0x00,
          (byte)0x01, (byte)0x00, (byte)0x01,
          };
    
    /**
     * Constructeur par défaut
     */
    protected SCAppletWithKey() {
        privateCode = new byte[BUFFER_LENGTH];
        code = new byte[BUFFER_LENGTH];
        messLength = (byte) 0;
        userKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_512, true);
        userKey.setExponent(rsaPrivateKey, (short)5, (short) 12);
        userKey.setModulus(rsaPrivateKey, (short)127, (short) 6);
        serverKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, true);
        sessionKey = null;

        cipher = Cipher.getInstance(Cipher.ALG_RSA_ISO9796, false);
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
        buffer = apdu.getBuffer();

        if (buffer[ISO7816.OFFSET_CLA] == CLA_SECURITY) {
            switch (buffer[ISO7816.OFFSET_INS]) {
                case CODE:
                    encode(apdu);
                    break;
      
                case DECODE:
                    break;
            }
        }
    }
    
    private void encode(APDU apdu)
    {
        short byteRead = (short)(apdu.setIncomingAndReceive());
        
        cipher.init(userKey, Cipher.MODE_ENCRYPT);
        short outbytes = cipher.doFinal(buffer,(short)ISO7816.OFFSET_CDATA, byteRead, buffer, (short)ISO7816.OFFSET_CDATA);    
        
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < outbytes)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

        // indicate the number of bytes in the data field
        apdu.setOutgoingLength((short)outbytes); 
        // at offset 0 send 128 byte of data in the buffer
        apdu.sendBytesLong(buffer, (short)ISO7816.OFFSET_CDATA, (short)outbytes);
        
//        messLength = apdu.setIncomingAndReceive();
//        byte[] requestID = new byte[messLength];
//        for (short i = 0; i < messLength; i++) {
//            requestID[i] = (byte)(buffer[(short)(i + ISO7816.OFFSET_CDATA)] & 0x10);
//        }
//        cipher.init(userKey, Cipher.MODE_ENCRYPT);
//        cipher.doFinal(requestID, (short)0, (short) requestID, )
    }
}
