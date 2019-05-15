package RemoteObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Object that represents an user
 * Maintains data of personal information and a collection that stores the offer this user has placed
 *
 * It doesn't contain any special method other than Constructor, Getters, Setters and Displayers
 */
public class User implements Serializable {
	private String name;
	private String nickname;
	private String email;
	private String address;
	private String phone;
	
	private ArrayList <Offer> offersPlaced;
	
	/*
	CONSTRUCTOR
	 */
	public User (String name, String nickname, String email, String address, String phone) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.nickname = nickname;
		
		offersPlaced = new ArrayList<>();
	}
	
	/**
	 * Append a new offer to the registered offers this user has placed
	 * @param offer the offer with the data to be added
	 */
	public void addOffer (Offer offer) {
		offersPlaced.add(offer);
	}
	
	/*
	GETTERS / SETTERS
	 */
	public String getName() {
		return name;
	}
	
	public ArrayList<Offer> getOffersPlaced() {
		return offersPlaced;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	/*
	METHODS TO DISPLAY THIS OBJECT TO THE STANDARD OUTPUT
	 */
	public void display () {
		System.out.println("-------------------------------------------");
		System.out.println("Name:\t\t" + this.name);
		System.out.println("Nickname:\t" + this.nickname);
		System.out.println("Email:\t\t" + this.email);
		System.out.println("Address:\t" + this.address);
		System.out.println("Phone:\t\t" + this.phone);
		System.out.println("Offers:\t\t" + this.displayOffers());
		System.out.println("-------------------------------------------");
	}
	
	private String displayOffers () {
		StringBuilder builder = new StringBuilder();
		for (Offer offer : offersPlaced) {
			builder.append(offer.getName());
			builder.append(" | ");
		}
		return builder.toString();
	}
}
