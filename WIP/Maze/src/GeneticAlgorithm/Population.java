package GeneticAlgorithm;

import java.util.Random;

public class Population {
	
//	private int populationSize;
//	private int tournamentSize;
	
//	private Individual [] population;
	
//	public Population(int populationSize, int tournamentSize) {
//		this.populationSize = populationSize;
//		this.tournamentSize = tournamentSize;
//		this.population = new Individual[populationSize];
//	}
	
	/*
	GETTER
	 */
//	public Individual[] getPopulation() {
//		return population;
//	}
	
	static Individual getFittest(Individual [] participants) {
		double bestFitnessValue = participants[0].getFitnessValue();
		Individual currentFittest = participants[0];
		for (Individual individual : participants) {
			if (individual.getFitnessValue() > bestFitnessValue) {
				bestFitnessValue = individual.getFitnessValue();
				currentFittest = individual;
			}
		}
		return currentFittest;
	}
	
	/**
	 * Initialize this population by generating a set of populationSize random individuals
	 */
	public static Individual [] initialize(int populationSize) {
		Random random = new Random(System.currentTimeMillis());
		
		Individual [] population = new Individual[populationSize];
		
		double initialX, initialY, initialDirection, velocity;
		long initialDelay;
		for (int i = 0; i < populationSize; i++) {
			initialX = random.nextDouble() * 100;
			initialY = random.nextDouble() * 100;
			initialDirection = random.nextDouble() * Math.PI * 2;
			initialDelay = random.nextLong() % 3000;
			velocity = random.nextDouble() * 2 + 1;
			
			population[i] = new Individual(initialX, initialY, initialDirection, Math.abs(initialDelay), velocity);
		}
		
		return population;
	}
	
	/**
	 * Evolve the current generation by crossover/mutation
	 * @param elitism
	 * @param mutationRate
	 */
	public static Individual [] evolve (Individual [] population, int populationSize, int tournamentSize, boolean elitism, double mutationRate) {
		Individual [] newGeneration = new Individual[populationSize];
		
		for (int i = 0; i < populationSize; i++) {
			if (elitism && i == 0) {
				newGeneration[0] = getFittest(population);
				continue;
			}
			Individual father = tournamentSelection(population, tournamentSize);
			Individual mother = tournamentSelection(population, tournamentSize);
			newGeneration[i] = crossover(father, mother);
			if (mutationRate < Math.random())
				newGeneration[i].mutate();
		}
		
		return newGeneration;
	}
	
	public static Individual tournamentSelection(Individual [] population, int tournamentSize) {
		Random random = new Random(System.currentTimeMillis());
		Individual [] participants = new Individual[tournamentSize];
		
		for (int i = 0; i < tournamentSize; i++)
			participants[i] = population[random.nextInt(population.length)];
		
		return getFittest(participants);
	}
	
//	void updatePopulation(Individual[] updatedIndividuals) {
//		System.arraycopy(updatedIndividuals, 0, population, 0, populationSize);
//	}
	
	public static Individual crossover(Individual father, Individual mother) {
		Random random = new Random(System.currentTimeMillis());
		double initialX = random.nextBoolean() ? father.getInitialX() : mother.getInitialX();
		double initialY = random.nextBoolean() ? father.getInitialY() : mother.getInitialY();
		double initialDirection = random.nextBoolean() ? father.getInitialDirection() : mother.getInitialDirection();
		long initialDelay = random.nextBoolean() ? father.getInitialDelay() : mother.getInitialDelay();
		double velocity = random.nextBoolean() ? father.getVelocity() : mother.getVelocity();
		
		return new Individual(initialX, initialY, initialDirection, initialDelay, velocity);
	}
}
