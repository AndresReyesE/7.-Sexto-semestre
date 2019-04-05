

import java.util.*;

/**
 * Gerardo Ayala, 2017.
 *
 * Based on the version by Jeff Heaton
 * (http://www.jeffheaton.com) 1-2002
 * http://www2.sys-con.com/ITSG/virtualcd/Java/archives/0705/heaton/index.html
 * Java Neural Network Example
 * Handwriting Recognition
 * -------------------------------------------------
 */

abstract public class NeuralNetwork
{
    //The value to consider a neuron on
    public final static double NEURON_ON_THRESHOLD = 0.9;
    //The value to consider a neuron off
    public final static double NEURON_OFF_THRESHOLD = 0.1;
    // Neuron activations
    protected double neuronActivationValues[];
    // Mean square error of the network
    protected double meanSquareError;
    // Number of input units
    protected int numberOfInputUnits;
    // Number of neurons
    protected int numberOfNeurons;
    // Random number generator
    protected Random random = new Random(System.currentTimeMillis());
    // the connectionsStrengths
    double connectionsStrengths[][];
    // The training set.
    protected TrainingSet trainingSet;
    //


    //////////////////////////////////////////////////////////

    // Called to learn from training sets.
    abstract public void learn () throws RuntimeException;

    ///////////////////////////////////////////////////////////



    /**
     * Clear the weights.
     */
    public void clearConnectionsStrengths()
    {
        int x;
        int y;
        //
        meanSquareError = 1.0;

        y = 0;
        while(y < connectionsStrengths.length)
        {
            x = 0;
            while(x < connectionsStrengths[0].length)
            {
                connectionsStrengths[y][x] = 0;
                x = x + 1;
            }//end while
            y = y + 1;
        }//end while
    }//clearConnectionsStrengths



    // Called to initialize the connection strengths
    public void initializeConnectionStrengths()
    {
        double randomNumber;
        int temp;
        int i;
        int j;
        //
        // SQRT(12)=3.464...
        temp = (int)(3.464101615 / (2. * Math.random() )) ;
        i = 0;
        while(i < connectionsStrengths.length)
        {
            j = 0;
            while(j < connectionsStrengths[0].length)
            {
                randomNumber =  (double) random.nextInt(Integer.MAX_VALUE) +
                        (double) random.nextInt(Integer.MAX_VALUE) -
                        (double) random.nextInt(Integer.MAX_VALUE) -
                        (double) random.nextInt(Integer.MAX_VALUE);
                connectionsStrengths[i][j] = temp * randomNumber ;
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
    }//end initializeConnectionStregths



}//end class