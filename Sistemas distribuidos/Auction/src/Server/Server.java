package Server;


import java.rmi.RemoteException;

/**
 * The server of this application manages a servant that contains all the required methods, say remote and locals.
 *
 * The server is a separated part of the servant because the latter is supposed to represent the implementation
 * of the services declared in the remote interfaces and this way, this server can manage more servants as they
 * are added with the time
 *
 * The structure of this server responds to another Software Design Pattern called Singleton,
 * such pattern is implemented in such a way that this class cannot be never instantiated more than once.
 * Instantiate this class more than once in the same execution of a program would erase the content of
 * previous servants because the new servants would be rebind to the rmiregistry in the same port with the
 * same name, overwriting the progress of previous servers
 */
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
