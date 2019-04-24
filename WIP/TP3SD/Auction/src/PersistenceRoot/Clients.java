package PersistenceRoot;

import Interfaces.CallbackInterface;
import Interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Clients implements ClientInterface {
	private ArrayList<CallbackInterface> clients;
	
	public Clients () {
		clients = new ArrayList<>();
	}
	
	@Override
	public void subscribeClient (String name) {
		try {
			Registry registry = LocateRegistry.getRegistry(5000);
			CallbackInterface clientStub = (CallbackInterface) registry.lookup(name);
			clients.add(clientStub);
			System.out.println("Clients actually: " + clients.size());
		}
		catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public int getClientsNumber () {
		return clients.size();
	}
	
	@Override
	public ArrayList <CallbackInterface> getClients () {
		return clients;
	}
	
	@Override
	public void notifyClients() throws RemoteException {
		for (CallbackInterface client : clients) {
			client.update();
		}
	}
}
