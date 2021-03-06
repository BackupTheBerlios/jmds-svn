/*
 * Created on 1 oct. 2004
 */
package de.berlios.jmds.server;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import de.berlios.jmds.tools.RightsConfiguration;


/**
 * This object list all objects on server and save this configuration on a xml file
 * 
 * @author denis
 */
public class FindObject {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        
    	java.util.Properties props = System.getProperties();
    	props.put ("org.omg.CORBA.ORBInitialPort" , "1234");

        ORB orb = ORB.init(args, props);

        // Recuperation du service d'annuaire
        NamingContext nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
        
        //Exploration de l'arborescence et sotkage des objets dans le fichier de conf
        createConfig(orb, nc);
    }
    
    /**
     * @param orb
     * @param context The current context
     * @throws InvalidName
     * @throws CannotProceed
     * @throws NotFound
     */
    private static void createConfig (ORB orb, NamingContext context) throws NotFound, CannotProceed, InvalidName
    {
    	RightsConfiguration rightsConfig = RightsConfiguration.getInstance();
    	Binding[] binding = null;
    	BindingListHolder listHolder = new BindingListHolder ();
    	BindingIteratorHolder iteratorHolder = new BindingIteratorHolder ();
    	context.list (10, listHolder, iteratorHolder);
    	do {
    		binding = listHolder.value;
    		for ( int i=0; i < binding.length; i++) {
    			for (int j=0; j<binding[i].binding_name.length; j++)
    			{
    				System.out.println(binding[i].binding_name[j].id + " : "+ binding[i].binding_name[j]);
    				Object objProxy = context.resolve(binding[i].binding_name);
    				rightsConfig.addIOR (objProxy.toString());
    				
//    				//Récupération de la classe
//    				Class c = objProxy.getClass();
//					System.out.println(c);
//    				
//					// Récupération et stockage des methodes de la classes
//    				java.lang.reflect.Method[] methodes = c.getMethods();
//    				for (int k=0; k<methodes.length; k++)
//    				{
//    					rightsConfig.addIORFonction (objProxy.toString(), methodes[k].getName());
//    					System.out.println(methodes[k].getName());
//    				}
    			}  
    			if ( binding[i].binding_type.equals (BindingType.ncontext))
    			{
    				NamingContext newContext =	NamingContextHelper.narrow (context.resolve(binding[i].binding_name));
    				createConfig(orb, newContext);
    			}
    		}
    	} while( iteratorHolder.value != null && iteratorHolder.value.next_n (10, listHolder));
    	rightsConfig.save();
    }
}
