/* 
 * File    : SCManager.java
 * Created : 9 f�vr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.tools;

/**
 * Give some conversion tools
 * 
 * @author J�r�me GUERS
 * 
 */
public class Convertor {
    
    /**
     * @param byteArray
     * @return the string
     */
    private final static String ByteToHexString(byte[] byteArray) {
        String strArray = new String();
        strArray = "";
        for (int x=0; x < byteArray.length; x++) {
            int b = ((int)byteArray[x] & 0x000000ff);
            if (b < 16) 
                strArray = strArray + "0" + Integer.toHexString(b).toUpperCase();
            else  
                strArray = strArray + Integer.toHexString(b).toUpperCase();
        }
        return strArray;
    }

    /**
     * @param i
     * @return the array of byte
     */
    private final static byte[] ShortToBytePair(short i)
    {
        byte[] retVal = new byte[2];
        retVal[0] = (byte)((i & 0xFFFF) >> 8);
        retVal[1] = (byte)(i & 0x00FF);
        return retVal;
    }
    
    // ----------------------------------------------------------//
    // ------------------- PUBLIC METHODS -----------------------//
    // ----------------------------------------------------------//
    /**
     * @param byteArray
     * @return the array of int
     */
    public final static int[] ByteArrayToIntArray(byte[] byteArray) {
        int[] tmp = new int[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            tmp[i] = byteArray[i];
        }
        return tmp;
    }

    /** 
     * @param byteArray
     * @return the string
     */
    public final static String ByteArrayToSpacedHexString(byte[] byteArray) {
        String strArray = "";
        String strHex = "";
        byte[] dataArray = new byte[1];
        
        for (int x=0; x < byteArray.length; x++) {
          dataArray[0] = byteArray[x];
          strHex = ByteToHexString(dataArray);      
          strArray = strArray + strHex + " ";
        }
        return strArray;    }
    
    /**
     * @param value
     * @return a table of int containing the string
     */
    public final static int[] HexStringToIntArray(String value) {
        int intKey[] = new int[(value.length() / 2)];
        int y = 0;
        String strInt;

        for (int x = 0; x < intKey.length; x++) {
            strInt = value.substring(y, (y + 2));
            if (strInt.equals("FF")) {
                intKey[x] = (int) 0xFF;
            } else {
                try {
                    intKey[x] = (int) Integer.parseInt(strInt, 16);
                } catch (NumberFormatException e) {
                    System.out.println("stringToShortArray Failed ");
                }
            }
            y = y + 2;
        }
        return intKey;
    }

    /**
     * 
     * @param strHex
     * @return the array of short
     */
    public final static short[] HexStringToShortArray(String strHex) {
        short shortKey[] = new short[(strHex.length() / 2)];
        int y = 0;
        String strShort;

        for (int x = 0; x < shortKey.length; x++) {
            strShort = strHex.substring(y, (y + 2));
            if (strShort.equals("FF")) {
                shortKey[x] = (short) 0xFF;
            } else {
                try {
                    shortKey[x] = (short) Short.parseShort(strShort, 16);
                } catch (NumberFormatException e) {
                    System.out.println("HexStringToShortArray Failed ");
                }
            }
            y = y + 2;
        }
        return shortKey;
    }

    /**
     * @param code
     * @return a table of byte corresponding to the argument code
     */
    public final static byte[] ShortArrayToByteArray(short[] code) {
        byte[] tmp = new byte[code.length];
        for (int i = 0; i < code.length; i++) {
            Short s = new Short(code[i]);
            tmp[i] = s.byteValue();
        }
        return tmp;
    }

    /** 
     * @param shortArray
     * @return the string
     */
    public final static String ShortArrayToSpacedHexString(short[] shortArray) {
        String strArray = "";
        String strHex = "";
        byte[] dataArray;
        byte[] dataArray2 = new byte[1];
        
        for (int x=0; x < shortArray.length; x++) {
          dataArray = ShortToBytePair(shortArray[x]);
          dataArray2[0] = dataArray[1];
          strHex = ByteToHexString(dataArray2);      
          strArray = strArray + strHex + " ";
        }
        return strArray;
    }
}
