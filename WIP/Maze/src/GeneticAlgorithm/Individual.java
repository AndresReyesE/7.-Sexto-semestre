package GeneticAlgorithm;

public class Individual {
	
	private double initialX;
	private double initialY;
	private double initialDirection;
	private long initialDelay;
	private double velocity;
	
	private double finalX;
	private double finalY;
	
	
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
	
	/*
	SETTERS
	 */
	public void setFinalX(double finalX) {
		this.finalX = finalX;
	}
	
	public void setFinalY(double finalY) {
		this.finalY = finalY;
	}
}
