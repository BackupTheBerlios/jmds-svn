/*
 * Created on 1 mars 2005
 * by Jérôme GUERS
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.common;

import de.berlios.jmds.tools.Convertor;
import slb.iop.IOP;
import slb.iop.IOPEvent;
import slb.iop.IOPListener;
import slb.iop.SmartCard;
import slb.iop.slbException;

/**
 * DOCME
 * 
 * @author Jérôme GUERS
 */
public class AppletManager {
    
    private static IOP APPLET_IOP;
    private static SmartCard card = null;
    
    public final static short[] APPLET_AID = {
        (short) 0x11, (short) 0x22, (short) 0x33, 
        (short) 0x44, (short) 0x55, (short) 0x66, (short) 0x77 };
    
    
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

    static {
        APPLET_IOP = new IOP();
        APPLET_IOP.addIOPListener(new jmdsIOPListener());
        findCard();
    }

    /**
     * Recherche d'une carte ayant notre applet instancié
     */
    public final static void findCard()
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
    public final static short[] sendCardAPDU(int CLA, int INS, int P1, int P2, int[] body, int LE) throws slbException,
            SecurityException {
        if (card == null)
            throw new SecurityException("Pas de carte disponible !");

        // Initialisation
        card.BeginTransaction();
        if (!card.SelectAID(AppletManager.APPLET_AID))
            throw new SecurityException("Pas d'applet de cryptage !");
        
        short[] out = card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
        card.EndTransaction();
        if (out == null)
            out = new short[0];
        return out;
    }
}

