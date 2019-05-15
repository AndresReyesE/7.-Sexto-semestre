package Client;

import Mediator.Mediator;
import RemoteObjects.Offer;
import javafx.application.Platform;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Concrete implementation of a Mediator that handle and redirect requests from a set of objects that should exchange information among each other
 * This is done in order to reduce the number of connections required in the application.
 * The colleagues for this mediator are supposed to be:
 * - A Controller for the Auction View
 * - A Controller for the Register View
 * - A reference to the global model
 */
public class ControllerMediator extends Mediator {
	
//	private HashMap<String, Object> colleagues;
	
	private AuctionController auctionController;
	private RegisterController registerController;
	private Model model;
	
	/*
	CONSTRUCTOR
	 */
	ControllerMediator() {
		this.colleagues = new Hashtable<String, Object>();
	}
	
	/*
	GETTER
	 */
	public Hashtable getColleagues () {
		return colleagues;
	}
	
	/*
	IMPLEMENTATION OF MEDIATOR REQUIRED METHODS
	 */
	@Override
	public void addColleague(String name, Object object) {
		colleagues.put(name, object);
	}
	
	@Override
	public void deleteColleague(String name) {
		colleagues.remove(name);
	}
	
	@Override
	public Object retrieveColleague(String name) {
		return colleagues.get(name);
	}
	
	/*
	METHODS REDIRECTION
	INVOKER CLASS -> INVOKED CLASS
	Invoker request the method in Invoked, this result is redirected to the invoker
	 */
	
	/**
	 * Model -> AuctionController
	 */
	void updateUserLoggedIn (String nickname) {
		auctionController = (AuctionController) this.retrieveColleague("Auction controller");
		
		auctionController.updateUserLoggedIn(nickname);
	}
	
	/**
	 * RegisterController -> Model
	 */
	int signUp (String name, String nickname, String email, String address, String phone) {
		model = (Model) this.retrieveColleague("Model");
		
		return model.signUp(name, nickname, email, address, phone);
	}
	
	/**
	 * RegisterController -> Model
	 */
	int login (String nickname) {
		model = (Model) this.retrieveColleague("Model");
		
		return model.login(nickname);
	}
	
	/**
	 * AuctionController -> Model
	 */
	boolean addOffer(String name, String description, String price, LocalDate deadline) {
		model = (Model) this.retrieveColleague("Model");
		
		return model.addOffer(name, description, price, deadline);
	}
	
	/**
	 * AuctionController -> Model
	 */
	boolean addBid (int offerId, double bid) {
		model = (Model) this.retrieveColleague("Model");
		
		return model.addBid(offerId, bid);
	}
	
	/**
	 * AuctionController -> Model
	 */
	Hashtable <Integer, Offer> getLocalOffers () {
		model = (Model) this.retrieveColleague("Model");
		
		return model.getLocalOffers();
	}
	
	/**
	 * AuctionController -> Model
	 */
	ArrayList<Offer> getUserOffers () {
		model = (Model) this.retrieveColleague("Model");
		
		return model.getUserOffers();
	}
	
	/**
	 * Model -> AuctionController
	 */
	void updateOffers() {
		auctionController = (AuctionController) this.retrieveColleague("Auction controller");

		/*
		As this instruction is being called outside a FX application (from the model) it is required to use the
		static methods of Platform that allows the concerned thread to run these instructions -that'll modify the FXML components-
		as soon as it's possible.
		The nested lambda is supposed to override the new Runnable () method required for runLater (equivalent to invokeLater in Swing)
		 */
		Platform.runLater(
				() -> {
					auctionController.updateView();
				}
		);
	}
	
	
}
