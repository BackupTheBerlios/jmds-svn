/*
 * Created on 24 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.berlios.jmds.tools;

/**
 * @author Denis
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RightsConfigurationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RightsConfiguration rightsConf = RightsConfiguration.getInstance();
		
		System.out.println("Access client : " + rightsConf.checkFonctionGroupAccess("grp_2", "ior_2", "fct_2"));

		System.out.println("Ajout methode : " + rightsConf.addIORFonction("ior_99", "fct_99"));
		System.out.println("Ajout methode : " + rightsConf.addIORFonction("ior_1", "fct_99"));
		System.out.println("Ajout methode : " + rightsConf.addIORFonction("ior_1", "fct_1"));
		rightsConf.save(); // sauvegarde dans le fichier saveRights.xml du repertoire src
	}
}
