/**
 * 
 * @author Gerardo Ayala
 *
 * BUILDER DESIGN PATTERN (code example)
 * 
 * PROPOSITO
 * =========
 * 
 * Separar la construccion de objetos complejos
 * de su representacion.
 * El mismo proceso de construccion puede crear diferentes
 * productos (representaciones) 
 * 
 * MOTIVACION
 * ==========
 * 
 * Posibilidad de crear nuevos productos (objetos) de un tipo
 * sin la necesidad de modificar el proceso generico de
 * su construccion.
 * Un Director mantiene la indicacion del proceso, mientras
 * constructores concretos de los productos llevan a cabo 
 * la construccion
 * siguiendo los lineamientos de la Interface Builder
 * 
 * APLICABILIDAD
 * =============
 * 
 * Cuando el proceso (algoritmo) de creacion de objetos complejos
 * (que consisten en partes) debe ser independiente de 
 * dichas partes 
 * y de como se ensamblan dichas partes.
 * 
 * 
 * PARTICIPANTES
 * ============
 * 
 * Builder:
 * Interface que indica que acciones se deben llevar a cabo para
 * la construccion de los objetos complejos.
 * 
 * ConcreteBuilder:
 * Implementa la Interface Builder.
 * Construye cada parte del objeto complejo correspondiente
 * y las ensambla para construir el objeto.
 * Determina la representacion del objeto complejo.
 * Tiene un metodo para proveer el objeto (producto) ensamblado.
 * 
 * Product:
 * Representacion del objeto complejo.
 * 
 * COLABORACION
 * ============
 * 
 * El cliente crea un objeto Director y lo configura con
 * la indicacion del objeto (producto) que se desea construir.
 * El Director indica paso por paso al ConcreteBuilder 
 * la construccion y ensablado del objeto complejo.
 * El ConcreteBuilder lleva a cabo la construccion de cada parte
 * y el ensamblado.
 * El cliente obtiene el objeto por parte del ConcreteBuilder.
 * 
 * 
 * CONSECUENCIAS
 * =============
 * 
 * Se puede cambiar la representacion interna del producto.
 * Separa el codigo del proceso de construccion.
 * Mayor control en el proceso de construccion de objetos complejos.
 * 
 * 
 * IMPLEMENTACION
 * ==============
 * 
 * Una Interface (Builder) define las posibles operaciones 
 * de construccion
 * de objetos complejos.
 * Un Director representa la secuencia de dichas operaciones.
 * El ConcreteBuilder implementa dichas operaciones 
 * y provee el objeto complejo.
 * 
 * USOS
 * ====
 * 
 * Los algoritmos de creacion de objetos complejos
 * deben estar separados.
 * Se requieren multiples representaciones de algoritmos de creacion.
 * Se requiere control en el proceso de creacion, en tiempo de ejecucion.
 * 
 *
 */
public class BuilderPattern
{
    public static void main(String[] args) 
    {
    		Director objetoDirector;
    		ConcreteBuilderKayak kayakBuilder;
    		ConcreteBuilderCanoe canoeBuilder;
     		ConcreteBuilderBalsa balsaBuilder;
    		Product kayak;
    		Product canoe;
    		Product balsa;
    		//--------------
     		kayakBuilder = new ConcreteBuilderKayak();
    		objetoDirector = new Director(kayakBuilder);
    		objetoDirector.construct();
    		kayak = kayakBuilder.getResult();
    		System.out.println(kayak);

    		canoeBuilder = new ConcreteBuilderCanoe();
    		objetoDirector = new Director(canoeBuilder);
    		objetoDirector.construct();
    		canoe = canoeBuilder.getResult();
    		System.out.println(canoe);
    		
    		balsaBuilder = new ConcreteBuilderBalsa();
     		objetoDirector = new Director(balsaBuilder);
     		objetoDirector.construct();
     		balsa = balsaBuilder.getResult();
    		System.out.println(balsa);
    	
    }//end main
    
}//end class ProyectoBuilder
