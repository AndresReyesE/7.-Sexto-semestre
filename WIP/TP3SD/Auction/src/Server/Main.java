package Server;

import Interfaces.OfferInterface;
import Interfaces.UserInterface;
import PersistenceRoot.Offers;
import PersistenceRoot.Users;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
	
	
	public static void main(String[] args) {
		try {
//			Registry registry = LocateRegistry.createRegistry(5000);
			Registry registry = LocateRegistry.getRegistry();
			
			Server server = Server.getInstance();
			
			Users users = server.getUsers();
			Offers offers = server.getOffers();
			
			UserInterface usersStub = (UserInterface) UnicastRemoteObject.exportObject(users, 0);
			OfferInterface offersStub = (OfferInterface) UnicastRemoteObject.exportObject(offers, 0);
			
			registry.rebind("Users", usersStub);
			registry.rebind("Offers", offersStub);
			
			System.out.println("Auction server is running and listening for calls...");
			
		}
		catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			String errorString = outError.toString();
			System.out.println(errorString);
		}
	}
}
