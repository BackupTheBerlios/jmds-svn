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

	public ForumImpl (String theme, String moderator)
	{
		this.theme = theme;
		this.moderator = moderator;
		discussion = new ConcurrentHashMap ();
	}

	public String theme ()
	{
		return theme;
	}

	public String moderator ()
	{
		return moderator;
	}

	public boolean postMessage (Message m)
	{
		if (discussion.putIfAbsent (theme , m) == null)
			return true;
		return false;
	}

	public Message getMessage (String title)
	{
		return (Message) discussion.get (title);
	}

	public Message [] getMessages ()
	{
		return (Message []) discussion.values ().toArray (new Message [discussion.size ()]);
	}

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
