package GraphicElements;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Bouncer {
	private final int NOTHING_TOUCHED = 0;
	private final int NORTHERN_WALL = 1;
	private final int SOUTHERN_WALL = 2;
	private final int WESTERN_WALL = 3;
	private final int EASTERN_WALL = 4;
	private final int NORTHWEST_WALL = 5;
	private final int NORTHEAST_WALL = 6;
	private final int SOUTHWEST_WALL = 7;
	private final int SOUTHEAST_WALL = 8;
	private final int OBSTACLE_HIT_FROM_BASE = 9;
	private final int OBSTACLE_HIT_FROM_SIDE= 10;
	private final int OBSTACLE_HIT_FROM_CORNER = 11;
	private final int TARGET_HIT = 12;
	
	private Maze bouncingArea;
	
	private Circle node;
	private Point2D initialPosition;
	private double radius;
	
	private double currentDirection;
	private double stepX, stepY;
	private double velocity;
	private long initialDelay;
	
	private IntegerBinding collide;
	private AnimationTimer bouncingAnimation;
	
	private double finalX;
	private double finalY;
	private double maximumDistance;
	private double minimumDistance;
	
	private Point2D targetPoint;
	private double closestX;
	private double closestY;
	private double closestDistance;
	private double fitnessValue;
	
	/**
	 * Constructor
	 * @param position
	 * @param radius
	 * @param maze
	 * @param currentDirection
	 * @param velocity
	 * @param initialDelay
	 */
	public Bouncer (Point2D position, double radius, Maze maze, double currentDirection, double velocity, long initialDelay) {
		ArrayList <Rectangle> obstacles = maze.getObstacleNodes();
		Rectangle targetZone = maze.getTargetZone();
		
		this.initialPosition = position;
		this.radius = radius;
		this.node = new Circle(position.getX(), position.getY(), radius, Color.AQUA);
		
		this.currentDirection = currentDirection;
		this.velocity = velocity;
		this.stepX = velocity * Math.cos(currentDirection);
		this.stepY = -velocity * Math.sin(currentDirection);
		this.initialDelay = initialDelay;
		
		this.bouncingArea = maze;
		this.fitnessValue = 0;
		this.targetPoint = new Point2D(targetZone.getX() + targetZone.getWidth() / 2, targetZone.getY() + targetZone.getHeight() / 2);
		this.maximumDistance = initialPosition.distance(targetPoint);
		this.minimumDistance =  radius + new Point2D(targetZone.getX(), targetZone.getY()).distance(targetPoint);
		this.closestDistance = maximumDistance;
		
		
		collide = Bindings.createIntegerBinding(
				() -> {
					if (node.getCenterY() - radius <= 0) { //bouncer touched northern wall
						if (node.getCenterX() - radius <= 0) //bouncer also touched western wall
							return NORTHWEST_WALL;
						if (node.getCenterX() + radius >= 500) //bouncer also touched eastern wall
							return NORTHEAST_WALL;
						return NORTHERN_WALL;
					}
					else if (node.getCenterY() + radius >= 500) { //bouncer touched southern wall
						if (node.getCenterX() - radius <= 0) //bouncer also touched western wall
							return SOUTHWEST_WALL;
						if (node.getCenterX() + radius >= 500) //bouncer also touched eastern wall
							return SOUTHEAST_WALL;
						return SOUTHERN_WALL;
					}
					else if (node.getCenterX() - radius <= 0) //bouncer touched western wall
						return WESTERN_WALL;
					else if (node.getCenterX() + radius >= 500)	//bouncer touched eastern wall
						return EASTERN_WALL;
					
					for (Rectangle obstacle : obstacles){
						if (node.intersects(obstacle.localToParent(obstacle.getBoundsInLocal()))) {
							if (node.getCenterX() >= obstacle.getX() + obstacle.getTranslateX() && node.getCenterX() <= obstacle.getY() + obstacle.getTranslateX() + obstacle.getWidth()) {
								return OBSTACLE_HIT_FROM_BASE;
							}
							else if (node.getCenterY() >= obstacle.getY() + obstacle.getTranslateY() && node.getCenterY() <= obstacle.getY() + obstacle.getTranslateY() + obstacle.getHeight())
								return OBSTACLE_HIT_FROM_SIDE;
							else
								return OBSTACLE_HIT_FROM_CORNER;
						}
					}
					
					if (node.intersects(maze.getTargetZone().getBoundsInLocal()))
						return TARGET_HIT;
					
					return NOTHING_TOUCHED;
				},
				node.centerXProperty(),
				node.centerYProperty()
		);
		
		collide.addListener(
				(observable, oldValue, newValue) -> {
					if (newValue.equals(NORTHERN_WALL) || newValue.equals(SOUTHERN_WALL) || newValue.equals(OBSTACLE_HIT_FROM_BASE))
						updateDirection(2 * Math.PI - this.currentDirection);
					else if (newValue.equals(WESTERN_WALL) || newValue.equals(EASTERN_WALL) || newValue.equals(OBSTACLE_HIT_FROM_SIDE))
						updateDirection(Math.PI - this.currentDirection);
					else if (newValue.equals(NORTHWEST_WALL)
							         || newValue.equals(NORTHEAST_WALL)
							         || newValue.equals(SOUTHWEST_WALL)
							         || newValue.equals(SOUTHEAST_WALL))
						updateDirection(this.currentDirection + Math.PI);
					else if (newValue.equals(OBSTACLE_HIT_FROM_CORNER))
						updateDirection(Math.PI - this.currentDirection);
					else if (newValue.equals(TARGET_HIT)) {
//						this.fitnessValue = 1;
						stop();
					}
				}
		);
		
		bouncingAnimation = new AnimationTimer() {
			private long lastUpdate = 0;
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 1000) {
					move();
					computeClosestDistance();
					lastUpdate = now;
				}
			}
		};
	}
	
	/*
	GETTERS
	 */
	public Circle getNode() {
		return node;
	}
	
	public long getInitialDelay() {
		return initialDelay;
	}
	
	/*
	
	 */
	
	private void computeClosestDistance() {
		double currentDistance = new Point2D(node.getCenterX(), node.getCenterY()).distance(targetPoint);
		
		if (currentDistance < closestDistance) {
			this.closestDistance = currentDistance;
			this.closestX = node.getCenterX();
			this.closestY = node.getCenterY();
			
			calculateFitnessValue();
		}
		
		this.closestDistance = currentDistance < closestDistance ? currentDistance : closestDistance;
	}
	
	private void calculateFitnessValue() {
		fitnessValue = (minimumDistance - closestDistance) / (maximumDistance - minimumDistance) + 1;
		
		Line line = new Line(closestX, closestY, targetPoint.getX(), targetPoint.getY());
		for (Rectangle obstacle : bouncingArea.getObstacleNodes()) {
			if (line.intersects(obstacle.localToParent(obstacle.getBoundsInLocal())))
				fitnessValue -= 0.20;
		}
		
	}
	
	public double getFitnessValue() {
		return fitnessValue;
		
	}
	
	/*
	ANIMATION METHODS
	 */
	public void move () {
		node.setCenterX(node.getCenterX() + stepX);
		node.setCenterY(node.getCenterY() + stepY);
	}
	
	public void play () {
		bouncingAnimation.start();
	}
	
	public void stop () {
		bouncingAnimation.stop();
		
//		closestDistance -= radius - bouncingArea.getTargetZone().getWidth() / 2;
		System.out.println("Closest distance: " + closestDistance + "\tFitness value: " + fitnessValue);
	}
	
	private void updateDirection (double newDirection) {
		this.currentDirection = newDirection;
		this.stepX = velocity * Math.cos(currentDirection);
		this.stepY = -velocity * Math.sin(currentDirection);
	}
}
