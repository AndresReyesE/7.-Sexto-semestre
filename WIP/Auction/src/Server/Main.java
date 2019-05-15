package Server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class represents the application to be run by the server.
 *
 * It creates an instance of a Server that will create an instance of the Servant itself,
 * as the Servant is already a subclass of UnicastRemoteObject this is already exported as a Remote Object,
 * ergo it is only necessary to get that reference from the server and bind it to the RMIRegistry
 * already created by this same application in the default port.
 *
 *
 * Those instances stay alive for the lifetime of the application and never is created another instance of these classes to encourage the coherence
 */
public class Main {
	public static void main(String[] args) {
		System.setProperty("java.security.policy", "java.policy");
		System.setSecurityManager(new SecurityManager());
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			
			Server server = Server.getInstance();
			
			Servant servant = server.getServant();
			
			registry.rebind("Servant", servant);
			
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
