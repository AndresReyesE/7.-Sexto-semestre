package Client;

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
import java.time.LocalDate;
import java.util.Hashtable;

class Model extends Subject {
	
//	AuctionController auctionController;
	ControllerMediator controllerMediator;
	
	private Registry registry;
	
	private UserInterface users;
	private OfferInterface offers;
	
	private User currentlyLoggedInAs;
	
	Model (String host) {
		currentlyLoggedInAs = null;
		try {
			registry = LocateRegistry.getRegistry(host);
			
//			users = (UserInterface) registry.lookup("//localhost:5000/Users");
//			offers = (OfferInterface) registry.lookup("//localhost:5000/Offers");
			users = (UserInterface) registry.lookup("Users");
			offers = (OfferInterface) registry.lookup("Offers");
			
			
		} catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
	}
	
//	public AuctionController getAuctionController() {
//		return auctionController;
//	}
//
//	void setAuctionController(AuctionController auctionController) {
//		this.auctionController = auctionController;
//	}
	
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
	boolean signUp (String name, String nickname, String email, String address, String phone) {
		try {
			System.out.println("Registering user in Model with data: " + name + ", " + nickname + " | " + email + " | " + address + " : " + phone);
			boolean userRegistered = users.registerUser(name, nickname, email, address, phone);
			
			users.displayUsers();
			return userRegistered;
		}
		catch (RemoteException re) {
			System.err.println("Error registering user from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
		return false;
	}
	
	boolean login (String nickname) {
		try {
			System.out.println("Logging in the user " + nickname + "...");
			User found = users.seekUser(nickname);
			String result = found == null ? " is not associated with any account" : " is now logged in";
			System.out.println("The user " + nickname + result);
			
			controllerMediator.updateUserLoggedIn(found == null ? "" : found.getNickname());
			controllerMediator.updateOffersList();
			
			this.currentlyLoggedInAs = found;
			
			return found != null;
		}
		catch (RemoteException re) {
			System.err.println("Error logging in user from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
		return false;
	}
	
	void addOffer (String offerName, String offerDescription, String initialPrice, LocalDate offerDeadline) {
		try {
			double price = Double.parseDouble(initialPrice);
			
			offers.addOffer(offerName, offerDescription, price, offerDeadline);
			
			users.addOfferToUser(currentlyLoggedInAs.getNickname(), offers.seekOffer(offerName, offerDeadline));
//			User user = users.seekUser(currentlyLoggedInAs.getNickname());
//			user.addOffer(offers.seekOffer(offerName, offerDeadline));
			
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
