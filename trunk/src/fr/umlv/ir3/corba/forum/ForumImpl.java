package fr.umlv.ir3.corba.forum;

import java.util.concurrent.ConcurrentHashMap;

import org.omg.CORBA.*;

public class ForumImpl extends ForumPOA {
  /**
   * Table de hachage contenant l'assocication entre un nom
   * et un message.
   * L'acc�s concurrent � la table est permis.
   */
  private ConcurrentHashMap messages = new ConcurrentHashMap();
  /** Th�me du forum */
  private String theme;
  /** Nom de l'administrateur */
  private String moderator;
  
  /** 
   * Constructeur d'un forum appel� par la fabrique.
   * @param name nom du forum cr��.
   * @param theme th�me du forum.
   * @param moderator nom de l'administrateur du forum.
   */
  ForumImpl(String theme, String moderator) {
    this.theme = theme;
    this.moderator = moderator;
  }
  
  /** 
   * Retourne le th�me du forum.
   */
  public String theme() {
    return theme;
  }

  /** 
   * Retourne le nom de l'administrateur du forum.
   */
  public String moderator() {
    return moderator;
  }
  /**
   * Poste un message dans le forum.
   * @param m le message � poster.
   * @return false s'il y a d�j� un message avec le m�me titre.
   */
  public void postMessage(String title, Any m) throws Reject {
    Any mInMap = (Any)messages.putIfAbsent(title,m);
    if (mInMap != null) {
      throw new Reject("PostMessage : message with title "+
		       title + " already exists!");
    }
  }
  /** 
   * R�cup�re le message dont le titre est pass� en argument.
   * @param title le titre du message � r�cup�rer.
   * @return le message ou null s'il n'existe pas de message
   * ayant ce titre.
   */
  public Any getMessage(String title) throws Reject{
    Any m = (Any)messages.get(title);
    if (m == null) {
      throw new Reject("GetMessage : message with title "+title+
		       " does not exist!");
    }
    return m;
  }
  
  /** 
   * Supprime un message du forum.
   * @param title le titre du message � retirer du forum.
   * @return false si le message n'est pas pr�sent dans le forum.
   */
  public void removeMessage(String title) throws Reject {
    if (messages.remove(title) == null) {
      throw new Reject("RemoveMessage : message " + title +
		       " does not exist!");
    }
  }
    
  /**
   * Retourne la liste des messages du forum.
   * @return la liste des messages
   */
  public Any[] messageList() {
    return (Any[]) messages.values().toArray(new Any[messages.size()]);
  }
  
  /**
   * Retourne dans les arguments les informations sur le forum.
   */
  public void getInfo(StringHolder theme, StringHolder moderator,
		      IntHolder size) {
    theme.value = this.theme;
    moderator.value = this.moderator;
    size.value = messages.size();
  }
}
