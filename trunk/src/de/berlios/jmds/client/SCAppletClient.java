/* 
 * File    : SCManager.java
 * Created : 22 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */

package de.berlios.jmds.client;

import de.berlios.jmds.tools.Convertor;
import slb.iop.IOP;
import slb.iop.IOPEvent;
import slb.iop.IOPListener;
import slb.iop.SmartCard;
import slb.iop.slbException;

/**
 * @author Denis
 * 
 */
public class SCAppletClient {

    /** The singleton instance * */
    private static SCAppletClient INSTANCE;
    private static IOP APPLET_IOP;

    private final static short[] APPLET_AID = {
            (short) 0x11, (short) 0x22, (short) 0x33, 
            (short) 0x44, (short) 0x55, (short) 0x66, (short) 0x77 };
    
    private final static byte CLA_SECURITY = (byte) 0x69;
    private final static byte INS_CODE = (byte) 0x10;
    private final static byte INS_DECODE = (byte) 0x20;
    
    private final static byte INS_GETKEY_MOD_SIZE = (byte) 0x40;
    private final static byte INS_GETKEY_MOD_DATA = (byte) 0x41;
    private final static byte INS_GETKEY_EXP_SIZE = (byte) 0x42;
    private final static byte INS_GETKEY_EXP_DATA = (byte) 0x43;

    private static SmartCard card = null;

    private static class jmdsIOPListener implements IOPListener {
        public void CardRemoved(IOPEvent event) {
            findCard();
        }

        public void CardInserted(IOPEvent event) {
            findCard();
        }

        public void ReaderRemoved(IOPEvent event) {
            findCard();
        }

        public void ReaderInserted(IOPEvent event) {
            findCard();
        }
    }

    // ----------------------------------------------------------//
    // ------------------- CONSTRUCTORS -------------------------//
    // ----------------------------------------------------------//
    /**
     * Constructor
     * 
     */
    private SCAppletClient() {
        APPLET_IOP.addIOPListener(new jmdsIOPListener());
    }

    // ----------------------------------------------------------//
    // ------------------- PUBLIC METHODS -----------------------//
    // ----------------------------------------------------------//
    /**
     * To get the singleton instance of LDAP
     * 
     * @return the singleton instance of LDAP
     */
    public static SCAppletClient getInstance() {
        if(INSTANCE == null)
        {
            APPLET_IOP = new IOP();
            INSTANCE = new SCAppletClient();
            findCard();
        }
        return INSTANCE;
    }

    /**
     * @param requestId
     *            as id of the request message
     * @return The array byte which contains the encoding servec context
     * @throws slbException 
     * @throws SecurityException 
     */
    public byte[] code(byte[] requestId) throws SecurityException, slbException {
        int[] body = Convertor.stringToIntArray("555555");
        byte[] code = Convertor.shortArrayToByteArray(sendCardAPDU(card, CLA_SECURITY, INS_CODE, 0, 0, body, 0x40));
        return code;
    }

    /**
     * @param SC
     *            as the message service context
     * @return the request id of the message
     * @throws slbException 
     * @throws SecurityException 
     */
    public byte[] decode(byte[] SC) throws SecurityException, slbException {
        int[] body = Convertor.stringToIntArray("555555");
        byte[] decode = Convertor.shortArrayToByteArray(sendCardAPDU(card, CLA_SECURITY, INS_DECODE, 0, 0, body, 0x40));
        return decode;
    }
    
    /**
     * @param card
     * @param LE
     * @param body
     * @param P2
     * @param P1
     * @param INS
     * @param CLA
     * @return out the result of the sendCardAPDU call
     * @throws slbException
     * 
     */
    private final static short[] sendCardAPDU(SmartCard card, int CLA, int INS,
            int P1, int P2, int[] body, int LE) throws slbException,
            SecurityException {
        if (card == null)
            throw new SecurityException("Pas de carte disponible !");

        // Initialisation
        card.BeginTransaction();
        if (!card.SelectAID(APPLET_AID)) // A vérifier. Je ne sais pas si ça marche !!
            throw new SecurityException("Pas d'applet de cryptage !");
        
        short[] out = card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
        card.EndTransaction();
        if (out == null)
            out = new short[0];
        return out;
    }
    
    /**
     * Recherche d'une carte ayant notre applet instancié
     */
    private final static void findCard()
    {
        String[] lstReaders = APPLET_IOP.ListReaders();
        int i = 0;
        while ((i < lstReaders.length) && (card == null)) {
            card = new SmartCard();
            System.out.println(lstReaders[i]);
            boolean result = APPLET_IOP.Connect(card, lstReaders[i], false);
            if (result) {
                short[] MACKey = Convertor.HexStringToShortArray("404142434445464748494A4B4C4D4E4F");
                short[] autKey = Convertor.HexStringToShortArray("404142434445464748494A4B4C4D4E4F");
                short[] KEKKey = Convertor.HexStringToShortArray("404142434445464748494A4B4C4D4E4F");

                try {
                    card.BeginTransaction();
                    card.EstablishSecureChannel(MACKey, autKey, KEKKey);
                    short shortAID[] = card.EnumerateAID((byte) 0x40);

                    short[] shortListAID;

                    int z = 0;
                    for (int y = 0; y < shortAID.length; y = z) {
                        shortListAID = new short[shortAID[y]];
                        z = z + shortListAID.length + 3;
                        for (int x = 0; x < shortListAID.length; x++) {
                            shortListAID[x] = shortAID[x + y + 1];
                        }

                        if ((shortAID[z - 1] & (byte) 0x80) != 1) {
                            boolean equals = (APPLET_AID.length == shortListAID.length);
                            for (int j = 0; j < shortListAID.length && equals == true; j++) {
                                if(APPLET_AID[j] != shortListAID[j])
                                    equals = false;
                            }
                            if(!equals)
                            {
                                card.EndTransaction();
                                card = null;
                            }
                        }
                    }
                    if(card != null)
                        card.EndTransaction();
                } catch (slbException e) {
                    card = null;
                }
            } else
                card = null;
            i++;
        }
    }
}
