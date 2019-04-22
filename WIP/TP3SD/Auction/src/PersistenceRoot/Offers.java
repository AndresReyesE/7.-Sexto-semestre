package PersistenceRoot;

import Interfaces.OfferInterface;
import RemoteObjects.Bid;
import RemoteObjects.Offer;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Hashtable;

public class Offers implements OfferInterface {
	
	private Hashtable<Integer, Offer> placedOffers;
	
	public Offers() {
		this.placedOffers = new Hashtable<>();
	}
	
	@Override
	public Hashtable<Integer, Offer> getPlacedOffers() throws RemoteException {
		return placedOffers;
	}
	
	@Override
	public void addOffer(String name, String description, double initialPrice, LocalDate deadline) throws RemoteException {
		int id = placedOffers.size() + 1;
		Offer newOffer = new Offer(id, name, description, initialPrice, deadline);
		
		placedOffers.putIfAbsent(id, newOffer);
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
	public void newBid(int offerID, String bidder, double bid) throws RemoteException {
		Offer offerConcerned = placedOffers.get(offerID);
		Bid bidOffered = new Bid (offerID, bidder, bid);
		
		offerConcerned.addToHistory(bidOffered);
	}
	
	@Override
	public void displayOffers () throws RemoteException {
		System.out.println("Placed offers: ");
		for (Offer offer : placedOffers.values()) {
			offer.display();
		}
	}
}
