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

public class TrainingSet
{
    protected int numberOfInputPatterns;
    protected int numberOfOutputPatterns;
    protected double inputPatterns[][];
    protected double outputPatterns[][];
    protected double classify[];
    protected int trainingSetSize;


    /**
     * The constructor.
     *
     * @param aNumberOfInputPatterns
     * @param aNumberOfOutputPatterns
     */
    TrainingSet(int aNumberOfInputPatterns,int aNumberOfOutputPatterns)
    {
        numberOfInputPatterns = aNumberOfInputPatterns;
        numberOfOutputPatterns = aNumberOfOutputPatterns;
        trainingSetSize = 0;
    }//end constructor



    /**
     * Get the input neuron count
     *
     * @return The input neuron count
     */
    public int getNumberOfInputPatterns()
    {
        return numberOfInputPatterns;
    }//end getNumberOfInputUnits




    /**
     * Get the output neuron count
     *
     * @return The output neuron count
     */
    public int getNumberOfOutputPatterns()
    {
        return numberOfOutputPatterns;
    }//end getOutputCount


    /**
     * Get the training set data.
     *
     * @return Training set data.
     */
    public int getTrainingSetSize()
    {
        return trainingSetSize;
    }//end getTrainingSetCount




    /**
     * Get an input set.
     *
     * @param set The entry requested.
     * @return The complete input set as an array.
     * @exception java.lang.RuntimeException
     */

    double[] getInputSet(int set) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetSize) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        return inputPatterns[set];
    }//end getInputSet




    /**
     * Set the number of entries in the training set. This method
     * also allocates space for them.
     *
     * @param aTrainingSetSize How many entries in the training set.
     */
    public void setTrainingSetSize(int aTrainingSetSize)
    {
        trainingSetSize = aTrainingSetSize;
        inputPatterns = new double[trainingSetSize][numberOfInputPatterns];
        outputPatterns = new double[trainingSetSize][numberOfOutputPatterns];
        classify = new double[trainingSetSize];
    }//end setTrainingSetCount



    /**
     * Set one of the training set's inputs.
     *
     * @param set The entry number
     * @param index The index(which item in that set)
     * @param value The value
     * @exception java.lang.RuntimeException
     */
    void setATrainingSetInput(int set,int index,double value) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetSize) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        if ( (index < 0) || (index >= numberOfInputPatterns) )
            throw(new RuntimeException("Training input index out of range:" + index ));
        //end if
        inputPatterns[set][index] = value;
    }//end setATrainingSetInput

}//end TrainingSet

