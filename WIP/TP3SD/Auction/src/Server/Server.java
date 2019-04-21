package Server;

import PersistenceRoot.Offers;
import PersistenceRoot.Users;

import java.time.LocalDate;

public class Server {
	private static Server uniqueInstance = new Server();
	
	private Users users;
	private Offers offers;
	
	public static Server getInstance() {
		return uniqueInstance;
	}
	
	private Server() {
		users = new Users();
		offers = new Offers();
	}
	public Users getUsers() {
		return users;
	}
	
	public Offers getOffers() {
		return offers;
	}
	
//	public void addOffer (String nickname, String name, String description, double initialPrice, LocalDate deadline) {
//		offers.addOffer(name, description, initialPrice, deadline);
//		users.seekUser(nickname).addOffer();
//	}
}
