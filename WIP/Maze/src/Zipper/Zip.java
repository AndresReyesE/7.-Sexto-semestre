package Zipper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import GraphicElements.Bouncer;
import GeneticAlgorithm.Individual;

public class Zip {
	
	public static <Bouncer, Individual>ArrayList<Tuple<Bouncer, Individual>> zip (List<Bouncer> firstList, List<Individual> secondList) {
		Iterator <Bouncer> firstIterator = firstList.iterator();
		Iterator <Individual> secondIterator = secondList.iterator();
		
		ArrayList <Tuple <Bouncer, Individual>> zipLists = new ArrayList<>();
		while (firstIterator.hasNext() && secondIterator.hasNext())
			zipLists.add(new Tuple<>(firstIterator.next(), secondIterator.next()));
		
		return zipLists;
	}
}
