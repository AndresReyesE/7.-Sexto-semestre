import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Node implements Comparable
{
    private Node parent;
    Vector<Node> children;
    private Situation state;
    double heuristicValue;
    static int numberOfExpantions = 0;
    static Random random = new Random(System.currentTimeMillis());
    //


    public Node()
    {
        children = null;
    }//end constructor


    public void setState(Situation aState)
    {
        state = aState;
        heuristicValue = state.heuristicFunction();
    }//end setState



    public Situation getState()
    {
        return state;
    }//end getState




    public void setParent(Node aNode)
    {
        parent = aNode;
    }//end setParent





    public Node getParent()
    {
        return parent;
    }//end getParent




    public Vector<Node> getChildren()
    {
        return children;
    }//end getChildren



    public Vector<Node> getRoute()
    {
        Vector<Node> route;
        Node node;
        //-----------------
        route = new Vector<Node>();
        route.add(this);
        node = getParent();
        while(node != null)
        {
            route.add(node);
            node = node.getParent();
        }//end while
        Collections.reverse(route);
        return route;
    }//end getRoute



    private Vector<Node> changeOrderIfTwoBestChilds(Vector<Node> orderedChildren)
    {
        Node aux;
        //
        if(orderedChildren.size() >= 2)
        {
            if (orderedChildren.get(0).heuristicValue ==
                    (orderedChildren.get(1).heuristicValue))
            {
                if (random.nextInt() > 50)
                {
                    // swap 0 <-> 1
                    aux = orderedChildren.get(0);
                    orderedChildren.setElementAt(orderedChildren.get(1), 0);
                    orderedChildren.setElementAt(aux, 1);
                }//end if
            }//end if
        }//end if
        return orderedChildren;
    }//end changeOrderIfTwoBestChilds



    public void sortChildren()
    {
        //
        Collections.sort(children);
        children = changeOrderIfTwoBestChilds(children);
    }//end sortChildren



    public void expand()
    {
        int i;
        //--------------------------
        numberOfExpantions = numberOfExpantions + 1;
        children = Knowledge.getNextPossibleNodes(state);
        if (children != null)
        {
            i = 0;
            while (i < children.size())
            {
                children.get(i).setParent(this);
                i = i + 1;
            }//end while
        }//end while
    }//end expand






    public int compareTo(Object anObject)
    {
        Node aNode;
        //
        aNode = (Node) anObject;
        if(heuristicValue < aNode.heuristicValue)
            return -1;
        else
            if(heuristicValue > aNode.heuristicValue)
                return 1;
            else
                return 0;
            //end if-else
        //end if-else
    }//end compareTo



    public String toString()
    {
        return state.toString();
    }//end toString

}//end class Node





