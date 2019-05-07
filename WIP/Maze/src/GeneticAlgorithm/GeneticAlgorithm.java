package GeneticAlgorithm;

import java.util.Random;

public class GeneticAlgorithm {

	private int numberOfGenerations;
	private int populationSize;
	private int tournamentSize;
	private boolean elitism;
	private double mutationRate;
	
	private Population currentGeneration;
	private int currentCountGeneration;
	
	/**
	 * Constructor
	 * @param numberOfGenerations
	 * @param populationSize
	 * @param tournamentSize
	 * @param mutationRate
	 * @param elitism
	 */
	public GeneticAlgorithm (int numberOfGenerations, int populationSize, int tournamentSize, double mutationRate, boolean elitism) {
		this.numberOfGenerations = numberOfGenerations;
		this.populationSize = populationSize;
		this.tournamentSize = tournamentSize;
		this.mutationRate = mutationRate;
		this.elitism = elitism;
		
		currentGeneration = new Population(populationSize);
		currentCountGeneration = 0;
	}
	
	/*
	GETTERS
	 */
	public Individual [] getCurrentGeneration() {
		return currentGeneration.getPopulation();
	}
	
	public void run () {
	
		
//		generateFirstGeneration();
//		evolvePopulation(numberOfGenerations);
		
	}
	
	public static Individual tournamentSelection (Individual[] population, int tournamentSize) {
		Random random = new Random(System.currentTimeMillis());
		Individual [] participants = new Individual[tournamentSize];
		
		
		for (int i = 0; i < tournamentSize; i++) {
			participants[i] = population[random.nextInt(population.length)];
		}
		
		return null;
	}
	
	/**
	 * Will update the value of this instance currentGeneration property.
	 * If it is the first time this method is called, new individuals will be generated randomly,
	 * otherwise it will use the current population to get the next one by evolution (tournaments + crossover)
	 */
	public void computeNextGeneration() {
		if (currentCountGeneration == 0)
			currentGeneration.initialize();
		else {
			currentGeneration.evolve(elitism, mutationRate);
		}
		currentCountGeneration++;
	}
}
