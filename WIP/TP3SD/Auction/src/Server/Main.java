package Server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
	public static void main(String[] args) {
		System.setProperty("java.security.policy", "java.policy");
		System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", "0.0.0.0");
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
//			Registry registry = LocateRegistry.getRegistry();
			
			Server server = Server.getInstance();
			
			Servant servant = server.getServant();
			
//			ServantInterface servantStub = (ServantInterface) UnicastRemoteObject.exportObject(servant, 0);
			
//			Naming.rebind("rmi://192.168.100.217:1099/Servant", servant);
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
