package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackInterface extends Remote {
	void update () throws RemoteException;
	
}
