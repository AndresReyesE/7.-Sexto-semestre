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
			Registry registry = LocateRegistry.createRegistry(6969);
			
			Server server = new Server();
			
			Users users = server.getUsers();
			Offers offers = server.getOffers();
			
			UserInterface usersStub = (UserInterface) UnicastRemoteObject.exportObject(users, 0);
			OfferInterface offersStub = (OfferInterface) UnicastRemoteObject.exportObject(offers, 0);
			
			registry.bind("Users", usersStub);
			registry.bind("Offers", offersStub);
			
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
