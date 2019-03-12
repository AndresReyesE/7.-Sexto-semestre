
public class ConcreteBuilderBalsa implements Builder
{
	String primeraParte;
	String segundaParte;
	String terceraParte;
	Product producto;
	//-----------
	
    public void buildPartOne()
    {
    	primeraParte = "/cortar troncos/";
    }//end buildPartOne
    
    
    public void buildPartTwo()
    {
    	segundaParte = "/amarrar los troncos/";
    }//end buildPartTwo
    
    
    public void buildPartThree()
    {
    	terceraParte = "";
    }//end buildPartThree
    
    
    public void ensambleProduct()
    {
    		producto = new Product(primeraParte + segundaParte + terceraParte);
    }//end ensambleProduct
    
    
    // se lleva control de quien solicita el objeto
    public Product getResult()
    {
    		return producto;
    }//end getResult


}//end class ConcreteBuilderBalsa
