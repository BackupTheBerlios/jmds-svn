/*
 * Created on 1 oct. 2004
 */
package de.berlios.jmds.server.core;

import java.util.concurrent.ConcurrentHashMap;

import de.berlios.jmds.generated.ForumPOA;
import de.berlios.jmds.generated.Message;



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
	 * @see de.berlios.jmds.generated.ForumOperations#theme()
	 */
	public String theme ()
	{
		return theme;
	}

	
	/**
	 * @see de.berlios.jmds.generated.ForumOperations#moderator()
	 */
	public String moderator ()
	{
		return moderator;
	}

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#postMessage(de.berlios.jmds.generated.Message)
	 */
	public boolean postMessage (Message m)
	{
		if (discussion.putIfAbsent (theme , m) == null)
			return true;
		return false;
	}

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#getMessage(java.lang.String)
	 */
	public Message getMessage (String title)
	{
		return (Message) discussion.get (title);
	}

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#getMessages()
	 */
	public Message [] getMessages ()
	{
		return (Message []) discussion.values ().toArray (new Message [discussion.size ()]);
	}

	/**
	 * @see de.berlios.jmds.generated.ForumOperations#removeMessage(java.lang.String)
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
