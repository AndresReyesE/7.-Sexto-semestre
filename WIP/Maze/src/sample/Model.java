package sample;

import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Individual;
import GraphicElements.Bouncer;
import GraphicElements.Maze;
import GraphicElements.Obstacle;
import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Model {
	
	private Controller controller;
	
	private int numberOfGenerations;
	private int populationSize;
	private int tournamentSize;
	private boolean elitism;
	private double mutationRate;
	
	private GeneticAlgorithm geneticAlgorithm;
	private Individual [] currentGeneration;
	
	private int level;
	private Maze maze;
	
	private Bouncer [] bouncers;
	
	/**
	 * Contructor
	 * @param controller
	 */
	public Model (Controller controller) {
		Random random = new Random(System.currentTimeMillis());
		
		maze = new Maze(1);
		this.controller = controller;
	}
	
	/**
	 * Create a new Genetic Algorithm instance with this given data.
	 * Will modify the geneticAlgorithm property of this instance
	 * @param numberOfGenerations
	 * @param populationSize
	 * @param tournamentSize
	 * @param mutationRate
	 * @param elitism
	 */
	public void createGeneticAlgorithm(int numberOfGenerations, int populationSize, int tournamentSize, double mutationRate, boolean elitism, int mazeLevel) {
		this.numberOfGenerations = numberOfGenerations;
		this.populationSize = populationSize;
		this.tournamentSize = tournamentSize;
		this.mutationRate = mutationRate;
		this.elitism = elitism;
		
		geneticAlgorithm = new GeneticAlgorithm(numberOfGenerations, populationSize, tournamentSize, mutationRate, elitism);
		
		maze = new Maze(mazeLevel);
		controller.addNode(maze.getBouncingArea());
		controller.addNodes(maze.getObstacleNodes());
		
		controller.addNode(maze.getTargetZone());
		
		bouncers = new Bouncer[populationSize];
		/*
		TO BE MOVED. WILL BE INVOKED EVERY TIME A NEW GENERATION IS REQUIRED,
		 */
		geneticAlgorithm.computeNextGeneration();
		createBouncers();
		initiateSimulation();
	}
	
	public void createBouncers() {
		int i = 0;
		for (Individual individual : geneticAlgorithm.getCurrentGeneration()) {
			bouncers[i] = new Bouncer(new Point2D(individual.getInitialX(), individual.getInitialY()), 12, maze, individual.getInitialDirection(), individual.getVelocity(), individual.getInitialDelay());
			controller.addNode(bouncers[i].getNode());
			i++;
		}
	}
	
	public void initiateSimulation () {
		Timer timer = new Timer();
		
		for (Obstacle obstacle : maze.getObstacles()) {
			obstacle.play();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					obstacle.stop();
				}
			}, 5000);
		}
		
		for (Bouncer bouncer : bouncers) {
//			bouncer.play();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					bouncer.play();
				}
			}, bouncer.getInitialDelay());
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					bouncer.stop();
				}
			}, 5000);
		}
		
	}
	
}
