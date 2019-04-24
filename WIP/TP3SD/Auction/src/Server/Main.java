package Server;

import Interfaces.ClientInterface;
import Interfaces.OfferInterface;
import Interfaces.UserInterface;
import PersistenceRoot.Clients;
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
			Registry registry = LocateRegistry.createRegistry(5000);
//			Registry registry = LocateRegistry.getRegistry();
			
			Server server = Server.getInstance();
			
			Users users = server.getUsers();
			Offers offers = server.getOffers();
			Clients clients = server.getClients();
			
			UserInterface usersStub = (UserInterface) UnicastRemoteObject.exportObject(users, 0);
			OfferInterface offersStub = (OfferInterface) UnicastRemoteObject.exportObject(offers, 0);
			ClientInterface clientsStub = (ClientInterface) UnicastRemoteObject.exportObject(clients, 0);
			
			registry.rebind("Users", usersStub);
			registry.rebind("Offers", offersStub);
			registry.rebind("Clients", clientsStub);
			
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
