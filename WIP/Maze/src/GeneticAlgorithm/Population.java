package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	
	private int populationSize;
	private Individual [] population;
	
	public Population(int populationSize) {
		this.populationSize = populationSize;
		this.population = new Individual[populationSize];
	}
	
	/*
	GETTER
	 */
	public Individual[] getPopulation() {
		return population;
	}
	
	/**
	 * Initialize this population by generating a set of populationSize random individuals
	 */
	public void initialize () {
		Random random = new Random(System.currentTimeMillis());
		
		double initialX, initialY, initialDirection, velocity;
		long initialDelay;
		for (int i = 0; i < populationSize; i++) {
			initialX = random.nextDouble() * 100;
			initialY = random.nextDouble() * 100;
			initialDirection = random.nextDouble() * Math.PI * 2;
			initialDelay = random.nextLong() % 3000;
			velocity = random.nextDouble() * 5 + 1;
			
			population[i] = new Individual(initialX, initialY, initialDirection, Math.abs(initialDelay), velocity);
		}
	}
	
	public void evolve (boolean elitism, double mutationRate) {
		Individual [] newGeneration = new Individual[populationSize];
		
		for (int i = 0; i < populationSize; i++) {
//			if (elitism && i == 0) {
//				newGeneration[0] = getFittest ();
//				continue;
//			}
//			Individual father = GeneticAlgorithm.tournamentSelection(population);
//			Individual mother = GeneticAlgorithm.tournamentSelection(population);
//			newGeneration[i] = crossover(father, mother);
		}
	}
	
}
