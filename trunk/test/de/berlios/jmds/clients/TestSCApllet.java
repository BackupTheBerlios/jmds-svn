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

import slb.iop.IOP;
import slb.iop.SmartCard;
import de.berlios.jmds.tools.Convertor;

/**
 * DOCME
 * @author Jérôme GUERS
 * 
 */
public class TestSCApllet{

    public static void main(String[] args) {
        IOP sIOP = new IOP();

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

                System.out.println("Demande d'encodage");
                CLA = Integer.parseInt("69", 16);
                INS = Integer.parseInt("10", 16);
                P1 = Integer.parseInt("00", 16);
                P2 = Integer.parseInt("00", 16);
                body = Convertor.stringToIntArray("102030");
                LE = Integer.parseInt("FF", 16);

                try {
                    card.BeginTransaction();
                    short[] out = card.SendCardAPDU(CLA, INS, P1, P2, body, LE);
                    for (int i = 0; i < out.length; i++) {
                        System.out.print(Integer.toHexString(out[i]) + " ");
                    }
                    System.out.println("");
                    card.EndTransaction();
                } catch (slb.iop.slbException e) {
                    System.out.println("Erreur codage");
                    e.printStackTrace();
                }
            }
        }
    }
}

