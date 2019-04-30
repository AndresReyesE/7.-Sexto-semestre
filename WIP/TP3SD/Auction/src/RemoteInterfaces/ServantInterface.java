package RemoteInterfaces;

import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Hashtable;

/**
 * Single remote interface to be implemented by the servant. Defines all the methods a remote client can invoke/request to the server
 */
public interface ServantInterface extends Remote {
	/*
	SUBJECT RELATED METHODS
	 */
	void attach (Object observer) throws RemoteException;
	
	void detach (Object observer) throws RemoteException;
	
	void notifyClients () throws RemoteException;
	
	/*
	USER RELATED METHODS
	 */
	boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException;
	
	User seekUser (String nickname) throws RemoteException;
	
	void displayUsers () throws RemoteException;
	
	/*
	OFFER RELATED METHODS
	 */
	
	void addOffer (String nickname, String name, String description, double initialPrice, LocalDate deadline) throws RemoteException;
	
	Offer seekOffer (String name, LocalDate deadline) throws RemoteException;
	
	void newBid (int offerID, String bidder, double bid) throws RemoteException;
	
	Hashtable<Integer, Offer> getCurrentOffers () throws RemoteException;
	
	void displayOffers () throws RemoteException;
}
