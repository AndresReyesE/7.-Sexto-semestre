
/**
 * Implementa la interface Builder
 * Construye y ensambla las partes del objeto producto
 * Provee el producto
 * 
 * @author Gerardo Ayala
 *
 */
public class ConcreteBuilderKayak implements Builder
{
	String primeraParte;
	String segundaParte;
	String terceraParte;
	Product producto;
	//-----------
	
    public void buildPartOne()
    {
    	primeraParte = "/corte de triplay/";
    }//end buildPartOne
    
    
    public void buildPartTwo()
    {
    	segundaParte = "/coser placas de triplay con alambre/pegar placas/";
    }//end buildPartTwo
    
    
    public void buildPartThree()
    {
    	terceraParte = "/cobertura de fibra de vidrio/pintado/";
    }//end buildPartThree
    
    
    public void ensambleProduct()
    {
		producto = new Product(primeraParte + 
				segundaParte + 
				terceraParte);
    }//end ensambleProduct
    
    
    // se lleva control de quien solicita el objeto
    public Product getResult()
    {
    		return producto;
    }//end getResult

}//end class ConcreteBuilderKayak
