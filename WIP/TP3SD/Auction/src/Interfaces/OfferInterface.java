package Interfaces;

import RemoteObjects.Offer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Hashtable;

public interface OfferInterface extends Remote {
	void addOffer (String name, String description, double initialPrice, LocalDate deadline) throws RemoteException;
	
	Offer seekOffer (String name, LocalDate deadline) throws RemoteException;
	
	void newBid (int offerID, String bidder, double bid) throws RemoteException;
	
	Hashtable <Integer, Offer> getPlacedOffers() throws RemoteException;
	
	void displayOffers () throws RemoteException;
}
