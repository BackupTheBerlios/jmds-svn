/*
 * Created on 1 oct. 2004
 */
package fr.umlv.ir3.corba.forum;

import java.util.concurrent.ConcurrentHashMap;
import org.omg.CORBA.*;

/**
 * @author jguers
 */
public class ForumImpl extends ForumPOA {

	private String theme;
	private String moderator;
	private ConcurrentHashMap discussion;
	
	/**
	 * @param theme
	 * @param moderator
	 */
	public ForumImpl(String theme, String moderator)
	{
		this.theme = theme;
		this.moderator = moderator;
		discussion = new ConcurrentHashMap();
	}
	
	/**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#theme()
	 */
	public String theme() {
		return theme;
	}

	/**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#moderator()
	 */
	public String moderator() {
		return moderator;
	}

	/**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#postMessage(java.lang.String, org.omg.CORBA.Any)
	 */
	public boolean postMessage(String title, org.omg.CORBA.Any m) throws Reject{
		if(discussion.putIfAbsent(title, m) == null)
			return true;
		return false;
	}

	/**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#getMessage(java.lang.String)
	 */
	public Any getMessage(String title)  throws Reject{
		if(!discussion.containsKey(title))
			throw new Reject("Title unknow", "Pas de message ayant ce titre: " + title);
		return (Any) discussion.get(title);
	}

	/**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#removeMessage(java.lang.String)
	 */
	public void removeMessage(String title) throws Reject {
		if(discussion.containsKey(title))
		{
			discussion.remove(title);
		}
	}
	
	 /**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#getMessages()
	 */
	public Any[] getMessages () {
	 	return (Any[]) discussion.values().toArray(new Any[discussion.size()]);
	 }
	 
	 /**
	 * @see fr.umlv.ir3.corba.forum.ForumOperations#getInfo(org.omg.CORBA.StringHolder, org.omg.CORBA.StringHolder, org.omg.CORBA.IntHolder)
	 */
	public void getInfo (StringHolder theme, StringHolder moderator, IntHolder size) {
	 	theme.value = this.theme;
	 	moderator.value = this.moderator;
	 	size.value = discussion.size();
	 }
}
