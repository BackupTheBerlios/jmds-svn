package fr.umlv.ir3.corba.forum;


/**
* fr/umlv/ir3/corba/forum/ForumHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 16 h 44 CET
*/

abstract public class ForumHelper
{
  private static String  _id = "IDL:forum/Forum:1.0";

  public static void insert (org.omg.CORBA.Any a, fr.umlv.ir3.corba.forum.Forum that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static fr.umlv.ir3.corba.forum.Forum extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (fr.umlv.ir3.corba.forum.ForumHelper.id (), "Forum");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static fr.umlv.ir3.corba.forum.Forum read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ForumStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, fr.umlv.ir3.corba.forum.Forum value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static fr.umlv.ir3.corba.forum.Forum narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof fr.umlv.ir3.corba.forum.Forum)
      return (fr.umlv.ir3.corba.forum.Forum)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      fr.umlv.ir3.corba.forum._ForumStub stub = new fr.umlv.ir3.corba.forum._ForumStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static fr.umlv.ir3.corba.forum.Forum unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof fr.umlv.ir3.corba.forum.Forum)
      return (fr.umlv.ir3.corba.forum.Forum)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      fr.umlv.ir3.corba.forum._ForumStub stub = new fr.umlv.ir3.corba.forum._ForumStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
