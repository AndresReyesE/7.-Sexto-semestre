package RemoteObjects;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Offer implements Serializable {
	
	private int id;
	private String name;
	private String description;
	private Double initialPrice;
	private LocalDate deadline;
	
	private String currentBidder;
	private double currentBid;
	
	private ArrayList <Bid> history;
	
	public Offer (int id, String name, String description, Double initialPrice, LocalDate deadline) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.initialPrice = initialPrice;
		this.deadline = deadline;
		
		currentBidder = "New";
		currentBid = initialPrice;
		
		this.history = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public void setHistory(ArrayList<Bid> history) {
		this.history = history;
	}
	
	public void addToHistory (Bid newBid) {
		this.history.add(newBid);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getInitialPrice() {
		return initialPrice;
	}
	
	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}
	
	public LocalDate getDeadline() {
		return deadline;
	}
	
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
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
	
	public void display () {
		System.out.println("-------------------------------------------");
		System.out.println("ID:\t\t" + this.id);
		System.out.println("Name:\t\t" + this.name);
		System.out.println("Description:\t" + this.description);
		System.out.println("Initial price:\t" + this.initialPrice);
		System.out.println("Deadline:\t\t" + this.deadline);
		System.out.println("History:\t\t" + this.displayHistory());
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
