package RemoteInterfaces;

import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Hashtable;

/**
 * Single remote interface to be implemented by the servant. Defines all the methods a remote client can invoke/request
 * to the server
 *
 * This interface contains methods related to several subjects, it contains methods for manage the registered users,
 * for create offers of product and manage bids performed to those offers.
 * It also defines methods for the client to subscribe, unsubscribe and perform the required dispersion of data when
 * the attribute of client's interest changes (in a more general way, these last 3 methods should be defined in a
 * separated interface called Subject to finish the complete concept of the Observer Design Pattern and be implemented
 * by the servant, but they were left in the same interface for this not to confront against what was learned in DS class
 */
public interface ServantInterface extends Remote {
	/*
	SUBJECT RELATED METHODS
	 */

	/**
	 * Subscribe a new client for receive updates when a certain change occurs
	 * @param observer a reference to the client that wants to receive updates
	 * @throws RemoteException
	 */
	void attach (Object observer) throws RemoteException;

	/**
	 * Subscribe a new client for receive updates when a certain change occurs
	 * @param observer a reference to the client that wants to stop receiving updates
	 * @throws RemoteException
	 */
	void detach (Object observer) throws RemoteException;

	/**
	 * When a certain change occurs, transmit the notification to all the nodes subscribed
	 * @throws RemoteException
	 */
	void notifyClients () throws RemoteException;
	
	/*
	USER RELATED METHODS
	 */

	/**
	 * Register a new user with this data
	 * @param name real name of the new user [required]
	 * @param nickname nickname of the new user (must be unique) [required]
	 * @param email email address of the new uer [optional]
	 * @param address physical address of the new user [optional]
	 * @param phone phone number of the new user [optional]
	 * @return true if the user was correctly registered, false otherwise
	 * @throws RemoteException
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
