/* 
 * File    : MyParseServiceContext.java
 * Created : 10 mars. 2005
 * 
 * =======================================
 * JMDS PROJECT ("http://jmds.berlios.de")
 * =======================================
 *
 */
package de.berlios.jmds.tools;

/**
 * Allow parse of service cntext to find contain information (session, clientID and RequestID)
 * @author Denis
 */
public class MyParseServiceContext {
	
	
	//----------------------------------------------------------//
	//------------------- PRIVATE METHODS -----------------------//
	//----------------------------------------------------------//
	
	/**
	 * @param serviceContext
	 * @return the array of byte witch contain the session
	 */
	public final static byte[]  findSession (byte[] serviceContext) {
		byte[] session = new byte[5];
		for (int i = 0; i<session.length; i++)
			session[i] = serviceContext[i];	
		return session ;
	}

	/**
	 * @param serviceContext
	 * @return the array of byte witch contain the clientID
	 */
	public final static byte[] findClientID (byte[] serviceContext) {
		byte[] clientID = new byte[10] ;
		for (int i = 0; i<clientID.length; i++)
			clientID [i] = serviceContext[i+5];	
		return clientID ;
	}

	/**
	 * @param serviceContext
	 * @return the array of byte witch contain the requestID
	 */
	public final static byte[] findRequestID (byte[] serviceContext) {
		byte[] requestID = new byte[serviceContext.length - 15] ;	
		for (int i = 0; i<requestID .length; i++)
			requestID [i] = serviceContext[i+15];	
		return requestID ;
	}	
}
