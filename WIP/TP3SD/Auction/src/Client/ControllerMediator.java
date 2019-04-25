package Client;

import Mediator.Mediator;
import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Hashtable;

public class ControllerMediator extends Mediator {
	
	private HashMap<String, Object> colleagues;
	
	private AuctionController auctionController;
	private RegisterController registerController;
	private Model2 model;
	
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
		model = (Model2) this.retrieveColleague("Model");
		registerController = (RegisterController) this.retrieveColleague("Register controller");
		try {
			return model.registerUser(name, nickname, email, address, phone);
		} catch (RemoteException e) {
			return false;
		}
	}
	
	boolean login (String nickname) {
		model = (Model2) this.retrieveColleague("Model");
		
		return model.login(nickname);
	}
	
	void addOffer(String name, String description, String price, LocalDate deadline) {
		model = (Model2) this.retrieveColleague("Model");
		
		try {
			model.addOffer(name, description, price, deadline);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	Hashtable <Integer, Offer> getCurrentOffers () {
		model = (Model2) this.retrieveColleague("Model");
		
		try {
			return model.getPlacedOffers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	void updateOffers() {
		AuctionController ac = (AuctionController) this.retrieveColleague("Auction controller");

//		ac.updateOffersList();
		ac.updateOffers();
	}
	
	void addBid (int offerId, double bid) {
		model = (Model2) this.retrieveColleague("Model");
		
		try {
			model.newBid(offerId, bid);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
