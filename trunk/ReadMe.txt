=========================================
 JMDS PROJECT ("http://jmds.berlios.de")
=========================================
     Version 1.0 du 15/03/2005
======================================= 

Le projet JMDS est une surcouche venant prendre en charge la s�curit� des �changes client/server en Corba.

A ce titre, il fonctionne de fa�on transparente pouvant donc �tre utiliser sur toute application prenant en compte les initializer client et Server du projet JMDS

________________________________
LANCEMENT DU PROJET SUR LE FORUM

	1) Lancement du serveur :
Pour lancer un serveur de mani�re s�curis� par JMDS, il faut lui d�clarer qu'il doit prendre un compte le ServerInitializer. Pour cela, deux solutions :
* Ligne de commande : il faut ajouter l'option suivante � la commande de lancement du serveur :
	-Dorg.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.ServerORBInitializer

* Modification des propri�t�s de l'ORB : il faut d�clarer l'ORB utiliser par le serveur ainsi :
	java.util.Properties props = System.getProperties();
	//...

	props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.ServerORBInitializer" , "");
	ORB orb = ORB.init(args, props);

Il faut noter par ailleurs, que pour que la configuration fonctionne, il faut que le serveur est une r�f�rence persistante.


	2) Lancement du client :
De la m�me mani�re, il faudra d�clarer l'initializer du client pour le client de l'application s�curis�e :
* Ligne de commande :
	-Dorg.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.client.ClientORBInitializer

* Propri�t�s de l'ORB :
	java.util.Properties props = System.getProperties();
	//...

	props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.client.ClientORBInitializer" , "");
	ORB orb = ORB.init(args, props);

_____________
CONFIGURATION

Afin de faciliter la configuration des acces sur les objects du server, est mis � disposition le programme FindObject.
Celui-ci liste l'ensemble des objets connu par un ORB et stocke leurs IOR dans le fichier rights.xml

La definition des droits s'effectue ensuite de fa�on simple comme expliqu� dans la doc utilisateur.

Vous trouverez dans le projet JMDS des fichiers exemple de configuration.

________
JAVACARD

Une fois l'applet instanci� sur la carte il convient de l'initialiser afin de renseigner les informations propre au client.
Aisni, l'objet InitApplet permet de renseigner l'ID utilisateu et la cl� de cryptage utilis�e. Cet objet contenant une fonction Main est un d�but d'outil de g�n�ration de carte. Il faudrait le retravailler un peu pour rendre la cl� d�pendante du serveur auquel le propri�taire de la carte devrait avoir acc�s.