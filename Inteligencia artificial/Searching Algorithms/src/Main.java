

/**
 * Created by gerardoayala on 8/15/17.
 * Some information at:
 * https://www.ijcai.org/Proceedings/93-1/Papers/035.pdf
 * https://www.cs.cornell.edu/courses/cs4700/2013fa/slides/CS4700-Search1_v6.pdf
 */
import java.util.Vector;
public class Main
{

    public static void showPlan(Vector<Node> plan)
    {
        int i;
        //
        i = 0;
        while( i < plan.size())
        {
            System.out.println(plan.get(i));
            i = i + 1;
        }//end while
        System.out.print("NUMBER OF MOVES: ");
        System.out.println(plan.size()-1);
    }//end showPlan




    public static void main(String[] args)
    {
        Situation initialState;
        Situation finalState;
        int[][] matrix;
        Result result;
        Vector<Node> plan;
        //


        // The Z problem
        // min : 31 moves
        matrix = new int[3][3];
        matrix[0][0] = 8;
        matrix[0][1] = 7;
        matrix[0][2] = 6;
        matrix[1][0] = 0;
        matrix[1][1] = 4;
        matrix[1][2] = 1;
        matrix[2][0] = 2;
        matrix[2][1] = 5;
        matrix[2][2] = 3;
        initialState = new Situation();
        initialState.setMatrix(matrix);

/*
        matrix = new int[3][3];
        matrix[0][0] = 4;
        matrix[0][1] = 0;
        matrix[0][2] = 6;
        matrix[1][0] = 2;
        matrix[1][1] = 5;
        matrix[1][2] = 1;
        matrix[2][0] = 7;
        matrix[2][1] = 8;
        matrix[2][2] = 3;
        initialState = new Situation();
        initialState.setMatrix(matrix);
        //
*/

        matrix = new int[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[0][2] = 3;
        matrix[1][0] = 4;
        matrix[1][1] = 5;
        matrix[1][2] = 6;
        matrix[2][0] = 7;
        matrix[2][1] = 8;
        matrix[2][2] = 0;
        finalState = new Situation();
        finalState.setMatrix(matrix);
        //
        result = BestFirst.search(initialState, finalState);
        plan = result.getPlan();
        showPlan(plan);

    }//end main

}//end Main
