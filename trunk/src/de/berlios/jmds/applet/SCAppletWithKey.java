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
import javacard.security.KeyPair;
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
    private final static byte INS_CODE = (byte) 0x10;
    private final static byte INS_DECODE = (byte) 0x20;
    
    private final static byte INS_GETKEY_MOD_SIZE = (byte) 0x40;
    private final static byte INS_GETKEY_MOD_DATA = (byte) 0x41;
    private final static byte INS_GETKEY_EXP_SIZE = (byte) 0x42;
    private final static byte INS_GETKEY_EXP_DATA = (byte) 0x43;

    private final static short BUFFER_LENGTH = (short) 255;

    private byte[] buffer;
    private byte[] tmpBuff;
    
    private KeyPair userKeyPair;
    private RSAPublicKey serverKey;
    private RSAPrivateKey sessionKey = null;
    private Cipher cipher;
    
    /**
     * Constructeur par défaut
     */
    protected SCAppletWithKey() {
        tmpBuff = new byte[256];
        
        userKeyPair = new KeyPair( KeyPair.ALG_RSA, KeyBuilder.LENGTH_RSA_512);
        userKeyPair.genKeyPair();
        serverKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, true);

        cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
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
                case INS_CODE: encode(apdu); break;
                case INS_DECODE: break;
                    
                case INS_GETKEY_EXP_DATA : getPubKeyExponentSize(apdu); break;
                case INS_GETKEY_EXP_SIZE : getPubKeyExponent(apdu); break;
                case INS_GETKEY_MOD_DATA : getPubKeyModulusSize(apdu); break;
                case INS_GETKEY_MOD_SIZE : getPubKeyModulus(apdu); break;
            }
        }
    }
    
    public boolean select() {
        return super.select();
    }
    
    private void encode(APDU apdu)
    {
        short byteRead = apdu.setIncomingAndReceive();
        
        cipher.init(userKeyPair.getPrivate(), Cipher.MODE_ENCRYPT);
        short outbytes = cipher.doFinal(buffer,(short)ISO7816.OFFSET_CDATA, byteRead, buffer, (short)ISO7816.OFFSET_CDATA);    
        
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < outbytes)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

        // indicate the number of bytes in the data field
        apdu.setOutgoingLength(outbytes); 
        // at offset 0 send 128 byte of data in the buffer
        apdu.sendBytesLong(buffer, (short)ISO7816.OFFSET_CDATA, (short)outbytes);
        
//        messLength = apdu.setIncomingAndReceive();
//        RSAPublicKey pubKey = (RSAPublicKey) userKeyPair.getPublic();
//        short modSize = pubKey.getModulus(tmpBuff, (short) 0);
//        short expSize = pubKey.getExponent(tmpBuff, (short) 0);
//        byte[] code = new byte[messLength + modSize + expSize];
//        for (short i = 0; i < messLength; i++) {
//            code[i] = buffer[(short) (i + ISO7816.OFFSET_CDATA)];
//        }
//        modSize = pubKey.getModulus(tmpBuff, (short) 0);
//        for (short i = 0; i < modSize; i++) {
//            code[i + messLength] = tmpBuff[i];
//        }
//        expSize = pubKey.getExponent(tmpBuff, (short) 0);
//        for (short i = 0; i < expSize; i++) {
//            code[i + messLength + modSize] = tmpBuff[i];
//        }
//        cipher.init(userKeyPair.getPrivate(), Cipher.MODE_ENCRYPT);
//        cipher.doFinal(code, (short)0, (short) (messLength + modSize + expSize), code, (short)0);
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    //                    Public-Key-Transfer                                     //
    ////////////////////////////////////////////////////////////////////////////////
    private void getPubKeyModulusSize( APDU apdu ) {
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < (short) 2)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        
        RSAPublicKey pubKey = (RSAPublicKey) userKeyPair.getPublic();
        short keySize = pubKey.getModulus( tmpBuff,(short) 0);
        buffer[ (byte)0 ] = (byte) (keySize / 256 );
        buffer[ (byte)1 ] = (byte) (keySize % 256 );
        apdu.setOutgoing();
        apdu.setOutgoingLength( (byte) 2 );
        apdu.sendBytesLong( buffer, (short) 0, (short) 2 );
    }
    
    private void getPubKeyModulus( APDU apdu ) {
        RSAPublicKey pubKey = (RSAPublicKey) userKeyPair.getPublic();
        short keySize = pubKey.getModulus( tmpBuff,(short) 0);
        
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < keySize)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

        apdu.setOutgoing();
        apdu.setOutgoingLength( keySize );
        apdu.sendBytesLong( tmpBuff, (short) 0, keySize );
    }
    
    private void getPubKeyExponentSize( APDU apdu ) {
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < (short) 2)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

        RSAPublicKey pubKey = (RSAPublicKey) userKeyPair.getPublic();
        short keySize = pubKey.getExponent(tmpBuff,(short) 0);
        buffer[ (byte)0 ] = (byte) (keySize / 256 );
        buffer[ (byte)1 ] = (byte) (keySize % 256 );
        apdu.setOutgoing();
        apdu.setOutgoingLength( (byte) 2 );
        apdu.sendBytesLong( buffer, (short) 0, (short) 2 );
    }
    
    private void getPubKeyExponent( APDU apdu ) {
        RSAPublicKey pubKey = (RSAPublicKey) userKeyPair.getPublic();
        short keySize = pubKey.getExponent( tmpBuff,(short) 0);
        
        // Send results
        short Le = apdu.setOutgoing();
        if (Le < keySize)
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

        apdu.setOutgoing();
        apdu.setOutgoingLength( keySize );
        apdu.sendBytesLong( tmpBuff, (short) 0, keySize );
    }
}
