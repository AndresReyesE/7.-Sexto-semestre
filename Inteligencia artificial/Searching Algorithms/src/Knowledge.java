import java.util.Vector;

/**
 * Created by 2924 on 28/09/2016.
 */
public abstract class Knowledge
{
    public static Vector<String> getCommitments(Situation state)
    {
        Vector<String> candidates;
        //
        candidates = new Vector<String>();
        if (state.matrix[0][0] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[0][0] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[0][1] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[0][1] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[0][1] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[0][2] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[0][2] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[1][0] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[1][0] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[1][0] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[1][2] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[1][2] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[1][2] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[2][0] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[2][0] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[2][1] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[2][1] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[2][1] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[2][2] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[2][2] == 0)
            candidates.add("right");
        //end if

        return candidates;
    }//end getCommitments


    private static Node createNode(Situation aState,
                                   String aCommitment)
    {
        Node newNode;
        Situation newState;
        int rowWithZero;
        int columnWithZero;
        int i, j;
        int[][] newMatrix;
        //
        newMatrix = new int[3][3];
        rowWithZero = -1;
        columnWithZero = -1;
        i=0;
        while(i<3)
        {
            j=0;
            while(j < 3)
            {
                newMatrix[i][j]=aState.matrix[i][j];
                if(aState.matrix[i][j] == 0)
                {
                    rowWithZero = i;
                    columnWithZero = j;
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while

        /// Commitment interpretation
        if(aCommitment.equals("up"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    aState.matrix[rowWithZero+1][columnWithZero];
            newMatrix[rowWithZero+1][columnWithZero]= 0;
        }//end if
        if(aCommitment.equals("down"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    aState.matrix[rowWithZero-1][columnWithZero];
            newMatrix[rowWithZero-1][columnWithZero]= 0;
        }//end if
        if(aCommitment.equals("right"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    aState.matrix[rowWithZero][columnWithZero-1];
            newMatrix[rowWithZero][columnWithZero-1]= 0;
        }//end if
        if(aCommitment.equals("left"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    aState.matrix[rowWithZero][columnWithZero+1];
            newMatrix[rowWithZero][columnWithZero+1]= 0;
        }//end if

        newNode = new Node();
        newState = new Situation();
        newState.setMatrix(newMatrix);
        newNode.setState(newState);
        return newNode;
    }//end createNode




    public static Vector<Node> getNextPossibleNodes(Situation state)
    {
        Vector<Node> possibleNodes;
        Node node;
        Vector<String> commitments;
        String aCommitment;
        int i;
        //
        possibleNodes = new Vector<Node>();
        commitments = getCommitments(state);
        i=0;
        while(i < commitments.size())
        {
            aCommitment = commitments.get(i);
            node = createNode(state,aCommitment);
            possibleNodes.add(node);
            i = i + 1;
        }//end while

        return possibleNodes;
    }//end getNextPossibleNodes




}//end class
