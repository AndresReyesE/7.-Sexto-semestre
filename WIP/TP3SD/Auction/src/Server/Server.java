package Server;


import java.rmi.RemoteException;

public class Server {
	private static Server uniqueInstance = new Server();
	
	private Servant servant;
	
	public static Server getInstance() {
		return uniqueInstance;
	}
	
	private Server() {
		try {
			servant = new Servant();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Servant getServant() {
		return servant;
	}
}
