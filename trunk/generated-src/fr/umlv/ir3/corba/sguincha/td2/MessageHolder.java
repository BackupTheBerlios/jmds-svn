package fr.umlv.ir3.corba.sguincha.td2;

/**
 * fr/umlv/ir3/corba/sguincha/td2/MessageHolder.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.2" from IDL/Forum.idl vendredi 15
 * octobre 2004 14 h 06 CEST
 */

public final class MessageHolder implements org.omg.CORBA.portable.Streamable
{
	public fr.umlv.ir3.corba.sguincha.td2.Message	value	= null;

	public MessageHolder ()
	{
	}

	public MessageHolder (fr.umlv.ir3.corba.sguincha.td2.Message initialValue)
	{
		value = initialValue;
	}

	public void _read (org.omg.CORBA.portable.InputStream i)
	{
		value = fr.umlv.ir3.corba.sguincha.td2.MessageHelper.read (i);
	}

	public void _write (org.omg.CORBA.portable.OutputStream o)
	{
		fr.umlv.ir3.corba.sguincha.td2.MessageHelper.write (o , value);
	}

	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.umlv.ir3.corba.sguincha.td2.MessageHelper.type ();
	}

}
