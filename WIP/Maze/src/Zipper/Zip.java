package Zipper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Personal representation of a zip functional function that encapsulates two collections into one by setting the
 * elements of this new list as an aggregate object that contains the nth element from both original lists
 * It is an iterable collection
 */
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
