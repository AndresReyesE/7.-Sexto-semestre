package RemoteInterfaces;

import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

/**
 * Abstract structure of an Observer
 * Pattern design that implies dependencies '1 to n' where the 1 represents a Subject and the n an arbitrary amount of Observers
 *
 * This pattern works in a similar way than a publish-subscribe architecture, the object working as Subject maintains a "state" attribute which
 * will be the 'subject' the Observers subscribe to, in such a way that when this attribute change in the Subject Implementation all the observers should be notified of this change.
 *
 * Any object that may want to be notified about a change should implement this interface and offer a implementation for the method update.
 * In this punctual project this interface is used for the callback is implemented in client's side for receive the updates from the server. The other methods are application-specific
 * and are only used to get client's information when required
 */
public interface Observer extends Remote {
	
	void update (User updatedUser, Hashtable<Integer, Offer> news) throws RemoteException;
	
	void test () throws RemoteException;
	
	String getID () throws RemoteException;
}
