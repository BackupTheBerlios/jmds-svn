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
	
	public ForumImpl(String theme, String moderator)
	{
		this.theme = theme;
		this.moderator = moderator;
		discussion = new ConcurrentHashMap();
	}
	
	public String theme() {
		return theme;
	}

	public String moderator() {
		return moderator;
	}

	public boolean postMessage(String title, org.omg.CORBA.Any m) throws Reject{
		if(discussion.putIfAbsent(title, m) == null)
			return true;
		return false;
	}

	public Any getMessage(String title)  throws Reject{
		if(!discussion.containsKey(title))
			throw new Reject("Title unknow", "Pas de message ayant ce titre: " + title);
		return (Any) discussion.get(title);
	}

	public void removeMessage(String title) throws Reject {
		if(discussion.containsKey(title))
		{
			discussion.remove(title);
		}
	}
	
	 public Any[] getMessages () {
	 	return (Any[]) discussion.values().toArray(new Any[discussion.size()]);
	 }
	 
	 public void getInfo (StringHolder theme, StringHolder moderator, IntHolder size) {
	 	theme.value = this.theme;
	 	moderator.value = this.moderator;
	 	size.value = discussion.size();
	 }
}
