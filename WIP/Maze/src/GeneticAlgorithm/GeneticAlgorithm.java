package GeneticAlgorithm;

/*
 * Carlos Andrés Reyes Evangelista
 * Universidad de las Américas Puebla
 * Ingeniería en Sistemas Computacionales
 *
 * May 9, 2019
 */

/**
 * Concrete class of an specific genetic algorithm
 * It holds the necessary methods and attributes in such a way an user only needs to instantiate this class once
 */
public class GeneticAlgorithm {

	/*
	Properties inherent to any genetic algorithm
	 */
	
	/**
	 * Specifies the maximum number of times the population of this genetic algorithm can be evolved
	 */
	private int numberOfGenerations;
	
	/**
	 * Defines the number of individuals to be created/evolved per generation
	 */
	private int populationSize;
	
	/**
	 * Implies the amount of individuals of the population that will compete in the tournament selections
	 */
	private int tournamentSize;
	
	/**
	 * If true the fittest individual will be cloned for the next generation
	 */
	private boolean elitism;
	
	/**
	 * Establishes the likelihood an arbitrary individual may mutate while evolving
	 */
	private double mutationRate;
	
	/**
	 * Collection of Individuals
	 * Represents the population of the current generated/evolved generation
	 */
	private Individual [] currentGeneration;
	
	private int currentCountGeneration;
	
	/**
	 * Generate a new instance of this genetic algorithm with the given data
	 *
	 * @param numberOfGenerations specifies the maximum number of generations allowed
	 * @param populationSize defines the number of individuals to be created/evolved per generation
	 * @param tournamentSize implies the amount of individuals of the population that will compete in the tournament
	 *                       selections
	 * @param mutationRate establishes the likelihood an arbitrary individual mutates while evolving
	 * @param elitism if true the fittest individual will be cloned for the next generation
	 */
	public GeneticAlgorithm (int numberOfGenerations, int populationSize, int tournamentSize, double mutationRate, boolean elitism) {
		this.numberOfGenerations = numberOfGenerations;
		this.populationSize = populationSize;
		this.tournamentSize = tournamentSize;
		this.mutationRate = mutationRate;
		this.elitism = elitism;
		
		currentGeneration = new Individual[populationSize];
		currentCountGeneration = 0;
	}
	
	/*
	GETTERS
	 */
	public Individual [] getCurrentGeneration() {
		return currentGeneration;
	}
	
	public int getCurrentCountGeneration() {
		return currentCountGeneration;
	}
	
	public Individual getFittest() {
		return Population.getFittest(currentGeneration);
	}
	
	
	/**
	 * Will update the value of this instance currentGeneration property.
	 * If it is the first time this method is called, a set of new individuals will be generated randomly,
	 * otherwise it will use the current population to get the next one by evolution (tournaments + crossover)
	 * If the count of generations has reached the limit established by the numberOfGenerations property it does nothing
	 */
	public void computeNextGeneration() {
		if (currentCountGeneration == 0) {
			currentGeneration = Population.initialize(populationSize);
			currentCountGeneration++;
		}
		else if (currentCountGeneration <= numberOfGenerations) {
			currentGeneration = Population.evolve(currentGeneration, populationSize, tournamentSize, elitism, mutationRate);
			currentCountGeneration++;
		}
	}
	
	/**
	 * When necessary the current population may be forced to fit any provided
	 *
	 * For this concrete program it is required because the Individuals generated and evolved lack of
	 * fitness value, which can only be calculated after a simulation is performed so the fitness value for each
	 * individual is calculated outside this class and then here the results are received
	 * @param updatedIndividuals a collection of Individuals
	 *                           It must contain the same amount of elements than this populationSize property
	 */
	public void setCurrentGeneration (Individual [] updatedIndividuals) {
		System.arraycopy(updatedIndividuals, 0, currentGeneration, 0, populationSize);
	}
	
	public void displayCurrentGeneration() {
		System.out.println("-----------------------------------------------");
		int i = 0;
		for (Individual individual : currentGeneration) {
			System.out.println("[" + i + " " + individual.toString() + "] ");
			i++;
		}
		System.out.println("-----------------------------------------------");
	}
}
