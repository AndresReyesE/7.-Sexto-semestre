package Server;

import RemoteInterfaces.ServantInterface;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try {
//			Registry registry = LocateRegistry.createRegistry(5000);
			Registry registry = LocateRegistry.getRegistry();
			
			Server server = Server.getInstance();
			
			Servant servant = server.getServant();
			
//			ServantInterface servantStub = (ServantInterface) UnicastRemoteObject.exportObject(servant, 0);
			
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
