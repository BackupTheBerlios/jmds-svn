/*
 * Created on 9 févr. 2005
 * by Jérôme GUERS
 * Copyright: GPL - UMLV(FR) - 2004/2005
 */
package de.berlios.jmds.test.clients;

import de.berlios.jmds.tools.Convertor;
import slb.iop.*;

/**
 * Teste la classe Ping chargée sur la carte
 * 
 * @version 0.1
 * 
 * @author Jérôme GUERS
 */
public class ClientPing {

    private static class jmdsIOPListener implements IOPListener {

        /**
         * DOCME
         * 
         * @param arg0
         * 
         * @see slb.iop.IOPListener#CardRemoved(slb.iop.IOPEvent)
         */
        public void CardRemoved(IOPEvent event) {
            System.out.println("Carte retirée de ");
            System.out.println(event.getReaderName());
        }

        /**
         * DOCME
         * 
         * @param arg0
         * 
         * @see slb.iop.IOPListener#CardInserted(slb.iop.IOPEvent)
         */
        public void CardInserted(IOPEvent event) {
            System.out.print("Carte insérée dans ");
            System.out.println(event.getReaderName());
        }

        /**
         * DOCME
         * 
         * @param arg0
         * 
         * @see slb.iop.IOPListener#ReaderRemoved(slb.iop.IOPEvent)
         */
        public void ReaderRemoved(IOPEvent event) {
            System.out.println("Lecteur déconnecté");
        }

        /**
         * DOCME
         * 
         * @param arg0
         * 
         * @see slb.iop.IOPListener#ReaderInserted(slb.iop.IOPEvent)
         */
        public void ReaderInserted(IOPEvent event) {
            System.out.println("Lecteur connecté");
        }
    }

    public static void main(String[] args) {
        IOP sIOP = new IOP();

        jmdsIOPListener listener = new jmdsIOPListener();
        sIOP.addIOPListener(listener);

        String[] lstReaders = sIOP.ListReaders();

        for (int i = 0; i < lstReaders.length; i++) {
            System.out.println(lstReaders[i]);
        }

        SmartCard card = new SmartCard();
        if (lstReaders.length > 0) {
            boolean result = sIOP.Connect(card, lstReaders[0], false);
            if (result) {
                System.out.println("Carte connectee");

                int CLA = Integer.parseInt("00", 16);
                int INS = Integer.parseInt("A4", 16);
                int P1 = Integer.parseInt("04", 16);
                int P2 = Integer.parseInt("00", 16);
                int[] body = Convertor.stringToIntArray("11223344556677");
                int LE = Integer.parseInt("00", 16);
                try {
                    System.out.println("Applet Selection");
                    card.BeginTransaction();
                    card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
                    System.out.println("Applet selected");
                    card.EndTransaction();
                } catch (slb.iop.slbException e) {
                    System.out.println("Pb de Selection");
                    e.printStackTrace();
                }

                System.out.println("Envoi de l'APDU Echo Request");
                CLA = Integer.parseInt("80", 16);
                INS = Integer.parseInt("10", 16);
                P1 = Integer.parseInt("00", 16);
                P2 = Integer.parseInt("00", 16);
                body = Convertor.stringToIntArray("102030");
                LE = Integer.parseInt("00", 16);

                try {
                    card.BeginTransaction();
                    card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
                    card.EndTransaction();
                } catch (slb.iop.slbException e) {
                    System.out.println("Erreur request");
                    e.printStackTrace();
                }

                System.out.print("Envoi de l'APDU Echo Reply: > ");
                CLA = (int) Integer.parseInt("80", 16);
                INS = (int) Integer.parseInt("30", 16);
                P1 = (int) Integer.parseInt("00", 16);
                P2 = (int) Integer.parseInt("00", 16);
                body = Convertor.stringToIntArray("");
                LE = (int) Integer.parseInt("03", 16);

                try {
                    card.BeginTransaction();
                    short[] out = card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
                    for (int i = 0; i < out.length; i++) {
                        System.out.print(Integer.toHexString(out[i]) + " ");
                    }
                    System.out.println("");
                    card.EndTransaction();
                } catch (slb.iop.slbException e) {
                    System.out.println("Erreur reply");
                    e.printStackTrace();
                }
            }
        }
    }
}
