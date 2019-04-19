package PersistenceRoot;

import Interfaces.UserInterface;
import RemoteObjects.User;

import java.rmi.RemoteException;
import java.util.Hashtable;

public class Users implements UserInterface {
	
	private Hashtable <String, User> registeredUsers;
	
	public Users() {
		this.registeredUsers =  new Hashtable<>();
	}
	
	public Hashtable<String, User> getRegisteredUsers() {
		return registeredUsers;
	}
	
	@Override
	public boolean registerUser(String name, String nickname, String email, String address, String phone) throws RemoteException {
		if (!registeredUsers.containsKey(nickname))
			return false;
		
		User newUser = new User (name, nickname, email, address, phone);
		registeredUsers.put(nickname, newUser);
		return true;
	}
}
