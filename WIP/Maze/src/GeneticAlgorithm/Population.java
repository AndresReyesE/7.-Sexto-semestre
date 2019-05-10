package GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

/*
 * Carlos Andrés Reyes Evangelista
 * Universidad de las Américas Puebla
 * Ingeniería en Sistemas Computacionales
 *
 * May 9, 2019
 */

/**
 * Abstract class that implements static methods to manage populations, i.e., collections of Individuals
 */
abstract class Population {
	
	/**
	 * Returns, if any, the Individual with the highest fitness value
	 * @param participants a collection of Individuals
	 * @return an Individual with the highest fitness value or the first one if they all have the same
	 */
	static Individual getFittest(Individual [] participants) {
		double bestFitnessValue = participants[0].getFitnessValue();
		Individual currentFittest = participants[0];
		
		for (Individual individual : participants)
			if (individual.getFitnessValue() > bestFitnessValue) {
				bestFitnessValue = individual.getFitnessValue();
				currentFittest = individual;
			}
		
		return currentFittest;
	}
	
	/**
	 * Creates a collection of randomly generated Individuals
	 * @param populationSize the amount of Individuals to generate
	 * @return the collection of individuals
	 */
	static Individual [] initialize(int populationSize) {
		//Create an infinite stream of random values based on the current system time
		Random random = new Random(System.currentTimeMillis());
		
		Individual [] population = new Individual[populationSize];
		
		double initialX, initialY, initialDirection, velocity;
		long initialDelay;
		
		for (int i = 0; i < populationSize; i++) {
			initialX =          random.nextDouble() * 100;          //random double in [0, 100] -pixels
			initialY =          random.nextDouble() * 100;          //random double in [0, 100] -pixels
			initialDirection =  random.nextDouble() * Math.PI * 2;  //random double in [0, 2π] -radians
			initialDelay =      random.nextLong() % 2000;           //random long in [0, 2000) -milliseconds
			velocity =          random.nextDouble() * 2 + 1;        //random double in [1, 3] -pixels/frame
			
			population[i] = new Individual(initialX, initialY, initialDirection, Math.abs(initialDelay), velocity);
		}
		
		return population;
	}
	
	/**
	 * * Evolve a given population, i.e., generate a new one from it by:
	 * 	 * 1. If elitism is true, the fittest Individual is gotten from the given generation and cloned into the new one
	 * 	 * To generate each of the new Individuals:
	 * 	 * 2. Get a father and a mother winners of a tournament
	 * 	 * 3, Generate a new individual as the result of a crossover among the progenitors
	 * 	 * 4. With a given likelihood, mutate this new individual
	 * @param population the collection of not null individuals from which the new population will be generated
	 * @param populationSize the amount of individuals the populations must have
	 * @param tournamentSize the amount of individuals that can participate in a tournament
	 * @param elitism a flag indicating if the fittest individual of the population must be conserved
	 * @param mutationRate the probability [0, 1] the new individuals have to mutate when born
	 * @return a new population evolved from the given one
	 */
	static Individual [] evolve (Individual [] population, int populationSize, int tournamentSize, boolean elitism, double mutationRate) {
		Individual [] newGeneration = new Individual[populationSize];
		
		for (int i = 0; i < populationSize; i++) {
			//if elitism is enabled the first iteration will only clone the fittest individual
			if (elitism && i == 0) {
				newGeneration[0] = getFittest(population);
				continue;
			}
			
			//the father and the mother are obtained as winners of a tournament
			Individual father = tournamentSelection(population, tournamentSize);
			//they can't be the same individual so we'll get a winner until it is different than the previous one
			Individual mother;
			do {
				mother = tournamentSelection(population, tournamentSize);
			} while (father == mother);
			
			//Generate a new individual as the result of a crossover among the progenitors
			newGeneration[i] = crossover(father, mother);
			
			//With a given likelihood, mutate this new individual
			if (mutationRate < Math.random())
				newGeneration[i].mutate();
		}
		
		return newGeneration;
	}
	
	/**
	 * A tournament is defined as follows:
	 * 1. Given a population, a fixed amount of participants are chosen randomly
	 * 2. The winner is the one with the highest fitness value
	 * @param population the set of participants that may participate in the tournament
	 * @param tournamentSize the amount of participants that can be chosen to compete
	 * @return the fittest individual among the chosen ones
	 */
	private static Individual tournamentSelection(Individual [] population, int tournamentSize) {
		Random random = new Random(System.currentTimeMillis());
		Individual [] participants = new Individual[tournamentSize];
		
		for (int i = 0; i < tournamentSize; i++) {
			Individual chosen;
			//the participants cannot be repeated
			do {
				chosen = population[random.nextInt(population.length)];
			} while (Arrays.asList(participants).contains(chosen));
			
			participants[i] = chosen;
		}
		
		return getFittest(participants);
	}
	
	/**
	 * The crossover is the operation by which a new individual can be generated based on the genes of two
	 * different individuals by choosing randomly among them
	 * @param father an individual winner of a tournament
	 * @param mother another individual winner of a tournament
	 * @return a "child" with the genes of the two given objects
	 */
	private static Individual crossover(Individual father, Individual mother) {
		//Create an infinite stream of random values based on the current system time
		Random random = new Random(System.currentTimeMillis());
		
		//from that stream we obtain random boolean values, if the random value is true the gene is gotten from
		//the father, if it is false then it is gotten from the mother
		double  initialX        = random.nextBoolean() ? father.getInitialX() : mother.getInitialX();
		double  initialY        = random.nextBoolean() ? father.getInitialY() : mother.getInitialY();
		double  initialDir      = random.nextBoolean() ? father.getInitialDirection() : mother.getInitialDirection();
		long    initialDelay    = random.nextBoolean() ? father.getInitialDelay() : mother.getInitialDelay();
		double  velocity        = random.nextBoolean() ? father.getVelocity() : mother.getVelocity();
		
		//and a new individual is created with those chosen genes
		return new Individual(initialX, initialY, initialDir, initialDelay, velocity);
	}
}
