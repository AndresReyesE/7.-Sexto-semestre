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
		bouncingArea.setFill(Color.DARKGRAY);
		bouncingArea.toBack();
		obstacles = new ArrayList<>();
		
		switch (level) {
			case 1:
				level1();
				break;
			case 2:
				level2();
				break;
			case 3:
				level3();
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
		
		obstacle[0] = new Obstacle(0, 140, 220, 40, 5, 200, 0, Color.GOLD);
		obstacle[1] = new Obstacle(280, 300, 220, 40, 5, -200, 0, Color.TEAL);
		
		obstacles.addAll(Arrays.asList(obstacle));
		
		target = new Image("resources/target.jpg", 50, 50, true, true);
		targetZone = new ImageView(target);
		targetZone.setX(450);
		targetZone.setY(450);
	}
	
	private void level2 () {
		Obstacle[] obstacle = new Obstacle[4];
		
		obstacle[0] = new Obstacle(0, 120, 240, 25, 1, 200, 0, Color.ANTIQUEWHITE);
		obstacle[1] = new Obstacle(280, 250, 220, 30, 1, -280, 0, Color.SKYBLUE);
		obstacle[2] = new Obstacle(200, 340, 300, 35, 0.7, -200, 20, Color.SNOW);
		obstacle[3] = new Obstacle(400, 380, 40, 40, 0.5, 0, 80, Color.LAVENDER);
		
		
		obstacles.addAll(Arrays.asList(obstacle));
		
		target = new Image("resources/target.jpg", 50, 50, true, true);
		targetZone = new ImageView(target);
		targetZone.setX(450);
		targetZone.setY(450);
	}
	
	private void level3 () {
		Obstacle[] obstacle = new Obstacle[7];
		
		obstacle[0] = new Obstacle(100, 120, 180, 25, 0.5, 220, 0, Color.MEDIUMVIOLETRED);
		obstacle[1] = new Obstacle(25, 180, 150, 30, 0.4, 200, 0, Color.DARKGOLDENROD);
		obstacle[2] = new Obstacle(375, 210, 100, 25, 0.7, -200, 20, Color.STEELBLUE);
		obstacle[3] = new Obstacle(0, 280, 220, 25, 0.6, 500, 10, Color.CORAL);
		obstacle[4] = new Obstacle(350, 350, 150, 30, 0.3, -350, 120, Color.AQUA);
		obstacle[5] = new Obstacle(400, 380, 40, 40, 0.5, 0, 80, Color.LIGHTGREEN);
		obstacle[6] = new Obstacle(360, 0, 40, 40, 0.5, -20, 80, Color.RED);
		
		obstacles.addAll(Arrays.asList(obstacle));
		
		target = new Image("resources/target.jpg", 50, 50, true, true);
		targetZone = new ImageView(target);
		targetZone.setX(450);
		targetZone.setY(450);
	}
	
}
