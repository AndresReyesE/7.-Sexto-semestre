
import java.util.Vector;
import it.unical.mat.wrapper.FactResult;

public class ProjectDeductiveDatabase 
{
	static DeductiveDatabase database;
	static Vector<FactResult>  answerSet;

	
	public static void presentaAnswerSet(Vector<FactResult> anAnswerSet)
	{
		int i;
		
		System.out.println("================");
		i = 0;
		while( i < anAnswerSet.size())
		{
			System.out.println(anAnswerSet.get(i));
			i = i + 1;
		}//end while
	}//end presentaAnswerSet
	
	
	
	public static void main(String args[])
	{

		Fact aFact;
		//

		database = new DeductiveDatabase("/Users/gerardoayala/Desktop/dlv/dlv.bin");
		database.setFacts("/Users/gerardoayala/Desktop/dlv/01-introduction.txt");
		
		System.out.println(" == ANSWER SET ====");
		answerSet = database.getAnswerSet();
		presentaAnswerSet(answerSet);

		aFact = new Fact("likes");
		aFact.addArgument("ringo");
		aFact.addArgument("selena");
		database.addFact(aFact);

		aFact = new Fact("man");
		aFact.addArgument("pepito");
		database.addFact(aFact);

		aFact = new Fact("friends");
		aFact.addStringArgument("Olivia Newton");
		aFact.addStringArgument("John Travolta");
		database.addFact(aFact);

		aFact = new Fact("age");
		aFact.addArgument("ringo");
		aFact.addArgument(73);
		database.addFact(aFact);

		System.out.println(" == ANSWER SET ====");
		answerSet = database.getAnswerSet();
		presentaAnswerSet(answerSet);

		aFact = new Fact("man");
		aFact.addArgument("pepito");
		database.removeFact(aFact);

		System.out.println(" == ANSWER SET ====");
		answerSet = database.getAnswerSet();
		presentaAnswerSet(answerSet);

		
	}//end main
	
}//end ProjectDeductiveDatabase




