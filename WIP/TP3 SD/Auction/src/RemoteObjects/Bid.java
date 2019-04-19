package RemoteObjects;


public class Bid {
	private int offerID;
	private String nickname;
	private double bid;
	
	public Bid (int id, String nickname, double bid) {
		this.offerID = id;
		this.nickname = nickname;
		this.bid = bid;
	}
	
	public int getOfferID() {
		return offerID;
	}
	
	public void setOfferID(int offerID) {
		this.offerID = offerID;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public double getBid() {
		return bid;
	}
	
	public void setBid(double bid) {
		this.bid = bid;
	}
}
