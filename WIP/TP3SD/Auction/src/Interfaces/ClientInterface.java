package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {
	void subscribeClient (String name) throws RemoteException;
	
	int getClientsNumber ()throws RemoteException;
	
	ArrayList<CallbackInterface> getClients () throws RemoteException;
	
	void notifyClients() throws RemoteException;
}
