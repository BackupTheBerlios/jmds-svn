/* 
 * File    : SCManager.java
 * Created : 18 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.clients;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import slb.iop.slbException;
import de.berlios.jmds.applet.UserManagerAppletWithKeyClient;
import de.berlios.jmds.client.SCAppletClient;
import de.berlios.jmds.tools.Convertor;

/**
 * DOCME
 * @author Jérôme GUERS
 * 
 */
public class TestSCAppletWithKey{

    public static void main(String[] args) throws NoSuchAlgorithmException, SecurityException, slbException, NoSuchPaddingException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        
        UserManagerAppletWithKeyClient.setUser("jmds".getBytes());
        UserManagerAppletWithKeyClient.setServerKeyModulus(publicKey.getModulus().toByteArray());
        UserManagerAppletWithKeyClient.setServerKeyExponent(publicKey.getPublicExponent().toByteArray());
        
        byte[] code = {(short)0x55, (short)0x55,(short)0x55,(short)0x55,(short)0x55};
        
        try {
            code = SCAppletClient.code(code);
            
            System.out.println(Convertor.ByteArrayToSpacedHexString(code));
            
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            cipher.doFinal(code, (short)0, (short) code.length, code, (short)0);

            System.out.println(Convertor.ByteArrayToSpacedHexString(code));
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (slbException e) {
            e.printStackTrace();
        } 
        System.exit(0);
    }
}
