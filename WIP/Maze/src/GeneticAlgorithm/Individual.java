package GeneticAlgorithm;

import java.util.Random;

public class Individual {
	
	private double initialX;
	private double initialY;
	private double initialDirection;
	private long initialDelay;
	private double velocity;
	
	private double finalX;
	private double finalY;
	private double fitnessValue;
	
	
	public Individual(double initialX, double initialY, double initialDirection, long initialDelay, double velocity) {
		this.initialX = initialX;
		this.initialY = initialY;
		this.initialDirection = initialDirection;
		this.initialDelay = initialDelay;
		this.velocity = velocity;
	}
	
	/*
	GETTERS
	 */
	
	public double getInitialX() {
		return initialX;
	}
	
	public double getInitialY() {
		return initialY;
	}
	
	public double getInitialDirection() {
		return initialDirection;
	}
	
	public long getInitialDelay() {
		return initialDelay;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public double getFitnessValue() {
		return fitnessValue;
	}
	
	/*
		SETTERS
		 */
	public void setFinalX(double finalX) {
		this.finalX = finalX;
	}
	
	public void setFinalY(double finalY) {
		this.finalY = finalY;
	}
	
	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}
	
	/*
	METHODS
	 */
	public void mutate() {
		Random random = new Random(System.currentTimeMillis());
		this.initialX += random.nextDouble() * 20 - 10;
		this.initialY += random.nextDouble() * 20 - 10;
		this.initialDirection += random.nextDouble() * Math.PI - (Math.PI/2);
		this.velocity += random.nextDouble() * 2 - 1;
		this.initialDelay += random.nextLong() % 2000 - 1000;
		
		normalize();
	}
	
	private void normalize() {
		this.initialX = initialX < 0 ? 0 : (initialX > 100) ? 100 : initialX;
		this.initialY = initialY < 0 ? 0 : (initialY > 100) ? 100 : initialY;
		this.velocity = velocity < 1 ? 1 : (velocity > 5) ? 5 : velocity;
		this.initialDelay = initialDelay < 0 ? 0 : (initialDelay > 3000) ? 3000 : initialDelay;
	}
}
