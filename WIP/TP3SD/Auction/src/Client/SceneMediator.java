package Client;

import Mediator.Mediator;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.HashMap;

public class SceneMediator extends Mediator {
	
	private HashMap <String, Object> colleagues;
	private Scene scene;
	
	SceneMediator (Scene scene) {
		this.scene = scene;
		colleagues = new HashMap<>();
	}
	
	public void addColleague (String name, Object value) {
		colleagues.putIfAbsent(name, value);
	}
	
	public void deleteColleague (String name) {
		colleagues.remove(name);
	}
	
	public Object retrieveColleague (String name) {
		return colleagues.get(name);
	}
	
	public void activate (String name) {
		scene.setRoot((Parent) colleagues.get(name));
	}
	
//	void updateLoggedUser () {
//		Parent auctionView = (Parent) colleagues.get("Auction view");
//		RegisterController rc = auctionView.getCon
//
//		Model m = (Model) colleagues.get("Model");
//
//		rc.
//	}
	
}
