=======================================
 JMDS PROJECT ("http://jmds.berlios.de")
=======================================
     Version 1.0 du 15/03/2005
======================================= 

Le projet JMDS est une surcouche venant prendre en charge la sécurité des échanges client/server
A ce titre, il fonctionne de façon transparente pouvant donc être utiliser sur toute application prenant en compte les initializer client et Server du projet JMDS

LANCEMENT DU PROJET SUR LE FORUM

Les fichiers de configuration rights.xml et security.xml on été configurer afin permettre l'utilisation du Forum en mode Client/Server

	1) Lancement du serveur : 
	2) Lancement du client :

Une fois le client lancé, celui-ci peut accéder au server et plus particulièrement a l'objet JMDSForum

CONFIGURATION

Afin de faciliter la configuration des acces sur les objects du server, est mis à disposition le programme FindObject.
celui-ci liste l'ensemble des objets du serveur et stocke leurs IOR dans le fichier rights.xml

La definition des droits s'effectue ensuite de façon simple come expliqué dans la doc utilisateur

JAVACARD

Une fois l'applet instancié sur la carte il convient de l'initialiser afin de renseigner les infromations propre au client.
Aisni, l'objet InitApplet permet de renseigner l'id du user et la clé de cryptage utilisée.