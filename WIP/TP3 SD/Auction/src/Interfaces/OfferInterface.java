package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface OfferInterface extends Remote {
	void addOffer (String name, String description, double initialPrice, Date deadline) throws RemoteException;
	
	void newBid (int offerID, String bidder, double bid) throws RemoteException;
}
