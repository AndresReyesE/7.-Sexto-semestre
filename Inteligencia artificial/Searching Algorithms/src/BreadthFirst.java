
import java.util.Vector;

/**
 * Created by gerardoayala on 3/16/16.
 */
public abstract class BreadthFirst extends Search
{

    private static Result breadthFirst(Vector<Node> aGeneration,
                                       Node goal)
    {
        int i;
        int j;
        Node node;
        Vector<Node> nextGeneration;
        Node nextChild;
        Vector<Node> route;
        //---------------------

        if(!found)
        {
            if (aGeneration != null)
            {
                nextGeneration = new Vector<Node>();
                i = 0;
                while (i < aGeneration.size() && (!found))
                {
                    node = aGeneration.get(i);
                    node.expand();
                    memory.add(node.getState());
                    if(node.getChildren() != null)
                    {
                        j = 0;
                        while(j < node.getChildren().size() && (!found))
                        {
                            nextChild = node.getChildren().get(j);
                            if(!memory.contains(nextChild))
                            {
                                if (nodeIsFinalState(nextChild, goal))
                                {
                                    found = true;
                                    foundNode = nextChild;
                                }//end if
                                nextGeneration.add(nextChild);
                            }//end if
                            j = j + 1;
                        }//end while j
                    }//end if
                    i = i + 1;
                }//end while i
                if(nextGeneration.size() > 0)
                    // recursive call
                    breadthFirst(nextGeneration, goal);
                //end if
                if(found)
                {
                    result.setFound(true);
                    route = foundNode.getRoute();
                    result.setPlan(route);
                }//end if
                else
                {
                    result.setFound(false);
                    result.setPlan(null);
                }//end else
            }//end if
        }//end if
        return result;
    }//end breadthFirst



    public static Result search(Situation initialState, Situation finalState)
    {
        Vector<Node> route;
        Vector<Node> founderGeneration;
        Node root;
        Node goal;
        //
        root = new Node();
        root.setState(initialState);
        goal = new Node();
        goal.setState(finalState);
        //
        memory = new Vector<Situation>();
        found = false;
        result = new Result();

        if (!nodeIsFinalState(root, goal))
        {
            founderGeneration = new Vector<Node>();
            founderGeneration.add(root);
            result = breadthFirst(founderGeneration, goal);
        }//end if
        else
        {
            result.setFound(true);
            route = new Vector<Node>();
            route.add(root);
            result.setPlan(route);
        }//end else

        return result;
    }//end search


}//end class
