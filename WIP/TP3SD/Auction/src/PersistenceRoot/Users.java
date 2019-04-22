package PersistenceRoot;

import Interfaces.UserInterface;
import RemoteObjects.Offer;
import RemoteObjects.User;

import java.rmi.RemoteException;
import java.util.Hashtable;

public class Users implements UserInterface {
	
	private Hashtable <String, User> registeredUsers;
	
	public Users() {
		this.registeredUsers =  new Hashtable<>();
	}
	
	public Hashtable <String, User> getRegisteredUsers () {
		return registeredUsers;
	}
	
	@Override
	public boolean registerUser (String name, String nickname, String email, String address, String phone) throws RemoteException {
		System.out.println("Registering user in Users object in server side: " + name + ", " + nickname + " | " + email + " | " + address + " : " + phone);
		
		if (registeredUsers.containsKey(nickname))
			return false;
		
		User newUser = new User (name, nickname, email, address, phone);
		registeredUsers.put(nickname, newUser);
		
		System.out.println("User registered!");
		return true;
	}
	
	@Override
	public void displayUsers () throws RemoteException {
		System.out.println("Users registered: ");
		for (User user : registeredUsers.values()) {
			user.display();
		}
	}
	
	@Override
	public User seekUser (String nickname) throws RemoteException {
		return registeredUsers.get(nickname);
	}
	
	@Override
	public boolean addOfferToUser (String nickname, Offer offer) throws RemoteException{
		User user = registeredUsers.get(nickname);
		
		if (user != null)       {
			user.addOffer(offer);
			registeredUsers.put(nickname, user);
		}
		
		
		return user != null;
	}
	
}
