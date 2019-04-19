package RemoteObjects;

import java.util.ArrayList;
import java.util.Date;

public class Offer {
	
	private int id;
	private String name;
	private String description;
	private Double initialPrice;
	private Date deadline;
	
	private String currentBidder;
	private double currentBid;
	
	private ArrayList <Bid> history;
	
	public Offer(int id, String name, String description, Double initialPrice, Date deadline) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.initialPrice = initialPrice;
		this.deadline = deadline;
		
		this.history = new ArrayList<Bid>();
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
	
	public Date getDeadline() {
		return deadline;
	}
	
	public void setDeadline(Date deadline) {
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
}
