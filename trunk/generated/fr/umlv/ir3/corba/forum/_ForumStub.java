package fr.umlv.ir3.corba.forum;


/**
* fr/umlv/ir3/corba/forum/_ForumStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from forum.idl
* lundi 21 f�vrier 2005 16 h 44 CET
*/

public class _ForumStub extends org.omg.CORBA.portable.ObjectImpl implements fr.umlv.ir3.corba.forum.Forum
{

  public String theme ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_theme", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return theme (        );
            } finally {
                _releaseReply ($in);
            }
  } // theme

  public String moderator ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_moderator", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return moderator (        );
            } finally {
                _releaseReply ($in);
            }
  } // moderator

  public void postMessage (String title, org.omg.CORBA.Any m) throws fr.umlv.ir3.corba.forum.Reject
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("postMessage", true);
                $out.write_string (title);
                $out.write_any (m);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:forum/Reject:1.0"))
                    throw fr.umlv.ir3.corba.forum.RejectHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                postMessage (title, m        );
            } finally {
                _releaseReply ($in);
            }
  } // postMessage

  public org.omg.CORBA.Any getMessage (String title) throws fr.umlv.ir3.corba.forum.Reject
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getMessage", true);
                $out.write_string (title);
                $in = _invoke ($out);
                org.omg.CORBA.Any $result = $in.read_any ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:forum/Reject:1.0"))
                    throw fr.umlv.ir3.corba.forum.RejectHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getMessage (title        );
            } finally {
                _releaseReply ($in);
            }
  } // getMessage

  public void removeMessage (String title) throws fr.umlv.ir3.corba.forum.Reject
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeMessage", true);
                $out.write_string (title);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:forum/Reject:1.0"))
                    throw fr.umlv.ir3.corba.forum.RejectHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                removeMessage (title        );
            } finally {
                _releaseReply ($in);
            }
  } // removeMessage

  public org.omg.CORBA.Any[] messageList ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("messageList", true);
                $in = _invoke ($out);
                org.omg.CORBA.Any $result[] = fr.umlv.ir3.corba.forum.MessageSetHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return messageList (        );
            } finally {
                _releaseReply ($in);
            }
  } // messageList

  public void getInfo (org.omg.CORBA.StringHolder theme, org.omg.CORBA.StringHolder moderator, org.omg.CORBA.IntHolder size)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getInfo", true);
                $in = _invoke ($out);
                theme.value = $in.read_string ();
                moderator.value = $in.read_string ();
                size.value = $in.read_long ();
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                getInfo (theme, moderator, size        );
            } finally {
                _releaseReply ($in);
            }
  } // getInfo

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:forum/Forum:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _ForumStub
