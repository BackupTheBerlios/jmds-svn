package fr.umlv.ir3.corba.forum;


/**
* fr/umlv/ir3/corba/forum/Reject.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 17 h 35 CET
*/

public final class Reject extends org.omg.CORBA.UserException
{
  public String message = null;

  public Reject ()
  {
    super(RejectHelper.id());
  } // ctor

  public Reject (String _message)
  {
    super(RejectHelper.id());
    message = _message;
  } // ctor


  public Reject (String $reason, String _message)
  {
    super(RejectHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class Reject
