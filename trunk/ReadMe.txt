=========================================
 JMDS PROJECT ("http://jmds.berlios.de")
=========================================
     Version 1.0 du 15/03/2005
======================================= 

Le projet JMDS est une surcouche venant prendre en charge la sécurité des échanges client/server en Corba.

A ce titre, il fonctionne de façon transparente pouvant donc être utiliser sur toute application prenant en compte les initializer client et Server du projet JMDS

________________________________
LANCEMENT DU PROJET SUR LE FORUM

	1) Lancement du serveur :
Pour lancer un serveur de manière sécurisé par JMDS, il faut lui déclarer qu'il doit prendre un compte le ServerInitializer. Pour cela, deux solutions :
* Ligne de commande : il faut ajouter l'option suivante à la commande de lancement du serveur :
	-Dorg.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.ServerORBInitializer

* Modification des propriétés de l'ORB : il faut déclarer l'ORB utiliser par le serveur ainsi :
	java.util.Properties props = System.getProperties();
	//...

	props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.server.ServerORBInitializer" , "");
	ORB orb = ORB.init(args, props);

Il faut noter par ailleurs, que pour que la configuration fonctionne, il faut que le serveur est une référence persistante.


	2) Lancement du client :
De la même manière, il faudra déclarer l'initializer du client pour le client de l'application sécurisée :
* Ligne de commande :
	-Dorg.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.client.ClientORBInitializer

* Propriétés de l'ORB :
	java.util.Properties props = System.getProperties();
	//...

	props.put ("org.omg.PortableInterceptor.ORBInitializerClass.de.berlios.jmds.client.ClientORBInitializer" , "");
	ORB orb = ORB.init(args, props);

_____________
CONFIGURATION

Afin de faciliter la configuration des acces sur les objects du server, est mis à disposition le programme FindObject.
Celui-ci liste l'ensemble des objets connu par un ORB et stocke leurs IOR dans le fichier rights.xml

La definition des droits s'effectue ensuite de façon simple comme expliqué dans la doc utilisateur.

Vous trouverez dans le projet JMDS des fichiers exemple de configuration.

________
JAVACARD

Une fois l'applet instancié sur la carte il convient de l'initialiser afin de renseigner les informations propre au client.
Aisni, l'objet InitApplet permet de renseigner l'ID utilisateu et la clé de cryptage utilisée. Cet objet contenant une fonction Main est un début d'outil de génération de carte. Il faudrait le retravailler un peu pour rendre la clé dépendante du serveur auquel le propriétaire de la carte devrait avoir accès.