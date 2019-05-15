package Client;


import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import sessionBean.SessionBeanRemote;


/**
 * Contains a local version of the collections concerning to a client, methods that handle the requests from the Controllers, performs remote methods invocation to the server and implements its own remote
 * methods defined in the Observer interface (for use of the server in order to receive callback)
 */
class Model {
	/*
	MEDIATOR ATTRIBUTES
	 */
	private ControllerMediator controllerMediator;
	
	/*
	RMI AND REFERENCES REQUIRED ATTRIBUTES
	 */
//	private ServantInterface servant;
	
	/*
	CLIENT RELATED ATTRIBUTES
	 */
	private User currentlyLoggedInAs;
	private Hashtable <Integer, Offer> currentOffers;
	
        
        SessionBeanRemote servant;
        
	/**
	 * Constructor
	 * Set the security manager with the required permissions for this application,
	 * initialize local objects and locate the registry initiated by the server
	 * in order to get a reference of the remote object Servant.
	 */
	Model (SessionBeanRemote servant) throws RemoteException {
            this.servant = servant;
            
            currentOffers = new Hashtable<>();
            currentlyLoggedInAs = null;
            getCurrentOffers();
	}
	
	/*
	SETTERS
	 */
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
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
			
			return true;
		} catch (RemoteException e) {
			System.out.println("Client.Model: RemoteException while trying to login");
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
			
			return true;
		} catch (RemoteException e) {
			System.err.println("Client.Model: Error adding a bid!");
			return false;
		}
	}
	
	/*
	LOCAL GETTERS
	 */
	
	Hashtable <Integer, Offer> getLocalOffers() {
		return currentOffers;
	}
	
	ArrayList <Offer> getUserOffers () {
            try {
                currentlyLoggedInAs = servant.seekUser(this.currentlyLoggedInAs.getNickname());
		return currentlyLoggedInAs.getOffersPlaced();
            } catch (RemoteException e) {
                System.out.println("Client.Model: Error updating this user object");
            }
            return null;
	}
	
	/*
	REMOTE CALLS
	 */
        /**
         * Gets the most recent version of the current offers collection
         */
	Hashtable <Integer, Offer> getCurrentOffers() {
		try {
                    currentOffers = new Hashtable<>(servant.getCurrentOffers());
                    return currentOffers;
		} catch (RemoteException e) {
                    System.err.println("Error retrieving offers from Client Side!");
		}
                return null;
	}
}
