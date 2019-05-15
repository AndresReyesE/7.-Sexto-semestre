package RemoteObjects;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Object that represents the offer of a product
 * It doesn't contain any special method else than Constructor, Getters, Setters and Displayers
 */
public class Offer implements Serializable {
	
	private int id;
	private String name;
	private String description;
	private Double initialPrice;
	private LocalDate deadline;
	
	private String currentBidder;
	private double currentBid;
	
	private ArrayList <Bid> history;
	
	/*
	CONSTRUCTOR
	 */
	public Offer (int id, String name, String description, Double initialPrice, LocalDate deadline) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.initialPrice = initialPrice;
		this.deadline = deadline;
		
		currentBidder = "New";
		currentBid = initialPrice;
		
		this.history = new ArrayList<>();
		history.add(new Bid ("Initial price", initialPrice));
	}
	
	/*
	GETTERS / SETTERS
	 */
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Bid> getHistory() {
		return history;
	}
	
	public void addToHistory (Bid newBid) {
		this.history.add(newBid);
	}
	
	public String getDescription() {
		return description;
	}
	
	public Double getInitialPrice() {
		return initialPrice;
	}
	
	public LocalDate getDeadline() {
		return deadline;
	}
	
	public String getCurrentBidder() {
		return currentBidder;
	}
	
	public void setCurrentBidder(String currentBidder) {
		this.currentBidder = currentBidder;
	}
	
	public double getCurrentBid() {
		return currentBid;
	}
	
	public void setCurrentBid(double currentBid) {
		this.currentBid = currentBid;
	}
	
	/*
	METHODS TO DISPLAY THIS OBJECT TO THE STANDARD OUTPUT
	 */
	public void display () {
		System.out.println("-------------------------------------------");
		System.out.println("ID:\t\t" + this.id);
		System.out.println("Name:\t\t" + this.name);
		System.out.println("Description:\t" + this.description);
		System.out.println("Initial price:\t" + this.initialPrice);
		System.out.println("Deadline:\t" + this.deadline);
		System.out.println("History:\t" + this.displayHistory());
		System.out.println("-------------------------------------------");
	}
	
	String displayHistory () {
		StringBuilder builder = new StringBuilder();
		for (Bid bid : history) {
			builder.append(bid.getNickname());
			builder.append(" - ");
			builder.append(bid.getBid());
			builder.append(" | ");
		}
		return builder.toString();
	}
}
