/*
 * Created on 1 oct. 2004
 */
package fr.umlv.ir3.corba.forum;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIterator;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;


/**
 * @author jguers
 */
public class FindObject {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        
    	java.util.Properties props = System.getProperties();
        // props.put( "org.omg.CORBA.ORBClass", "org.openorb.CORBA.ORB" );
        // props.put("org.omg.CORBA.ORBSingletonClass","org.openorb.CORBA.ORBSingleton" );
        // props.put( "verbose", "5" );
    	props.put ("org.omg.CORBA.ORBInitialPort" , "1234");

        ORB orb = ORB.init(args, props);

//        String[] objects = orb.list_initial_services();
//        System.out.println(objects);
//        for (int i = 0 ; i<objects.length; i++)
//        	System.out.println(objects[i]);
        
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
//    	Binding[] bl = null;
//    	BindingListHolder blh = new BindingListHolder();
//    	BindingIteratorHolder bih = new BindingIteratorHolder();
//    	context.list (10, blh, bih);
//    	bl = blh.value;
//    	for (int i=0; i < bl.length; i++) {
//    		for (int j=0; j<bl[i].binding_name.length; j++)
//    			System.out.println ("boucle1 : " + bl[i].binding_name[j].id);
//    	}
//    	BindingIterator bi = bih.value;
//    	if (bi != null) {
//    		boolean continuer = true;
//    		while (continuer) {
//    			continuer = bi.next_n(10,blh);
//    			bl = blh.value;
//    			for (int i=0; i < bl.length; i++) {
//    				System.out.println ("boucle2 : " + bl[i].binding_name);
//    			}
//    		}
//    		bi.destroy();
//    	}
    	Binding[] binding = null;
    	BindingListHolder listHolder = new BindingListHolder ();
    	BindingIteratorHolder iteratorHolder = new BindingIteratorHolder ();
    	context.list (10, listHolder, iteratorHolder);
    	do {
    		binding = listHolder.value;
    		for ( int i=0; i < binding.length; i++) {
    			for (int j=0; j<binding[i].binding_name.length; j++)
    			{
    				System.out.println(binding[i].binding_name[j].id + " : "+ binding[i].binding_name[j].kind +  " : " + binding[i].binding_name[j]);
    				Object objProxy = context.resolve(binding[i].binding_name);
    			}  
    			if ( binding[i].binding_type.equals (BindingType.ncontext))
    			{
    				NamingContext newContext =	NamingContextHelper.narrow (context.resolve(binding[i].binding_name));
    				//createConfig(newContext);
    			}
    		}
    	} while( iteratorHolder.value != null && iteratorHolder.value.next_n (10, listHolder));
    }
}
