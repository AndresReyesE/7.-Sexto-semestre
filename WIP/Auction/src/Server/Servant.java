package Server;

import RemoteInterfaces.Observer;
import RemoteInterfaces.ServantInterface;
import RemoteObjects.Bid;
import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * Remote object that implements the remote interface named "ServantInterface"
 * and besides extends the abstract class UnicastObjectRemote, meaning that as soon as an
 * instance of this class is created, the resultant object will be already exported as a remote object.
 *
 * Offers a concrete implementation of all the remote methods defined in the interface,
 * store the collections with all the data of this session,
 *
 */
public class Servant extends UnicastRemoteObject implements ServantInterface {
	
	/*
	COLLECTIONS THAT STORE ALL THE USERS REGISTERED; ALL THE PLACED OFFERS AND ALL THE CLIENTS THAT ARE
	SUBSCRIBED TO THIS SESSION
	 */
	private List<Observer> subscribedClients;
	private Hashtable<String, User> registeredUsers;
	private Hashtable<Integer, Offer> placedOffers;
	
	/**
	 * Initializes the collections and invoke the super Constructor (from UnicastObjectRemote), such that the instances
	 * of this class are always exported as remote objects
	 */
	Servant () throws RemoteException {
		super();
		try {
			subscribedClients = Collections.synchronizedList(new ArrayList<>());
			registeredUsers = new Hashtable<>();
			placedOffers = new Hashtable<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	CLIENT (SUBJECT) RELATED METHODS
	 */
	
	/**
	 * Aggregate a generic object to the list of subscribed clients.
	 * The indicated observer must implement the interface Observer,
	 * otherwise it will be automatically eliminated as soon as the function notifyClients is called
	 * @param observer a reference to the client that wants to receive updates
	 * @throws RemoteException
	 */
	@Override
	public void attach(Object observer) throws RemoteException {
		System.out.println("Adding new client observer...");
		subscribedClients.add((Observer) observer);
		((Observer) observer).test();
		System.out.println("New client added! Clients currently subscribed: " + subscribedClients.size());
	}
	
	/**
	 * Remove an observer from the list of subscribed clients
	 * @param observer a reference to the client that wants to stop receiving updates
	 * @throws RemoteException
	 */
	@Override
	public void detach(Object observer) throws RemoteException {
		System.out.println("Unsubscribing a client...");
		subscribedClients.remove((Observer) observer);
		System.out.println("Client unsubscribed. Clients currently subscribed: " + subscribedClients.size());
	}
	
	/**
	 * Whenever a change occurs in the placedOffers collection, every object (reference) in subscribedClients will
	 * be notified by sending them a copy of the updated collection
	 * These methods work as the Callback of this application
	 * @throws RemoteException
	 */
	@Override
	public void notifyClients() throws RemoteException {
		for (Observer observer : subscribedClients)
			try {
				User temp = seekUser(observer.getID());
				observer.update(temp, placedOffers);
			} catch (RemoteException e) {
				System.out.println("Server.Servant: RemoteException while notifying an observer. Removing it from observers");
				subscribedClients.remove(observer);
			}
	}
	
	/*
	USER RELATED METHODS
	 */
	
	/**
	 * Register a new user with this data, if successful is aggregated to the registeredClients collection
	 * @param name real name of the new user [required]
	 * @param nickname nickname of the new user (must be unique) [required]
	 * @param email email address of the new uer [optional]
	 * @param address physical address of the new user [optional]
	 * @param phone phone number of the new user [optional]
	 * @return true if the user was correctly registered, false otherwise
	 * @throws RemoteException
	 */
	@Override
	public boolean registerUser(String name, String nickname, String email, String address, String phone) throws RemoteException {
		System.out.println("Registering user in Users object in server side: " + name + ", " + nickname + " | " + email + " | " + address + " : " + phone);
		
		if (registeredUsers.containsKey(nickname))
			return false;
		
		User newUser = new User (name, nickname, email, address, phone);
		registeredUsers.put(nickname, newUser);
		
		System.out.println("User registered!");
		displayUsers();
		return true;
	}
	
	/**
	 * Search for an user with a determined nickname
	 * @param nickname the nickname of the user to be searched
	 * @return copy of the User object associated with that nickname or null if no user has that nickname
	 * @throws RemoteException
	 */
	@Override
	public User seekUser(String nickname) throws RemoteException {
		System.out.println("Seeking user: " + nickname);
		System.out.println("User " + (registeredUsers.containsKey(nickname) ? "found!" : " not found"));
		return registeredUsers.get(nickname);
	}
	
	private void displayUsers() throws RemoteException {
		System.out.println("Users registered: ");
		for (User user : registeredUsers.values())
			user.display();
	}
	
	/*
	OFFER RELATED METHODS
	 */
	
	/**
	 * Place a new offer with this data, if successful the offer is added to the placedOffers collection
	 * @param nickname nickname of the user who's placing the offer
	 * @param name name of the product to be offered
	 * @param description brief description of the product
	 * @param initialPrice price to be used as lower threshold for the consequent bids
	 * @param deadline date when the auction will be counted as expired or finished
	 * @throws RemoteException
	 */
	@Override
	public void addOffer(String nickname, String name, String description, double initialPrice, LocalDate deadline) throws RemoteException {
		System.out.println("Creating a new offer...");
		int id = placedOffers.size() + 1;
		Offer newOffer = new Offer(id, name, description, initialPrice, deadline);
		
		placedOffers.put(id, newOffer);
		addOfferToUser(nickname, newOffer);
		displayOffers();
		notifyClients();
	}
	
	/**
	 * Whenever an offer is aggregated to the global list of offers it is also added a copy to the
	 * user who add the offer
	 * @param nickname nickname of the user who added the offer
	 * @param offer a reference to the offer aggregated
	 */
	private void addOfferToUser(String nickname, Offer offer) {
		System.out.println("Linking the new offer to the user...");
		User user = registeredUsers.get(nickname);
		
		if (user != null) {
			user.addOffer(offer);
			registeredUsers.put(nickname, user);
			System.out.println(nickname + " is now selling a " + offer.getName() + " at " + offer.getInitialPrice());
		}
		else
			System.out.println("Somehow the user doesn't exist, please check me");
	}
	
	/**
	 * Perform a new bid over the indicated offer, if all the data is correct, the offer concerned is
	 * updated with a new bid in its history
	 * @param offerID ID of the offer that the user wants to bid over
	 * @param bidder nickname of the client who's planting the bid
	 * @param bid amount of money greater than the current highest bid for that offer
	 * @throws RemoteException
	 */
	@Override
	public void newBid(int offerID, String bidder, double bid) throws RemoteException {
		System.out.println("Offering a new bid...");
		Offer offerConcerned = placedOffers.get(offerID);
		Bid bidOffered = new Bid(bidder, bid);
		
		
		offerConcerned.setCurrentBid(bid);
		offerConcerned.setCurrentBidder(bidder);
		offerConcerned.addToHistory(bidOffered);
		
		placedOffers.remove(offerID);
		placedOffers.put(offerID, offerConcerned);
		
		System.out.println("New bid over " + offerConcerned.getName() + " by " + bidder + ". New highest bid: " + bid);
		notifyClients();
	}
	
	/**
	 * Provides a copy updated of the placedOffers collection
	 * @return a copy of the server's collection of all the offers placed so far
	 * @throws RemoteException
	 */
	@Override
	public Hashtable<Integer, Offer> getCurrentOffers () throws RemoteException {
		return placedOffers;
	}
	
	public void displayOffers() {
		System.out.println("Placed offers: ");
		for (Offer offer : placedOffers.values())
			offer.display();
	}
}
