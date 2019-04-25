package Interfaces;

import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Hashtable;

public interface ModelInterface extends Remote {
	void addOffer (String name, String description, String initialPrice, LocalDate deadline) throws RemoteException;
	
	Offer seekOffer (String name, LocalDate deadline) throws RemoteException;
	
	void newBid (int offerID, double bid) throws RemoteException;
	
	Hashtable<Integer, Offer> getPlacedOffers() throws RemoteException;
	
	void displayOffers () throws RemoteException;
	
	/* USERS METHODS */
	
	boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException;
	
	void displayUsers () throws RemoteException;
	
//	User seekUser (String nickname) throws RemoteException;
	
	boolean login (String nickname) throws RemoteException;
	
//	boolean addOfferToUser (String nickname, Offer offer) throws RemoteException;
}
