package sample;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	private Sphere sphere;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Circle circle = new Circle(100);
		Polyline pl = new Polyline();
		pl.getPoints().addAll(new Double[] {0.0, 10.0, 20.0, 10.0, 40.0, 20.0});
		
		PathTransition transition = new PathTransition();
		transition.setDuration(Duration.seconds(1));
		transition.setCycleCount(PathTransition.INDEFINITE);
		transition.setPath(pl);
		transition.setNode(sphere);
		transition.play();
	}
}
