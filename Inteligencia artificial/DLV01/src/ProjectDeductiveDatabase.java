
import java.util.List;
import java.util.Vector;

import it.unical.mat.dlv.program.Term;
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
		List<Term> terms;


		//

		database = new DeductiveDatabase("/bin/dlv");
		database.setFacts("/home/reyes/UDLAP/Inteligencia artificial/DLV01/src/facts");
		database.setRules("/home/reyes/UDLAP/Inteligencia artificial/DLV01/src/rules");

		Fact fact;
		fact = new Fact("man");
		fact.addArgument("oscar");
        database.addFact(fact);

        fact = new Fact("age");
        fact.addArgument("oscar");
        fact.addArgument(21);
        database.addFact(fact);

        fact = new Fact("woman");
		fact.addArgument("anahi");
        database.addFact(fact);

        fact = new Fact("age");
        fact.addArgument("anahi");
        fact.addArgument(13);
        database.addFact(fact);

        database.removeFact("age(oscar, 21)");

        fact = new Fact ("woman");
        fact.addArgument("anahi");
		database.removeFact(fact);

		fact = new Fact("man");
		fact.addArgument("charly");
		database.addNegationFact(fact);

        if (database.isTrue("man(paul)"))
            System.out.println("Paul is a man");
        else
            System.out.println("We don't know if Paul's a man");

        System.out.println(" == ANSWER SET ====");
		answerSet = database.getAnswerSet();
		//presentaAnswerSet(answerSet);


        for (int i = 0; i < answerSet.size(); i++) {
            String aws = answerSet.get(i).toString();
            System.out.println("Answer set[" + i + "]: " + aws.substring(0, aws.indexOf("(")));
            terms = answerSet.get(i).attributes();
            for (int j = 0; j < terms.size(); j++)
                System.out.println("Arg " + j + ": " + terms.get(j).toString());
        }

		
	}//end main
	
}//end ProjectDeductiveDatabase