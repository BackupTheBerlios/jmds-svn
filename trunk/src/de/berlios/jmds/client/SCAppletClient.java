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
    private final static SCAppletClient INSTANCE = new SCAppletClient();

    private final static IOP APPLET_IOP = new IOP();
    private final static short[] APPLET_AID = {
            (short) 0x11, (short) 0x22, (short) 0x33, 
            (short) 0x44, (short) 0x55, (short) 0x66, (short) 0x77 };

    private static SmartCard card = null;

    private static int CLA;
    private static int INS;
    private static int P1;
    private static int P2;
    private static int[] body;
    private static int LE;

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
        // Envoi de l'APDU CODE
        CLA = Integer.parseInt("69", 16);
        INS = Integer.parseInt("10", 16);
        P1 = Integer.parseInt("00", 16);
        P2 = Integer.parseInt("00", 16);
        body = Convertor.stringToIntArray("73119");
        LE = Integer.parseInt("00", 16);
        System.out.println(" body: " + requestId.toString());

        sendCardAPDU(card, CLA, INS, P1, P2, body, LE);

        // Envoi de l'APDU GETCODE
        System.out.print("Envoi de l'APDU Get Code: > ");
        CLA = (int) Integer.parseInt("69", 16);
        INS = (int) Integer.parseInt("11", 16);
        P1 = (int) Integer.parseInt("00", 16);
        P2 = (int) Integer.parseInt("00", 16);
        body = Convertor.stringToIntArray("");
        LE = (int) Integer.parseInt("20", 16);
        short[] code = sendCardAPDU(card, CLA, INS, P1, P2, body, LE);
        if (code != null)
            return Convertor.shortArrayToByteArray(code);
        return null;
    }

    /**
     * @param SC
     *            as the message service context
     * @return the request id of the message
     * @throws slbException 
     * @throws SecurityException 
     */
    public byte[] decode(byte[] SC) throws SecurityException, slbException {
        // Envoi de l'APDU CODE
        int CLA = Integer.parseInt("69", 16);
        int INS = Integer.parseInt("10", 16);
        int P1 = Integer.parseInt("00", 16);
        int P2 = Integer.parseInt("00", 16);
        int[] body = Convertor.stringToIntArray("6");
        int LE = Integer.parseInt("00", 16);

        SmartCard card = new SmartCard();
        sendCardAPDU(card, CLA, INS, P1, P2, body, LE);

        // Envoi de l'APDU getCode
        System.out.print("Envoi de l'APDU Get Code: > ");
        CLA = (int) Integer.parseInt("69", 16);
        INS = (int) Integer.parseInt("11", 16);
        P1 = (int) Integer.parseInt("00", 16);
        P2 = (int) Integer.parseInt("00", 16);
        body = Convertor.stringToIntArray("");
        LE = (int) Integer.parseInt("20", 16);
        short[] code = sendCardAPDU(card, CLA, INS, P1, P2, body, LE);

        SC = Convertor.shortArrayToByteArray(code);
        return SC;
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
        // CLA = Integer.parseInt("00", 16);
        // INS = Integer.parseInt("A4", 16);
        // P1 = Integer.parseInt("04", 16);
        // P2 = Integer.parseInt("00", 16);
        // body = Convertor.stringToIntArray("11223344556677");
        // LE = Integer.parseInt("00", 16);
        // sendCardAPDU(card,CLA,INS,P1,P2,body,LE);
        short[] out = card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
        card.EndTransaction();
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
                            if(!APPLET_AID.equals(shortListAID))
                                card = null;
                        }
                    }

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
