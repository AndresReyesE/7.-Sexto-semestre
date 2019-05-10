package Project;

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

/*
 * Carlos Andrés Reyes Evangelista
 * Universidad de las Américas Puebla
 * Ingeniería en Sistemas Computacionales
 *
 * May 9, 2019
 */

/**
 * Implements the required methods to communicate the Genetic Algorithm related objects with the graphical components and the user interactions
 */
class Model {
	/**
	 * Reference to the controller of the simulation view,
	 * is used to manage the life cycle of the graphical objects in the view
	 */
	private SimulationController simulationController;
	
	private int numberOfGenerations, populationSize;
	
	private Maze maze;
	private int mazeLevel;
	
	private Bouncer [] bouncers;
	private int bouncersRadius;
	private Color bouncersColor;
	
	private GeneticAlgorithm geneticAlgorithm;
	private Individual [] currentGeneration;
	
	private long simulationTime;
	
	
	/**
	 * Sets the reference to the simulationController property to be used in this application
	 * @param simulationController reference to the SimulationController.
	 *                             It has to be not null and has to have a reference to its correspondent view
	 */
	void setSimulationController(SimulationController simulationController) {
		this.simulationController = simulationController;
	}
	
	/**
	 * Create a new Genetic Algorithm instance with this given data.
	 * Will modify the geneticAlgorithm property of this instance.
	 * This function will only be called once and the instance of a geneticAlgorithm will last the lifetime of this session
	 * @param numberOfGenerations specifies the maximum number of generations allowed
	 * @param populationSize defines the number of individuals to be created/evolved per generation
	 * @param tournamentSize implies the amount of individuals of the population that will compete in the tournament
	 *                       selections
	 * @param mutationRate establishes the likelihood an arbitrary individual mutates while evolving
	 * @param elitism if true the fittest individual will be cloned for the next generation
	 */
	void createGeneticAlgorithm(int numberOfGenerations, int populationSize, int tournamentSize, double mutationRate, boolean elitism) {
		this.numberOfGenerations = numberOfGenerations;
		this.populationSize = populationSize;
		
		geneticAlgorithm = new GeneticAlgorithm(numberOfGenerations, populationSize, tournamentSize, mutationRate, elitism);
	}
	
	/**
	 * Sets various user-given parameters that may affect visually the performance of the simulations.
	 *
	 * At this point the maze level is known so the instance and graphical components are created
	 * @param mazeLevel the level for the predefined maze, the higher the number, the harder difficulty and
	 *                  faster movement of the obstacles setup
	 * @param bouncersRadius radius of the bouncer circles
	 * @param bouncersColor color of the bouncers
	 * @param simulationTimeSeconds amount of seconds every bouncer has available to finish the obstacle course
	 */
	void setUserParameters(int mazeLevel, int bouncersRadius, Color bouncersColor, int simulationTimeSeconds) {
		this.mazeLevel = mazeLevel;
		this.bouncersRadius = bouncersRadius;
		this.bouncersColor = bouncersColor;
		this.simulationTime = simulationTimeSeconds * 1000;
		simulationController.setSimulationTime(this.simulationTime);
		
		createMaze();
	}
	
	/**
	 * Method called by the simulationController whenever the user requests a new generation.
	 * It uses this object geneticAlgorithm property to get the next evolved generation, once got it uses
	 * that new set of individuals to create a bouncer object for each and ask the simulationController to
	 * add them to the simulation pane
	 * It finally initiates the animations nested in every obstacle and bouncer objects concerning this instance
	 *
	 * It only performs the previous operations as long as the number of generations limit hasn't been reached
	 */
	void nextGeneration() {
		int countGeneration = geneticAlgorithm.getCurrentCountGeneration();
		
		if (countGeneration < numberOfGenerations) {
			geneticAlgorithm.computeNextGeneration();
			geneticAlgorithm.displayCurrentGeneration();
			createBouncers();
			initiateSimulation();
			simulationController.inSimulation();
			simulationController.updateCurrentGeneration(countGeneration + 1 + "/" + numberOfGenerations);
		}
		else
			simulationController.updateCurrentGeneration("You've reached the limit of generations");
		
	}
	
	/**
	 * Creates this session's maze and ask the controller to add its graphical components to the view
	 */
	private void createMaze() {
		maze = new Maze(mazeLevel);
		simulationController.addNode(maze.getBouncingArea());
		simulationController.addNodes(maze.getObstacleNodes());
		
		simulationController.addNode(maze.getTargetZone());
	}
	
	/**
	 * Creates a Bouncer for each Individual of this generation population based on its parameters. Ask the
	 * controller to add the nodes of every bouncer to the view
	 * I.e., an Individual is a set of parameters which are used to create a Bouncer
	 */
	void createBouncers() {
		bouncers = new Bouncer[populationSize];
		int i = 0;
		currentGeneration = geneticAlgorithm.getCurrentGeneration();
		for (Individual individual : currentGeneration) {
			bouncers[i] = new Bouncer(new Point2D(individual.getInitialX(), individual.getInitialY()), bouncersRadius, maze, individual.getInitialDirection(), individual.getVelocity(), individual.getInitialDelay());
			bouncers[i].getNode().setFill(bouncersColor);
			simulationController.addNode(bouncers[i].getNode());
			i++;
		}
	}
	
	/*
	Ask the controller to eliminate the bouncers of the view
	 */
	private void cleanBouncers() {
		for (Bouncer bouncer : bouncers)
			simulationController.removeNode(bouncer.getNode());
	}
	
	/**
	 * Initiate the animations of every movable object in the scene
	 * This includes:
	 * - Play immediately the animations of every obstacle and
	 * - Stop the obstacles animations once the simulation time is over
	 * - Play the animations of each bouncer when its corresponding delay has passed
	 * - Stop the animations of each bouncer once the simulation time is over
	 *
	 * Additionally, when the bouncers are stopped they are removed from the view and
	 * their resulting fitness value is obtained and is assigned to their corresponding Individual
	 * Once every Individual have receive its fitness value, they are sent to the geneticAlgorithm instance
	 */
	void initiateSimulation() {
		Timer timer = new Timer();
		
		//timer.schedule is used for delay instructions for a determined time
		//for each obstacle, play its animation and stop it after simulationTime seconds
		for (Obstacle obstacle : maze.getObstacles()) {
			obstacle.play();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					obstacle.stop();
					obstacle.resetPosition();
				}
			}, simulationTime);
		}
		
		/*
		Zip is used here because any attribute referenced within an inner class (new TimerTask() {...}) must be either
		final or effective final, and neither the collection of bouncers nor the collection of individuals are,
		so in order to iterate over its elements and access to them an Iterator-like interface is required.
		In this case zip will create a list of tuples in which the first element is a Bouncer and the second one is an
		Individual, this way we can access both at the same time by iterating with for each over a single list
		
		The list return a list that looks like this:
		({Bouncer1, Individual1}, {Bouncer2, Individual2}, ...)
		 */
		for (Tuple<Bouncer, Individual> bouncerIndividualTuple : Zip.zip(Arrays.asList(bouncers), Arrays.asList(currentGeneration))) {
			//plays the bouncer animation after its defined delay
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					bouncerIndividualTuple.getLeft().play();
				}
			}, bouncerIndividualTuple.getLeft().getInitialDelay());
			
			//stop its animation after simulationTime is over and set the fitness value obtained by the bouncer
			//to the individual
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					bouncerIndividualTuple.getLeft().stop();
					bouncerIndividualTuple.getRight().setFitnessValue(bouncerIndividualTuple.getLeft().getFitnessValue());
				}
			}, simulationTime);
		}
		
		/*
		some millis after the simulationTime is over, clean all the bouncers, send the new individuals (with fitness
		value) and ask the controller to display the best fitness value
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				geneticAlgorithm.setCurrentGeneration(currentGeneration);
				Individual fittest = geneticAlgorithm.getFittest();
				
				/*
				it is used Platform.runLater() because these invocations pretend to trigger operations to affect
				a JavaFX view but outside the thread of the FX application, with this static method it is possible to
				avoid the problem by asking the FX thread to do this whenever it has free time
				 */
				Platform.runLater(() -> {
					simulationController.updateFittest(fittest.getFitnessValue());
					cleanBouncers();
				});
				geneticAlgorithm.displayCurrentGeneration();
			}
		}, simulationTime + 100);
	}
}
