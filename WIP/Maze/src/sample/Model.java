package sample;

import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Individual;
import GraphicElements.Bouncer;
import GraphicElements.Maze;
import GraphicElements.Obstacle;
import Zipper.Tuple;
import Zipper.Zip;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Model {
	
	private SimulationController simulationController;
	
	private int numberOfGenerations;
	private int populationSize;
	private int tournamentSize;
	private boolean elitism;
	private double mutationRate;
	
	private Maze maze;
	private int mazeLevel;
	
	private Bouncer [] bouncers;
	private int bouncersRadius;
	private Color bouncersColor;
	
	private GeneticAlgorithm geneticAlgorithm;
	private Individual [] currentGeneration;
	
	
	/**
	 * Constructor
	 * @param simulationController
	 */
	public Model (SimulationController simulationController) {
		maze = new Maze(1);
		this.simulationController = simulationController;
	}
	
	/**
	 * Create a new Genetic Algorithm instance with this given data.
	 * Will modify the geneticAlgorithm property of this instance.
	 * This function will only be called once and the instance of a geneticAlgorithm will last until this session ends
	 * @param numberOfGenerations
	 * @param populationSize
	 * @param tournamentSize
	 * @param mutationRate
	 * @param elitism
	 */
	public void createGeneticAlgorithm(int numberOfGenerations, int populationSize, int tournamentSize, double mutationRate, boolean elitism) {
		this.numberOfGenerations = numberOfGenerations;
		this.populationSize = populationSize;
		this.tournamentSize = tournamentSize;
		this.mutationRate = mutationRate;
		this.elitism = elitism;
		
		geneticAlgorithm = new GeneticAlgorithm(numberOfGenerations, populationSize, tournamentSize, mutationRate, elitism);
		
		/*
		TO BE MOVED. WILL BE INVOKED EVERY TIME A NEW GENERATION IS REQUIRED,
		 */

	}
	
	public void setUserParameters(int mazeLevel, int bouncersRadius, Color bouncersColor) {
		this.mazeLevel = mazeLevel;
		this.bouncersRadius = bouncersRadius;
		this.bouncersColor = bouncersColor;
	}
	
	public void createMaze() {
		maze = new Maze(mazeLevel);
		simulationController.addNode(maze.getBouncingArea());
		simulationController.addNodes(maze.getObstacleNodes());
		
		simulationController.addNode(maze.getTargetZone());
	}
	
	public void createBouncers() {
		bouncers = new Bouncer[populationSize];
		int i = 0;
		currentGeneration = geneticAlgorithm.getCurrentGeneration();
		for (Individual individual : currentGeneration) {
			bouncers[i] = new Bouncer(new Point2D(individual.getInitialX(), individual.getInitialY()), 12, maze, individual.getInitialDirection(), individual.getVelocity(), individual.getInitialDelay());
			simulationController.addNode(bouncers[i].getNode());
			i++;
		}
	}
	
	public void cleanBouncers() {
		for (Bouncer bouncer : bouncers) {
			simulationController.removeNode(bouncer.getNode());
		}
	}
	
	public void nextGeneration() {
		geneticAlgorithm.computeNextGeneration();
		simulationController.updateCurrentGeneration(geneticAlgorithm.getCurrentCountGeneration());
		createBouncers();
		initiateSimulation();
	}
	
	private void initiateSimulation() {
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
		
		for (Tuple<Bouncer, Individual> bouncerIndividualTuple : Zip.zip(Arrays.asList(bouncers), Arrays.asList(currentGeneration))) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					bouncerIndividualTuple.getLeft().play();
				}
			}, bouncerIndividualTuple.getLeft().getInitialDelay());
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					bouncerIndividualTuple.getLeft().stop();
					bouncerIndividualTuple.getRight().setFitnessValue(bouncerIndividualTuple.getLeft().getFitnessValue());
				}
			}, 5000);
		}
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				geneticAlgorithm.setCurrentGeneration(currentGeneration);
				Individual fittest = geneticAlgorithm.getFittest();
				Platform.runLater(() -> {
					simulationController.updateFittest(fittest.getFitnessValue());
					cleanBouncers();
				});
			}
		}, 5500);
		
	}
	
}
