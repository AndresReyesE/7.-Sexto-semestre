public class FunctionLibrary
{


    public static void showVector(double[] vector)
    {
        int i;
        //
        System.out.print("[");
        i = 0;
        while(i < vector.length)
        {
            System.out.print(vector[i] + "  ");
            i = i + 1;
        }//end while
        System.out.println("]");
    }//end showVector




    public static void showMatrix(double[][] matrix)
    {
        int i;
        int j;
        //
        i = 0;
        while (i < matrix.length)
        {
            System.out.print("OUTPUT NEURON: "+ i + " = ");
            j = 0;
            while(j < matrix[0].length)
            {
                System.out.print(matrix[i][j] + "  ");
                j = j + 1;
            }//end while
            System.out.println("");
            i = i + 1;
        }//end while
    }//end showConnectionsStrengths



    // Called to calculate a dot product.
    public static double dotProduct(double vectorA[] , double vectorB[])
    {
        int i;
        int k;
        int m;
        double result;
        //
        result = 0.0;
        k = vectorA.length / 4;
        m = vectorA.length % 4;
        i = 0;
        while ( (k--) > 0 )
        {
            result += vectorA[i] * vectorB[i];
            result += vectorA[i+1] * vectorB[i+1];
            result += vectorA[i+2] * vectorB[i+2];
            result += vectorA[i+3] * vectorB[i+3];
            i+=4;
        }//end while
        while ( (m--) > 0 )
        {
            result += vectorA[i] * vectorB[i];
            i = i + 1;
        }//end while
        return result;
    }//end dotProduct


    // Calculate the length of a vector.
    public static double vectorLength(double vector[])
    {
        double sum;
        int i;
        //
        sum = 0.0;
        i = 0;
        while(i < vector.length)
        {
            sum = sum + (vector[i] * vector[i]);
            i = i + 1;
        }//end while
        return Math.sqrt(sum);
    }//end vectorLength


}//end class FunctionLibrary
