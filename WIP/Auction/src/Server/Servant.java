package Server;

import RemoteInterfaces.Observer;
import RemoteInterfaces.*;
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

public class Servant extends UnicastRemoteObject implements ServantInterface {
	/*
	COLLECTIONS
	 */
	private List<Observer> subscribedClients;
	private Hashtable<String, User> registeredUsers;
	private Hashtable<Integer, Offer> placedOffers;
	
	/*
	CONSTRUCTOR
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
		GETTERS AND SETTERS
	 */
	public List<Observer> getSubscribedClients() {
		return subscribedClients;
	}
	
	public Hashtable <String, User> getRegisteredUsers () {
		return registeredUsers;
	}
	
	public Hashtable<Integer, Offer> getPlacedOffers() {
		return placedOffers;
	}
	
	/*
	CLIENT RELATED METHODS
	 */
	
	@Override
	public void attach(Object observer) throws RemoteException {
		System.out.println("Adding new client observer...");
		subscribedClients.add((Observer) observer);
		((Observer) observer).test();
		System.out.println("New client added! Clients currently subscribed: " + subscribedClients.size());
	}
	
	@Override
	public void detach(Object observer) throws RemoteException {
		System.out.println("Unsubscribing a client...");
		subscribedClients.remove((Observer) observer);
		System.out.println("Client unsubscribed. Clients currently subscribed: " + subscribedClients.size());
	}
	
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
	
	@Override
	public void displayUsers() throws RemoteException {
		System.out.println("Users registered: ");
		for (User user : registeredUsers.values())
			user.display();
	}
	
	@Override
	public User seekUser(String nickname) throws RemoteException {
		System.out.println("Seeking user: " + nickname);
		System.out.println("User " + (registeredUsers.containsKey(nickname) ? "found!" : " not found"));
		return registeredUsers.get(nickname);
	}
	
	
	/*
	OFFER RELATED METHODS
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
	
	@Override
	public Offer seekOffer(String name, LocalDate deadline) throws RemoteException {
		//it seems this method ain't really used
		return null;
	}
	
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
	
	@Override
	public void displayOffers() throws RemoteException {
		System.out.println("Placed offers: ");
		for (Offer offer : placedOffers.values())
			offer.display();
	}
	
	@Override
	public Hashtable<Integer, Offer> getCurrentOffers () throws RemoteException {
		return placedOffers;
	}
}
