package GraphicElements;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/*
 * Carlos Andrés Reyes Evangelista
 * Universidad de las Américas Puebla
 * Ingeniería en Sistemas Computacionales
 *
 * May 9, 2019
 */

/**
 * Object that represents an obstacle, it contains a graphical node and an cyclic animation
 */
public class Obstacle {
	
	private Rectangle node;
	private TranslateTransition transition;
	
	public Obstacle (int x, int y, int width, int height, double seconds, double byX, double byY, Color color) {
		node = new Rectangle(x, y, width, height);
		node.setFill(color);
		node.setStroke(Color.BLACK);
		
		transition = new TranslateTransition(Duration.seconds(seconds), node);
		transition.setByX(byX);
		transition.setByY(byY);
		transition.setCycleCount(TranslateTransition.INDEFINITE);
		transition.setAutoReverse(true);
	}
	
	public Rectangle getNode () {
		return node;
	}
	
	public void resetPosition() {
		node.setTranslateX(0);
		node.setTranslateY(0);
	}
	
	public void play() {
		transition.play();
	}
	
	public void stop() {
		transition.stop();
	}
}
