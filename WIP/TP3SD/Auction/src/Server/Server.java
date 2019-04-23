package Server;

import Client.Callback;
import Interfaces.CallbackInterface;
import PersistenceRoot.Clients;
import PersistenceRoot.Offers;
import PersistenceRoot.Users;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.time.LocalDate;
import java.util.ArrayList;

public class Server {
	private static Server uniqueInstance = new Server();
	
	
	private Users users;
	private Offers offers;
	private Clients clients;
//	private Callback clients;
	
	
	public static Server getInstance() {
		return uniqueInstance;
	}
	
	private Server() {
		users = new Users();
		offers = new Offers();
		clients = new Clients();
	}
	public Users getUsers() {
		return users;
	}
	
	public Offers getOffers() {
		return offers;
	}
	
	public Clients getClients () {
		return clients;
	}
	
//	public void addOffer (String nickname, String name, String description, double initialPrice, LocalDate deadline) {
//		offers.addOffer(name, description, initialPrice, deadline);
//		users.seekUser(nickname).addOffer();
//	}
}
