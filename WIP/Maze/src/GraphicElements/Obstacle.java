package GraphicElements;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Obstacle {
	
	private Rectangle node;
	private TranslateTransition transition;
	
	public Obstacle (int x, int y, int width, int height, double seconds, double byX, Color color) {
		node = new Rectangle(x, y, width, height);
		node.setFill(color);
		
		transition = new TranslateTransition(Duration.seconds(seconds), node);
		transition.setByX(byX);
		transition.setCycleCount(TranslateTransition.INDEFINITE);
		transition.setAutoReverse(true);
	}
	
	public Rectangle getNode () {
		return node;
	}
	
	public void play() {
		transition.play();
	}
	
	public void stop() {
		transition.stop();
	}
}
