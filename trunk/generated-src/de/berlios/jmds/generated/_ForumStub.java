package de.berlios.jmds.generated;

/**
 * fr/umlv/ir3/corba/sguincha/td2/_ForumStub.java . Generated by the IDL-to-Java
 * compiler (portable), version "3.2" from IDL/Forum.idl vendredi 15 octobre
 * 2004 14 h 06 CEST
 */

public class _ForumStub extends org.omg.CORBA.portable.ObjectImpl implements de.berlios.jmds.generated.Forum
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#theme()
	 */
	public String theme ()
	{
		org.omg.CORBA.portable.InputStream $in = null;
		try
		{
			org.omg.CORBA.portable.OutputStream $out = _request ("_get_theme" , true);
			$in = _invoke ($out);
			String $result = $in.read_string ();
			return $result;
		}
		catch (org.omg.CORBA.portable.ApplicationException $ex)
		{
			$in = $ex.getInputStream ();
			String _id = $ex.getId ();
			throw new org.omg.CORBA.MARSHAL (_id);
		}
		catch (org.omg.CORBA.portable.RemarshalException $rm)
		{
			return theme ();
		}
		finally
		{
			_releaseReply ($in);
		}
	} // theme

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#moderator()
	 */
	public String moderator ()
	{
		org.omg.CORBA.portable.InputStream $in = null;
		try
		{
			org.omg.CORBA.portable.OutputStream $out = _request ("_get_moderator" , true);
			$in = _invoke ($out);
			String $result = $in.read_string ();
			return $result;
		}
		catch (org.omg.CORBA.portable.ApplicationException $ex)
		{
			$in = $ex.getInputStream ();
			String _id = $ex.getId ();
			throw new org.omg.CORBA.MARSHAL (_id);
		}
		catch (org.omg.CORBA.portable.RemarshalException $rm)
		{
			return moderator ();
		}
		finally
		{
			_releaseReply ($in);
		}
	} // moderator

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#postMessage(de.berlios.jmds.generated.Message)
	 */
	public boolean postMessage (de.berlios.jmds.generated.Message m)
	{
		org.omg.CORBA.portable.InputStream $in = null;
		try
		{
			org.omg.CORBA.portable.OutputStream $out = _request ("postMessage" , true);
			de.berlios.jmds.generated.MessageHelper.write ($out , m);
			$in = _invoke ($out);
			boolean $result = $in.read_boolean ();
			return $result;
		}
		catch (org.omg.CORBA.portable.ApplicationException $ex)
		{
			$in = $ex.getInputStream ();
			String _id = $ex.getId ();
			throw new org.omg.CORBA.MARSHAL (_id);
		}
		catch (org.omg.CORBA.portable.RemarshalException $rm)
		{
			return postMessage (m);
		}
		finally
		{
			_releaseReply ($in);
		}
	} // postMessage

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#getMessage(java.lang.String)
	 */
	public de.berlios.jmds.generated.Message getMessage (String title)
	{
		org.omg.CORBA.portable.InputStream $in = null;
		try
		{
			org.omg.CORBA.portable.OutputStream $out = _request ("getMessage" , true);
			$out.write_string (title);
			$in = _invoke ($out);
			de.berlios.jmds.generated.Message $result = de.berlios.jmds.generated.MessageHelper.read ($in);
			return $result;
		}
		catch (org.omg.CORBA.portable.ApplicationException $ex)
		{
			$in = $ex.getInputStream ();
			String _id = $ex.getId ();
			throw new org.omg.CORBA.MARSHAL (_id);
		}
		catch (org.omg.CORBA.portable.RemarshalException $rm)
		{
			return getMessage (title);
		}
		finally
		{
			_releaseReply ($in);
		}
	} // getMessage

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#getMessages()
	 */
	public de.berlios.jmds.generated.Message[] getMessages ()
	{
		org.omg.CORBA.portable.InputStream $in = null;
		try
		{
			org.omg.CORBA.portable.OutputStream $out = _request ("getMessages" , true);
			$in = _invoke ($out);
			de.berlios.jmds.generated.Message $result[] = de.berlios.jmds.generated.MessageSetHelper.read ($in);
			return $result;
		}
		catch (org.omg.CORBA.portable.ApplicationException $ex)
		{
			$in = $ex.getInputStream ();
			String _id = $ex.getId ();
			throw new org.omg.CORBA.MARSHAL (_id);
		}
		catch (org.omg.CORBA.portable.RemarshalException $rm)
		{
			return getMessages ();
		}
		finally
		{
			_releaseReply ($in);
		}
	} // getMessages

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#removeMessage(java.lang.String)
	 */
	public boolean removeMessage (String title)
	{
		org.omg.CORBA.portable.InputStream $in = null;
		try
		{
			org.omg.CORBA.portable.OutputStream $out = _request ("removeMessage" , true);
			$out.write_string (title);
			$in = _invoke ($out);
			boolean $result = $in.read_boolean ();
			return $result;
		}
		catch (org.omg.CORBA.portable.ApplicationException $ex)
		{
			$in = $ex.getInputStream ();
			String _id = $ex.getId ();
			throw new org.omg.CORBA.MARSHAL (_id);
		}
		catch (org.omg.CORBA.portable.RemarshalException $rm)
		{
			return removeMessage (title);
		}
		finally
		{
			_releaseReply ($in);
		}
	} // removeMessage

	// Type-specific CORBA::Object operations
	private static String []	__ids	= {"IDL:td2/Forum:1.0"};

	/**
	 * @see org.omg.CORBA.portable.ObjectImpl#_ids()
	 */
	public String [] _ids ()
	{
		return (String []) __ids.clone ();
	}

	private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
	{
		String str = s.readUTF ();
		String [] args = null;
		java.util.Properties props = null;
		org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args , props).string_to_object (str);
		org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
		_set_delegate (delegate);
	}

	private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
	{
		String [] args = null;
		java.util.Properties props = null;
		String str = org.omg.CORBA.ORB.init (args , props).object_to_string (this);
		s.writeUTF (str);
	}
} // class _ForumStub
