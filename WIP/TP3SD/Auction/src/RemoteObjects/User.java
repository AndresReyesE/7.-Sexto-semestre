package RemoteObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private String name;
	private String nickname;
	private String email;
	private String address;
	private String phone;
	
	private ArrayList <Offer> offersPlaced;
	
	public User (String name, String nickname, String email, String address, String phone) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.nickname = nickname;
		
		offersPlaced = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
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
	
	public void addOffer (Offer offer) {
		offersPlaced.add(offer);
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
