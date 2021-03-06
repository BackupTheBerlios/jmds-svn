package fr.umlv.ir3.corba.forum;


/**
* fr/umlv/ir3/corba/forum/AudioMessageHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 17 h 35 CET
*/

abstract public class AudioMessageHelper
{
  private static String  _id = "IDL:forum/AudioMessage/AudioMessage:1.0";

  public static void insert (org.omg.CORBA.Any a, fr.umlv.ir3.corba.forum.AudioMessage that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static fr.umlv.ir3.corba.forum.AudioMessage extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [5];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "title",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "author",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "date",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "body",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "paroles",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (fr.umlv.ir3.corba.forum.AudioMessageHelper.id (), "AudioMessage", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static fr.umlv.ir3.corba.forum.AudioMessage read (org.omg.CORBA.portable.InputStream istream)
  {
    fr.umlv.ir3.corba.forum.AudioMessage value = new fr.umlv.ir3.corba.forum.AudioMessage ();
    value.title = istream.read_string ();
    value.author = istream.read_string ();
    value.date = istream.read_string ();
    value.body = istream.read_string ();
    value.paroles = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, fr.umlv.ir3.corba.forum.AudioMessage value)
  {
    ostream.write_string (value.title);
    ostream.write_string (value.author);
    ostream.write_string (value.date);
    ostream.write_string (value.body);
    ostream.write_string (value.paroles);
  }

}
