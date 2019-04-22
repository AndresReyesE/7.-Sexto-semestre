package Client;

import Mediator.Mediator;
import RemoteObjects.Offer;
import RemoteObjects.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Hashtable;

public class ControllerMediator extends Mediator {
	
	private HashMap<String, Object> colleagues;
	
	private AuctionController auctionController;
	private RegisterController registerController;
	private Model model;
	
	ControllerMediator() {
		colleagues = new HashMap<>();
	}
	
	@Override
	public void addColleague(String name, Object object) {
		colleagues.putIfAbsent(name, object);
	}
	
	@Override
	public void deleteColleague(String name) {
		colleagues.remove(name);
	}
	
	@Override
	public Object retrieveColleague(String name) {
		return colleagues.get(name);
	}
	
	void updateUserLoggedIn (String nickname) {
		AuctionController ac = (AuctionController) this.retrieveColleague("Auction controller");
		
		ac.updateUserLoggedIn(nickname);
	}
	
	boolean signUp (String name, String nickname, String email, String address, String phone) {
		model = (Model) this.retrieveColleague("Model");
		registerController = (RegisterController) this.retrieveColleague("Register controller");
		
		return model.signUp(name, nickname, email, address, phone);
	}
	
	boolean login (String nickname) {
		model = (Model) this.retrieveColleague("Model");
		
		return model.login(nickname);
	}
	
	void addOffer(String name, String description, String price, LocalDate deadline) {
		model = (Model) this.retrieveColleague("Model");
		
		model.addOffer(name, description, price, deadline);
	}
	
	Hashtable <Integer, Offer> getCurrentOffers () {
		model = (Model) this.retrieveColleague("Model");
		
		return model.getCurrentOffers();
	}
	
	void updateOffersList() {
		AuctionController ac = (AuctionController) this.retrieveColleague("Auction controller");
		
		ac.updateOffersList();
	}
	
	void addBid (int offerId, double bid) {
		model = (Model) this.retrieveColleague("Model");
		
		model.addBid(offerId, bid);
	}
//	User getCurrentlyLoggedInAs () {
//		model = (Model) this.retrieveColleague("Model");
//
//		return model.getCurrentlyLoggedInAs();
//	}
}
