package Server;

import PersistenceRoot.Offers;
import PersistenceRoot.Users;

public class Server {
	private static Server uniqueInstance;
	
	private Users users;
	private Offers offers;
	
	public Server () throws Exception {
		if (uniqueInstance != null)
			throw new Exception("An instance of the server has already been created");
		else {
			users = new Users();
			offers = new Offers();
		}
	}
	
	public static Server getInstance () {
		if (uniqueInstance == null)
			try {
				uniqueInstance = new Server();
			}
			catch (Exception e) {
				System.out.println(e);
			}
		return uniqueInstance;
	}
	
	public Users getUsers() {
		return users;
	}
	
	public Offers getOffers() {
		return offers;
	}
}
