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
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	private Sphere sphere;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Random random = new Random(System.currentTimeMillis());
		
		double[] points = new double[100];
		
		for (int i = 0; i < 100; i++) {
			points[i] = random.nextInt(190);
			System.out.print(points[i] + " ");
			if (i % 2 != 0)
				System.out.println();
		}
		
		
		Circle circle = new Circle(100);
		Polyline pl = new Polyline(points);
		//pl.getPoints().addAll(new Double[] {0.0, 10.0, 20.0, 10.0, 40.0, 20.0});
		
		PathTransition transition = new PathTransition();
		transition.setDuration(Duration.seconds(100));
		transition.setCycleCount(PathTransition.INDEFINITE);
		transition.setPath(pl);
		transition.setNode(sphere);
		transition.play();
	}
}
