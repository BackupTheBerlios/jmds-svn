package de.berlios.jmds.generated;

/**
 * fr/umlv/ir3/corba/sguincha/td2/MessageSetHolder.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.2" from IDL/Forum.idl vendredi 15
 * octobre 2004 14 h 06 CEST
 */

public final class MessageSetHolder implements org.omg.CORBA.portable.Streamable
{
	/**
	 * Comment for <code>value</code>
	 */
	public de.berlios.jmds.generated.Message	value[]	= null;

	/**
	 * 
	 */
	public MessageSetHolder ()
	{
	}

	/**
	 * @param initialValue
	 */
	public MessageSetHolder (de.berlios.jmds.generated.Message[] initialValue)
	{
		value = initialValue;
	}

	/**
	 * @see org.omg.CORBA.portable.Streamable#_read(org.omg.CORBA.portable.InputStream)
	 */
	public void _read (org.omg.CORBA.portable.InputStream i)
	{
		value = de.berlios.jmds.generated.MessageSetHelper.read (i);
	}

	/**
	 * @see org.omg.CORBA.portable.Streamable#_write(org.omg.CORBA.portable.OutputStream)
	 */
	public void _write (org.omg.CORBA.portable.OutputStream o)
	{
		de.berlios.jmds.generated.MessageSetHelper.write (o , value);
	}

	/**
	 * @see org.omg.CORBA.portable.Streamable#_type()
	 */
	public org.omg.CORBA.TypeCode _type ()
	{
		return de.berlios.jmds.generated.MessageSetHelper.type ();
	}

}
