/*
 * Created on 9 févr. 2005
 * by Jérôme GUERS
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.tools;

/**
 * Permet la conversion de String en short[] et inversement
 * 
 * @version 0.1
 * 
 * @author Jérôme GUERS
 */
public class Convertor {
    
    /**
     * DOCME
     * @param value 
     * @return a table of short
     */
    public final static short[] stringToShortArray(String value) {
        short shortKey[] = new short[(value.length() / 2)];
        int y = 0;
        String strShort;

        for (int x = 0; x < shortKey.length; x++) {
            strShort = value.substring(y, (y + 2));
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
     * DOCME
     * @param value 
     * @return a table of int containing the stirng
     */
    public static int[] stringToIntArray(String value) {
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
	 * @param context_data
	 * @return string of the byte table
	 */
	public static String byteToString (byte [] context_data)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i < context_data.length; i++) {
			sb.append(context_data[i]);
		}
		return sb.toString();
	}
}

