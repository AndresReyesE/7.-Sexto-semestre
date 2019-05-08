package sample;

import Mediator.Mediator;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.HashMap;

/**
 * Concrete implementation of a Mediator that handle and redirect requests from a set of objects that should exchange information among each other
 * This is done in order to reduce the number of connections required in the application.
 * The colleagues for this mediator are supposed to be:
 * - A SimulationController for the Auction View
 * - A SimulationController for the Register View
 */
public class SceneMediator extends Mediator {
	
	private HashMap <String, Object> colleagues;
	private Scene scene;
	
	SceneMediator (Scene scene) {
		this.scene = scene;
		colleagues = new HashMap<>();
	}
	
	/*
	IMPLEMENTATION OF MEDIATOR REQUIRED METHODS
	 */
	public void addColleague (String name, Object value) {
		colleagues.putIfAbsent(name, value);
	}
	
	public void deleteColleague (String name) {
		colleagues.remove(name);
	}
	
	public Object retrieveColleague (String name) {
		return colleagues.get(name);
	}
	
	/**
	 * Given the name of a colleague, this mediator change the current view to that precise colleague (it has to be a name associated to a FXML view)
	 */
	void activate (String name) {
		scene.setRoot((Parent) colleagues.get(name));
	}
}
