package GraphicElements;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
	private Rectangle bouncingArea;
	private ArrayList <Obstacle> obstacles;
	private Image target;
	private ImageView targetZone;
	
	public Maze (int level) {
		bouncingArea = new Rectangle(0, 0, 500, 500);
		obstacles = new ArrayList<>();
		
		switch (level) {
			case 1:
				level1();
				break;
		}
	}
	
	public Rectangle getBouncingArea() {
		return bouncingArea;
	}
	
	public ArrayList<Rectangle> getObstacleNodes () {
		ArrayList<Rectangle> nodes = new ArrayList<>();
		
		for (Obstacle obs : obstacles)
			nodes.add(obs.getNode());
		
		return nodes;
	}
	
	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
	
	public ImageView getTargetZone() {
		return targetZone;
	}
	
	private void level1 () {
		Obstacle[] obstacle = new Obstacle[2];
		
		obstacle[0] = new Obstacle(0, 140, 220, 40, 5, 200, Color.GOLD);
		obstacle[1] = new Obstacle(280, 300, 220, 40, 5, -200, Color.TEAL);
		
		obstacles.addAll(Arrays.asList(obstacle));
		
//		targetZone = new Rectangle(450, 450, 50, 50);
		target = new Image("resources/target.jpg", 50, 50, true, true);
//		targetZone.setStroke(Color.RED);
//		targetZone.setFill(Color.GHOSTWHITE);
		targetZone = new ImageView(target);
		targetZone.setX(450);
		targetZone.setY(450);
//		Label targetIndicator = new Label("Target zone!", targetZone);
//		targetIndicator.setVisible(true);
		
		
	}
	
}
