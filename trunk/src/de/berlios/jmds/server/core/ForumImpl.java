/*
 * Created on 1 oct. 2004
 */
package de.berlios.jmds.server.core;

import java.util.concurrent.ConcurrentHashMap;

import fr.umlv.ir3.corba.sguincha.td2.ForumPOA;
import fr.umlv.ir3.corba.sguincha.td2.Message;


/**
 * @author sguinchard
 */
public class ForumImpl extends ForumPOA
{

	private String				moderator;
	private String				theme;
	private ConcurrentHashMap	discussion;

	/**
	 * @param theme
	 * @param moderator
	 */
	public ForumImpl (String theme, String moderator)
	{
		this.theme = theme;
		this.moderator = moderator;
		discussion = new ConcurrentHashMap ();
	}

	/**
	 * @see fr.umlv.ir3.corba.sguincha.td2.ForumOperations#theme()
	 */
	public String theme ()
	{
		return theme;
	}

	
	/**
	 * @see fr.umlv.ir3.corba.sguincha.td2.ForumOperations#moderator()
	 */
	public String moderator ()
	{
		return moderator;
	}

	/**
	 * @see fr.umlv.ir3.corba.sguincha.td2.ForumOperations#postMessage(fr.umlv.ir3.corba.sguincha.td2.Message)
	 */
	public boolean postMessage (Message m)
	{
		if (discussion.putIfAbsent (theme , m) == null)
			return true;
		return false;
	}

	/**
	 * @see fr.umlv.ir3.corba.sguincha.td2.ForumOperations#getMessage(java.lang.String)
	 */
	public Message getMessage (String title)
	{
		return (Message) discussion.get (title);
	}

	/**
	 * @see fr.umlv.ir3.corba.sguincha.td2.ForumOperations#getMessages()
	 */
	public Message [] getMessages ()
	{
		return (Message []) discussion.values ().toArray (new Message [discussion.size ()]);
	}

	/**
	 * @see fr.umlv.ir3.corba.sguincha.td2.ForumOperations#removeMessage(java.lang.String)
	 */
	public boolean removeMessage (String title)
	{
		if (discussion.containsKey (title))
		{
			discussion.remove (title);
			return true;
		}
		return false;
	}
}
