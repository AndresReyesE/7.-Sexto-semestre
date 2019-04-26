package Server;


public class Server {
	private static Server uniqueInstance = new Server();
	
	private Servant servant;
	
	public static Server getInstance() {
		return uniqueInstance;
	}
	
	private Server() {
		servant = new Servant();
	}
	
	public Servant getServant() {
		return servant;
	}
}
