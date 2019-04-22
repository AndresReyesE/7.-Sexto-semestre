package Interfaces;

import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInterface extends Remote {
	boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException;
	
	void displayUsers () throws RemoteException;
	
	User seekUser (String nickname) throws RemoteException;
	
	boolean addOfferToUser (String nickname, Offer offer) throws RemoteException;
}
