package PersistenceRoot;

import Interfaces.OfferInterface;
import RemoteObjects.Bid;
import RemoteObjects.Offer;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;

public class Offers implements OfferInterface {
	
	private Hashtable<Integer, Offer> placedOffers;
	
	public Offers() {
		this.placedOffers = new Hashtable<>();
	}
	
	public Hashtable<Integer, Offer> getPlacedOffers() {
		return placedOffers;
	}
	
	@Override
	public void addOffer(String name, String description, double initialPrice, Date deadline) throws RemoteException {
		Offer newOffer = new Offer(placedOffers.size() + 1, name, description, initialPrice, deadline);
		
		placedOffers.put(newOffer.getId(), newOffer);
	}
	
	@Override
	public void newBid(int offerID, String bidder, double bid) throws RemoteException {
		Offer offerConcerned = placedOffers.get(offerID);
		Bid bidOffered = new Bid (offerID, bidder, bid);
		
		offerConcerned.addToHistory(bidOffered);
	}
}
