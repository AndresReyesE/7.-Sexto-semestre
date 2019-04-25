package Client;

import Interfaces.CallbackInterface;
import Interfaces.ClientInterface;
import Interfaces.OfferInterface;
import Interfaces.UserInterface;
import Observer.Subject;
import RemoteObjects.Offer;
import RemoteObjects.User;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Hashtable;

class Model extends Subject {
	
	//	AuctionController auctionController;
	ControllerMediator controllerMediator;
	
	
	private UserInterface users;
	private OfferInterface offers;
	private ClientInterface clients;
	
	private Registry registry;
	
	private User currentlyLoggedInAs;
	
//	Model() {}
//
//	Model (String host) {
//		currentlyLoggedInAs = null;
//		try {
//
////			controllerMediator = med;
//			registry = LocateRegistry.getRegistry(host, 5000);
//
////			users = (UserInterface) registry.lookup("//localhost:5000/Users");
////			offers = (OfferInterface) registry.lookup("//localhost:5000/Offers");
//			users = (UserInterface) registry.lookup("Users");
//			offers = (OfferInterface) registry.lookup("Offers");
//			clients = (ClientInterface) registry.lookup("Clients");
//
//		} catch (Exception e) {
//			StringWriter outError = new StringWriter();
//			e.printStackTrace(new PrintWriter(outError));
//			String errorString = outError.toString();
//			System.out.println(errorString);
//		}
//	}
	
	void bindCallback () {
		try {
			Callback callback = (Callback) controllerMediator.retrieveColleague("Callback");
			CallbackInterface stubCallback = (CallbackInterface) UnicastRemoteObject.exportObject(callback, 0);
			
			int numberOfClients = clients.getClientsNumber();
			
			registry.rebind("Client" + numberOfClients, stubCallback);
			
			clients.subscribeClient("Client" + numberOfClients);
		}
		catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
	}
	
//	void setControllerMediator (ControllerMediator cm) {
//		this.controllerMediator = cm;
//	}
//
//	boolean signUp (String name, String nickname, String email, String address, String phone) {
//		try {
//			System.out.println("Registering user in Model with data: " + name + ", " + nickname + " | " + email + " | " + address + " : " + phone);
//			boolean userRegistered = users.registerUser(name, nickname, email, address, phone);
//
//			users.displayUsers();
//			return userRegistered;
//		}
//		catch (RemoteException re) {
//			System.err.println("Error registering user from Client Side!");
//			StringWriter outError = new StringWriter();
//			re.printStackTrace(new PrintWriter(outError));
//			String errorString = outError.toString();
//			System.out.println(errorString);
//		}
//		return false;
//	}
//
//	boolean login (String nickname) {
//		try {
//			System.out.println("Logging in the user " + nickname + "...");
//			User found = users.seekUser(nickname);
//			String result = found == null ? " is not associated with any account" : " is now logged in";
//			System.out.println("The user " + nickname + result);
//
//			controllerMediator.updateUserLoggedIn(found == null ? "" : found.getNickname());
//
//			this.currentlyLoggedInAs = found;
//
//			return found != null;
//		}
//		catch (RemoteException re) {
//			System.err.println("Error logging in user from Client Side!");
//			StringWriter outError = new StringWriter();
//			re.printStackTrace(new PrintWriter(outError));
//			String errorString = outError.toString();
//			System.out.println(errorString);
//		}
//		return false;
//	}
	
	void addOffer (String offerName, String offerDescription, String initialPrice, LocalDate offerDeadline) {
		try {
			double price = Double.parseDouble(initialPrice);
			
			offers.addOffer(offerName, offerDescription, price, offerDeadline);
			
			users.addOfferToUser(currentlyLoggedInAs.getNickname(), offers.seekOffer(offerName, offerDeadline));
//			User user = users.seekUser(currentlyLoggedInAs.getNickname());
//			user.addOffer(offers.seekOffer(offerName, offerDeadline));
			clients.notifyClients();
			offers.displayOffers();
			users.displayUsers();
		} catch (RemoteException re) {
			System.err.println("Error adding offer from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
	}
	
	User getCurrentlyLoggedInAs() {
		return currentlyLoggedInAs;
	}
	
	Hashtable <Integer, Offer> getCurrentOffers() {
		try {
			return offers.getPlacedOffers();
		} catch (RemoteException re) {
			System.err.println("Error retrieving offers from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
		return null;
	}
	
	void addBid (int offerId, double bid) {
		try {
			offers.newBid(offerId, currentlyLoggedInAs.getNickname(), bid);
			offers.displayOffers();
//			controllerMediator.updateOffersList();
			clients.notifyClients();
		} catch (RemoteException re) {
			System.err.println("Error retrieving offers from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
	}
	
	@Override
	public void attach(Object observer) {
	
	}
	
	@Override
	public void detach(Object observer) {
	
	}
	
	@Override
	public void notifyObservers() {
	
	}
}
