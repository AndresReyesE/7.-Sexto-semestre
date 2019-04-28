package Observer;

import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface Observer extends Remote {
	
	void update (User updatedUser, Hashtable<Integer, Offer> news) throws RemoteException;
	
	void test () throws RemoteException;
	
	String getID () throws RemoteException;
}
