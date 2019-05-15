/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import java.rmi.RemoteException;
import javax.ejb.Remote;
import RemoteObjects.*;
import java.time.LocalDate;
import java.util.Hashtable;

/**
 *
 * @author reyes
 */
@Remote
public interface SessionBeanRemote {
	
	/*
	USER RELATED METHODS
	 */

	/**
	 * Register a new user with this data
	 * @param name real name of the new user [required]
	 * @param nickname nickname of the new user (must be unique) [required]
	 * @param email email address of the new user [optional]
	 * @param address physical address of the new user [optional]
	 * @param phone phone number of the new user [optional]
	 * @return true if the user was correctly registered, false otherwise
	 * @throws RemoteException
	 */
	boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException;
	
	/**
	 * Search for an user with a determined nickname
	 * @param nickname the nickname of the user to be searched
	 * @return a copy of the User object associated with that nickname or null if no user has that nickname
	 * @throws RemoteException
	 */
	User seekUser (String nickname) throws RemoteException;
	
	
	
	/*
	OFFER RELATED METHODS
	 */
	
	/**
	 * Place a new offer with this data
	 * @param nickname nickname of the user who's placing the offer
	 * @param name name of the product to be offered
	 * @param description brief description of the product
	 * @param initialPrice price to be used as lower threshold for the consequent bids
	 * @param deadline date when the auction will be counted as expired or finished
	 * @throws RemoteException
	 */
	void addOffer (String nickname, String name, String description, double initialPrice, LocalDate deadline) throws RemoteException;
	
	/**
	 * Perform a new bid over the indicated offer
	 * @param offerID ID of the offer that the user wants to bid over
	 * @param bidder nickname of the client who's planting the bid
	 * @param bid amount of money greater than the current highest bid for that offer
	 * @throws RemoteException
	 */
	void newBid (int offerID, String bidder, double bid) throws RemoteException;
	
	/**
	 * Request an updated version of the offers collection
	 * @return a copy of the server's collection of all the offers placed so far
	 * @throws RemoteException
	 */
	Hashtable<Integer, Offer> getCurrentOffers () throws RemoteException;
        
       void test ();
}
