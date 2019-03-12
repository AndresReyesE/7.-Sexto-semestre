/**
 * Created by 2924 on 26/09/2016.
 */
public class Situation
{
    int matrix[][];
    //

    public Situation()
    {
        matrix = new int[3][3];
    }//end constructor



    public void setMatrix(int[][] aMatrix)
    {
        matrix = aMatrix;
    }//end setMatrix


    @Override
    public boolean equals(Object anObject)
    {
        int i, j;
        boolean areEqual;
        Situation anotherState;
        //
        anotherState = (Situation) anObject;
        areEqual = true;
        i = 0;
        while (i < 3)
        {
            j = 0;
            while(j < 3)
            {
                if (matrix[i][j] != anotherState.matrix[i][j])
                    areEqual = false;
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return areEqual;
    }//end equals




    // HEURISTIC FUNCTION
    // FOR IMPLEMENTING A HEURISTIC SEARCH:
    // Manhattan Distance
    private double manhattanDistance()
    {
        int i, j;
        double manhattanDistance;
        int token;
        int targetX;
        int targetY;
        int differenceX;
        int differenceY;
        //------------------
        manhattanDistance = 0;
        i=0;
        while(i < 3)
        {
            j = 0;
            while(j < 3)
            {
                token = matrix[i][j];
                if (token != 0)
                { // we don't compute distance for element 0
                    targetX = (token - 1) / 3; // expected x-coordinate (row)
                    targetY = (token - 1) % 3; // expected y-coordinate (col)
                    differenceX = i - targetX; // x-distance to expected coordinate
                    differenceY = j - targetY; // y-distance to expected coordinate
                    manhattanDistance  = manhattanDistance +
                            Math.abs(differenceX) +
                            Math.abs(differenceY);
                }//end if
                j = j + 1;
            }//end while j
            i = i + 1;
        }//end while i
        return manhattanDistance;
    }//end manhattanDistance

    public double alreadyOrdered() {
        double acc = 0;
        for (int i = 0; i < 9; i++)
            if (matrix[i/3][i%3] == i + 1)
                acc++;

            return acc;
    }

    public double heuristicFunction()
    {
        double value;
        //------------
        value = manhattanDistance();
        if (matrix[0][0] == 1 && matrix[0][1] == 2 && matrix[0][2] == 3) {
            value = value - 50;
        }
        //value = 9 - alreadyOrdered() ;
        return value;
    }//end heuristicFunction



    public String toString()
    {
        String string;
        //
        string =  "\n";
        string = string + matrix[0][0] + "  ";
        string = string + matrix[0][1] + "  ";
        string = string + matrix[0][2] + "\n";
        string = string + matrix[1][0] + "  ";
        string = string + matrix[1][1] + "  ";
        string = string + matrix[1][2] + "\n";
        string = string + matrix[2][0] + "  ";
        string = string + matrix[2][1] + "  ";
        string = string + matrix[2][2] + "\n" + "\n";
        return string;
    }//end toString



}//end class Situation
