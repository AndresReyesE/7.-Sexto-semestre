
/**
 * Implementa la interface Builder
 * Construye y ensambla las partes del objeto producto
 * Provee el producto.
 * 
 * @author Gerardo Ayala
 *
 */
public class ConcreteBuilderCanoe implements Builder
{

	String primeraParte;
	String segundaParte;
	String terceraParte;
	Product producto;
	//-----------
	
    public void buildPartOne()
    {
    	primeraParte = "/corte de tiras de madera/";
    }//end buildPartOne
    
    
    public void buildPartTwo()
    {
    	segundaParte = "/pegado de tiras/";
    }//end buildPartTwo
    
    
    public void buildPartThree()
    {
    	terceraParte = "/cubierta de fibra de vidrio/";
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
    
}//end class ConcreteBuilderCanoe
