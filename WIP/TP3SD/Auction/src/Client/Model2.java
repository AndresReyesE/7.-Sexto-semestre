package Client;

import Interfaces.ClientInterface;
import Interfaces.ModelInterface;
import Interfaces.OfferInterface;
import Interfaces.UserInterface;
import RemoteObjects.Bid;
import RemoteObjects.Offer;
import RemoteObjects.User;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Hashtable;


public class Model2 implements ModelInterface, Remote {
	/*
	MODEL ATTRIBUTES
	 */
	private Registry registry;
	
	private User currentlyLoggedInAs;
	
	private ControllerMediator controllerMediator;
	
	/*
	USERS ATTRIBUTES
	 */
	private Hashtable<String, User> registeredUsers;
	
	/*
	OFFERS ATTRIBUTES
	 */
	private Hashtable<Integer, Offer> placedOffers;
	
	/*
	ACTUAL "MODEL" FUNCTIONALITY
	 */
	Model2 (String host) {
		currentlyLoggedInAs = null;
		registeredUsers = new Hashtable<>();
		placedOffers = new Hashtable<>();
//		try {

//			controllerMediator = med;
//			registry = LocateRegistry.getRegistry(host, 5000);

//			users = (UserInterface) registry.lookup("//localhost:5000/Users");
//			offers = (OfferInterface) registry.lookup("//localhost:5000/Offers");
//			users = (UserInterface) registry.lookup("Users");
//			offers = (OfferInterface) registry.lookup("Offers");
//			clients = (ClientInterface) registry.lookup("Clients");
		
//		} catch (Exception e) {
//			StringWriter outError = new StringWriter();
//			e.printStackTrace(new PrintWriter(outError));
//			String errorString = outError.toString();
//			System.out.println(errorString);
//		}
	}
	
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
	/*
	USERS IMPLEMENTATIONS
	 */
	
//	public Users() {
//		this.registeredUsers =  new Hashtable<>();
//	}
	
	public Hashtable <String, User> getRegisteredUsers () {
		return registeredUsers;
	}
	
	@Override
	public boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException {
		System.out.println("Registering user in Users object in server side: " + name + ", " + nickname + " | " + email + " | " + address + " : " + phone);
		
		if (registeredUsers.containsKey(nickname))
			return false;
		
		User newUser = new User (name, nickname, email, address, phone);
		registeredUsers.put(nickname, newUser);
		
		System.out.println("User registered!");
		return true;
	}
	
	@Override
	public void displayUsers () throws RemoteException {
		System.out.println("Users registered: ");
		for (User user : registeredUsers.values()) {
			user.display();
		}
	}
	
	@Override
	public boolean login (String nickname) {
		System.out.println("Logging in the user " + nickname + "...");
		User found = seekUser(nickname);
		String result = found == null ? " is not associated with any account" : " is now logged in";
		System.out.println("The user " + nickname + result);
		
		controllerMediator.updateUserLoggedIn(found == null ? "" : found.getNickname());
		
		this.currentlyLoggedInAs = found;
		
		return found != null;
	}
	
//	@Override
	public User seekUser (String nickname) {
		return registeredUsers.get(nickname);
	}
	
//	@Override
	boolean addOfferToUser (Offer offer) {
		User user = currentlyLoggedInAs;
		
		if (user != null)       {
			user.addOffer(offer);
			registeredUsers.put(currentlyLoggedInAs.getNickname(), user);
		}
		
		return user != null;
	}
	
	/*
	OFFERS IMPLEMENTATIONS
	 */
	
//	public Offers() {
//		this.placedOffers = new Hashtable<>();
//	}
	
	@Override
	public Hashtable<Integer, Offer> getPlacedOffers() throws RemoteException {
		return placedOffers;
	}
	
	@Override
	public void addOffer(String name, String description, String initialPrice, LocalDate deadline) throws RemoteException {
		double price = Double.parseDouble(initialPrice);
		
		int id = placedOffers.size() + 1;
		Offer newOffer = new Offer(id, name, description, price, deadline);
		
		placedOffers.putIfAbsent(id, newOffer);
		
		addOfferToUser(newOffer);
		
		displayOffers();
		displayUsers();
	}
	
	@Override
	public Offer seekOffer(String name, LocalDate deadline) throws RemoteException {
		for (Offer offer : placedOffers.values()) {
			if (offer.getName().equals(name) && offer.getDeadline().equals(deadline))
				return offer;
		}
		return null;
	}
	
	@Override
	public void newBid(int offerID, double bid) throws RemoteException {
		System.out.println("New bid over offer: " + offerID + " by " + currentlyLoggedInAs.getNickname() + ": " + bid);
		Offer offerConcerned = placedOffers.get(offerID);
		Bid bidOffered = new Bid(currentlyLoggedInAs.getNickname(), bid);
		
		offerConcerned.setCurrentBid(bid);
		offerConcerned.addToHistory(bidOffered);
		
		placedOffers.remove(offerID);
		placedOffers.put(offerID, offerConcerned);
		
		displayOffers();
	}
	
	@Override
	public void displayOffers () throws RemoteException {
		System.out.println("Placed offers: ");
		for (Offer offer : placedOffers.values()) {
			offer.display();
		}
	}
}
