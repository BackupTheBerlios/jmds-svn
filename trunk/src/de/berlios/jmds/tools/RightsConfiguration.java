/* 
 * File    : SCManager.java
 * Created : 23 févr. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.tools;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * @author Denis
 *
 */
public class RightsConfiguration {
	/** The security config xml file **/
	private XMLConfiguration rightsConfig = null;
	
	/** The rigths config file **/
	private static final File RIGHTS_FILE = new File("rights.xml");
	
	/** The singleton instance * */
	private static RightsConfiguration INSTANCE = null;
	
	//----------------------------------------------------------//
	//------------------- CONSTRUCTORS -------------------------//
	//----------------------------------------------------------//
	
	/**
	 * Constructor
	 *  
	 */
	private RightsConfiguration ()
	{
		try
		{
			rightsConfig = new XMLConfiguration ();
			rightsConfig.setFile(RIGHTS_FILE);
			rightsConfig.load();
		}
		catch (ConfigurationException e)
		{
			throw new RuntimeException (e);
		}
	}
	
	//----------------------------------------------------------//
	//------------------- PUBLIC METHODS -----------------------//
	//----------------------------------------------------------//

	
	/**
	 * To get the singleton instance of RightsConfiguration
	 * 
	 * @return the singleton instance of RightsConfiguration
	 */
	public static RightsConfiguration getInstance ()
	{
		if (INSTANCE == null)
			INSTANCE = new RightsConfiguration ();
		
		return INSTANCE;
	}
	
	/**
	 * @param szGroup		the group of client
	 * @param szIOR			the object reference
	 * @param szFonction	the fonction
	 * @return 				the permission of access (true or false)
	 */
	public boolean checkFonctionGroupAccess(String szGroup, String szIOR, String szFonction)
	{
		System.out.println("Recherche des groups ayant acces");
		int iIOR = -1;
		int iFonction = -1;
		
		// Recherche de l'objet dans le fichier de conf
		System.out.println("Recherche de l'objet");
		iIOR = getIORIndex(szIOR);
		
		// Si l'objet a ete trouve alors recherche de la fonction
		if (iIOR != -1){
			System.out.println("Recherche e la fonction");
			iFonction = getFonctionIndex(iIOR, szFonction);
		}
		
		// si la fonction de l'objet a ete trouve alors recuperation de group y ayant access
		if (iFonction != -1){
			System.out.println("Recherche du group");
			int iGroup = getGroupIndex(iIOR, iFonction, szGroup);
			if (iGroup !=-1)
				return true;			
		}
		
		// Si la l objet ou la fonction de l'objet n ont pas ete trouves
		return false;
	}
	
	/**
	 * @param szIOR			the ior reference of object
	 * @param szFonction	the the fonction of object
	 * @return				the add status (true validate add and false indicate the fonction exist)
	 */
	public boolean addIORFonction (String szIOR, String szFonction)
	{
		addIOR(szIOR);
		
		int iIOR = getIORIndex(szIOR);
		int iFonction = getFonctionIndex(iIOR, szFonction);
			
		if (iFonction !=- 1) 	// si la fonction existe
			return false ;
		
        // si la fonction n existe pas on l ajoute
		int iAddFonction = countFonction(iIOR)+1;
		rightsConfig.addProperty("ior("+ iIOR+ ").fonction("+ iAddFonction+ ")[@id]", szFonction);
		
		return true;
	}
	
	/**
	 * @param szIOR			the ior reference of object
	 * @return				the add status (true validate add and false indicate the fonction exist)
	 */
	public boolean addIOR (String szIOR)
	{
		int iIOR = getIORIndex(szIOR);
		
		if (iIOR !=-1) // Si l'objet existe dans le fichier de conf
			return false ;
		// si l'objet n'existe pas on l'ajoute
		int iAddIOR = countIOR()+1;
		rightsConfig.addProperty("ior("+ iAddIOR +")[@id]", szIOR);

        return true;
	}
	
	/**
	 * To save the configuration file
	 *
	 */
	public void save ()
	{
		try
		{
			// TODO enregistrement dans le meme fichier rights.xml apres les tests
			rightsConfig.save("src/saveRights.xml");
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	//----------------------------------------------------------//
	//------------------- PRIVATE METHODS -----------------------//
	//----------------------------------------------------------//	
	
	/**
	 * @param szIOR the object reference
	 * @return		the objet index in hierachie
	 */
	private int getIORIndex(String szIOR) {
		int i = 0;
		Iterator iter = rightsConfig.getList("ior[@id]").iterator();
		while(iter.hasNext()){
			String iorId = (String) iter.next();
			System.out.println(iorId);
			if (iorId.compareTo(szIOR)==0)
				return i;
			i++;
		}
		return -1;
	}
	
	/**
	 * @param iIOR			the object index
	 * @param szFonction	the fonction of object
	 * @return				the fonction index in hierachie
	 */
	private int getFonctionIndex(int iIOR, String szFonction) {
		int i = 0;
		Iterator iter = rightsConfig.getList("ior("+ iIOR+ ").fonction[@id]").iterator();
		i = 0;
		while(iter.hasNext()){
			String fonctionId = (String) iter.next();
			System.out.println(fonctionId);
			if (fonctionId.compareTo(szFonction)==0)
				return i;
			i++;
		}
		return -1;
	}
	
	/**
	 * @param iIOR			the object index
	 * @param iFonction		the fonction index
	 * @param szGroup		the group which have access to the fonction
	 * @return				the group index in hierachie 
	 */
	private int getGroupIndex(int iIOR, int iFonction, String szGroup) {
		int i = 0;
		Iterator iter = rightsConfig.getList("ior("+ iIOR+ ").fonction("+ iFonction+ ").group[@id]").iterator();
		while(iter.hasNext()){
			String groupId = (String) iter.next();
			System.out.println(groupId);
			if (groupId.compareTo(szGroup)==0)
				return i;
			i++;
		}
		return -1;
	}
	
	/**
	 * @return the number of object
	 */
	private int countIOR() {
		return rightsConfig.getList("ior[@id]").size();
	}
	
	/**
	 * @param iIOR	the object reference
	 * @return		the number of fonction
	 */
	private int countFonction(int iIOR) {
		return rightsConfig.getList("ior("+ iIOR+ ").fonction[@id]").size();
	}
}
