package fr.umlv.ir3.corba.forum;

/**
* fr/umlv/ir3/corba/forum/AudioMessageHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 17 h 35 CET
*/

public final class AudioMessageHolder implements org.omg.CORBA.portable.Streamable
{
  public fr.umlv.ir3.corba.forum.AudioMessage value = null;

  public AudioMessageHolder ()
  {
  }

  public AudioMessageHolder (fr.umlv.ir3.corba.forum.AudioMessage initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = fr.umlv.ir3.corba.forum.AudioMessageHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    fr.umlv.ir3.corba.forum.AudioMessageHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return fr.umlv.ir3.corba.forum.AudioMessageHelper.type ();
  }

}
