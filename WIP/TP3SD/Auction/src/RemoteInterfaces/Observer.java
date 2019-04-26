package RemoteInterfaces;

import RemoteObjects.Offer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface Observer extends Remote {
	void update (Hashtable<Integer, Offer> news) throws RemoteException;
	
	void test () throws RemoteException;
}
