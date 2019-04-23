package RemoteObjects;


import java.io.Serializable;

public class Bid implements Serializable {
	private String nickname;
	private double bid;
	
	public Bid (String nickname, double bid) {
		this.nickname = nickname;
		this.bid = bid;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public double getBid() {
		return bid;
	}
	
}
