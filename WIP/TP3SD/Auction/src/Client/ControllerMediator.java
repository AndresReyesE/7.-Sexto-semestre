package Client;

import Mediator.Mediator;
import RemoteObjects.Offer;
import RemoteObjects.User;
import javafx.application.Platform;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Hashtable;

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
	
	Hashtable <Integer, Offer> getCurrentOffers () {
		model = (Model) this.retrieveColleague("Model");
		
		return model.getCurrentOffers();
	}
	
	Hashtable <Integer, Offer> getLocalOffers () {
		model = (Model) this.retrieveColleague("Model");
		
		return model.getLocalOffers();
	}
	
	void updateOffers() {
		auctionController = (AuctionController) this.retrieveColleague("Auction controller");

		Platform.runLater(
				() -> {
					auctionController.updateOffers();
				}
		);
	}
	
	
}
