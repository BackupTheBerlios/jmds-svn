package fr.umlv.ir3.corba.forum;


/**
* fr/umlv/ir3/corba/forum/AudioMessage.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 16 h 44 CET
*/

public final class AudioMessage implements org.omg.CORBA.portable.IDLEntity
{
  public String title = null;
  public String author = null;
  public String date = null;
  public String body = null;

  public AudioMessage ()
  {
  } // ctor

  public AudioMessage (String _title, String _author, String _date, String _body)
  {
    title = _title;
    author = _author;
    date = _date;
    body = _body;
  } // ctor

} // class AudioMessage