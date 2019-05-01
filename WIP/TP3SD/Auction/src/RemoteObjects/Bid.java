package RemoteObjects;

import java.io.Serializable;

/**
 * Object that represents a single bid (puje en español)
 * It's used to maintain a bids history for every offer
 * It doesn't contain any special method else than Constructor, Getters, Setters and Displayers
 */
public class Bid implements Serializable {
	private String nickname;
	private double bid;
	
	/*
	CONSTRUCTOR
	 */
	public Bid (String nickname, double bid) {
		this.nickname = nickname;
		this.bid = bid;
	}
	
	
	/*
	GETTERS / SETTERS
	 */
	public String getNickname() {
		return nickname;
	}
	
	public double getBid() {
		return bid;
	}
	
}
