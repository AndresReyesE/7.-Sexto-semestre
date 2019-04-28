package Server;

import RemoteInterfaces.ServantInterface;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try {
			Registry registry = LocateRegistry.createRegistry();
//			Registry registry = LocateRegistry.getRegistry();
			
			Server server = Server.getInstance();
			
			Servant servant = server.getServant();
			
//			ServantInterface servantStub = (ServantInterface) UnicastRemoteObject.exportObject(servant, 0);
			
			Naming.rebind("//192.168.100.217/Servant", servant);
			
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
