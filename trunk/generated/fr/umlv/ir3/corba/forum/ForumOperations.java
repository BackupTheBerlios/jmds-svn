package fr.umlv.ir3.corba.forum;


/**
* fr/umlv/ir3/corba/forum/ForumOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 16 h 44 CET
*/

public interface ForumOperations 
{
  String theme ();
  String moderator ();
  void postMessage (String title, org.omg.CORBA.Any m) throws fr.umlv.ir3.corba.forum.Reject;
  org.omg.CORBA.Any getMessage (String title) throws fr.umlv.ir3.corba.forum.Reject;
  void removeMessage (String title) throws fr.umlv.ir3.corba.forum.Reject;
  org.omg.CORBA.Any[] messageList ();
  void getInfo (org.omg.CORBA.StringHolder theme, org.omg.CORBA.StringHolder moderator, org.omg.CORBA.IntHolder size);
} // interface ForumOperations