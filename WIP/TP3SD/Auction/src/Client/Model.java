package Client;

import RemoteInterfaces.Observer;
import RemoteInterfaces.ServantInterface;
import RemoteObjects.Offer;
import RemoteObjects.User;
import Observer.Subject;
import javafx.application.Platform;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

class Model extends UnicastRemoteObject implements Observer {
	/*
	MEDIATOR ATTRIBUTES
	 */
	private ControllerMediator controllerMediator;
	
	/*
	RMI AND REFERENCES REQUIRED ATTRIBUTES
	 */
	private ServantInterface servant;
	
	/*
	CLIENT RELATED ATTRIBUTES
	 */
	private User currentlyLoggedInAs;
	private Hashtable <Integer, Offer> currentOffers;
	
	/*
	CONSTRUCTOR
	 */
	Model (String host) throws RemoteException {
		super();
		System.setSecurityManager(new SecurityManager());
		currentOffers = new Hashtable<>();
		currentlyLoggedInAs = null;
		try {
			Registry registry;
			if (host == null)
				registry = LocateRegistry.getRegistry(5000);
			else
				registry = LocateRegistry.getRegistry(host, 5000);
			
			servant = (ServantInterface) registry.lookup("Servant");
			currentOffers = getCurrentOffers();
		} catch (RemoteException e) {
			System.out.println("Client.Model: RemoteException when getting stub from registry");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("Client.Model: NotBound Exception when looking up for Servant");
			e.printStackTrace();
		}
	}
	
	/*
	SETTERS
	 */
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
	void registerToServer () {
		try {
			Model self = (Model) controllerMediator.retrieveColleague("Model");
//			Observer selfStub = (Observer) UnicastRemoteObject.exportObject(self, 0);
			
			servant.attach(self);
		}
		catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
	}
	
	/*
	USER RELATED METHODS
	INPUT: FROM RegisterView -> RegisterController -> ControllerMediator -> this
	OUTPUT: TO ControllerMediator -> RegisterController -> RegisterView
	 */
	
	/**
	 * Ask the server to register this new user
	 * @param name Real name of the user (required)
	 * @param nickname Nickname with which the user will be known for other users, must be unique (required)
	 * @param email Email of the user (optional)
	 * @param address Address of the user (optional)
	 * @param phone Phone of the user (optional)
	 * @return 1 if the user was successfully registered and the connection ended without problems,
	 * 0 if the user's nickname was already taken
	 * -1 if some exception occur during the connection
	 */
	int signUp (String name, String nickname, String email, String address, String phone) {
		try {
			System.out.println("Registering user in Model with data: " + name + ", " + nickname + " | " + email + " | " + address + " : " + phone);
			boolean nicknameFree = servant.registerUser(name, nickname, email, address, phone);
			
			return nicknameFree ? 1 : 0;
		}
		catch (RemoteException e) {
			System.out.println("Client.Model: RemoteException while trying to register an user");
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Using a nickname the activities of this session of the current client will be done at this name
	 * @param nickname Nickname of the user to login
	 * @return 1 if the user was found in the server
	 * 0 if the user's nickname doesn't exist
	 * -1 if some exception occur during the connection
	 */
	int login (String nickname) {
		try {
			System.out.println("Logging in the user " + nickname + "...");
			User found = servant.seekUser(nickname);
			String result = found == null ? " is not associated with any account" : " is now logged in";
			System.out.println("The user " + nickname + result);
			
			controllerMediator.updateUserLoggedIn(found == null ? "" : found.getNickname());
			
			this.currentlyLoggedInAs = found;
			
			return found == null ? 0 : 1;
		}
		catch (RemoteException e) {
			System.out.println("Client.Model: RemoteException while trying to login");
			e.printStackTrace();
			return -1;
		}
	}
	
	/*
	OFFER RELATED METHODS
	INPUT: FROM AuctionView -> AuctionController -> ControllerMediator -> this
	OUTPUT: TO ControllerMediator -> AuctionController -> AuctionView
	 */
	
	/**
	 * Send information of a new offer to the server
	 * @param offerName Name of the product to offer
	 * @param offerDescription A brief description of the product
	 * @param initialPrice Lowest threshold of bid
	 * @param offerDeadline Date when the auction is considered ended
	 * @return true if the method ends without problems
	 * false if a remote exception is thrown
	 */
	boolean addOffer (String offerName, String offerDescription, String initialPrice, LocalDate offerDeadline) {
		try {
			double price = Double.parseDouble(initialPrice);
			
			servant.addOffer(currentlyLoggedInAs.getNickname(), offerName, offerDescription, price, offerDeadline);
		
//			clients.notifyClients();
//			offers.displayOffers();
//			users.displayUsers();
			return true;
		} catch (RemoteException e) {
			System.out.println("Client.Model: RemoteException while trying to login");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Send info to the server about a new bid to be appended to the history of bids for this offer and to be set as current bid for that offer
	 * @param offerId ID of the offer to bid for
	 * @param bid Amount of money proposed, greater than the previous bid
	 * @return true if the method ends without problems
	 * false if a remote exception is thrown
	 */
	boolean addBid (int offerId, double bid) {
		try {
			servant.newBid(offerId, currentlyLoggedInAs.getNickname(), bid);
//			offers.displayOffers();
//			controllerMediator.updateOffersList();
//			clients.notifyClients();
			return true;
		} catch (RemoteException re) {
			System.err.println("Error retrieving offers from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
			return false;
		}
	}
	
	/*
	NOT YET DEFINED METHODS OR PRONE TO BE OMITTED
	 */
	
	Hashtable <Integer, Offer> getLocalOffers() {
		return currentOffers;
	}
	
	Hashtable <Integer, Offer> getCurrentOffers() {
		try {
			return servant.getCurrentOffers();
		} catch (RemoteException re) {
			System.err.println("Error retrieving offers from Client Side!");
			StringWriter outError = new StringWriter();
			re.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
		return null;
	}
	
	@Override
	public void update(Hashtable<Integer, Offer> news) throws RemoteException {
//		currentOffers.putAll(news);
		currentOffers = new Hashtable<>(news);
//		currentOffers = servant.getCurrentOffers();
		controllerMediator.updateOffers();
	}
	
	@Override
	public void test () throws RemoteException{
		System.out.println("This function just can be called by the server, should be printed in this client");
	}
}
