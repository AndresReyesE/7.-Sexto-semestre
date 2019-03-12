/**
 * Modelo computacional del concepto Persona.
 * <p>
 * La clase es abstracta, pues no habran objetos de clase Persona.
 *
 * @author Gerardo Ayala
 * @version 1.1
 * @since December 2014
 *
 */

public abstract class Persona
{
    /**
     * Nombre de la persona.
     */
    protected String nombre;
    /**
     * Edad de la persona.
     */
    protected int edad;
    ///////////////



    /////// Metodos SETs /////////////////////////

    /**
     *
     * @param unNombre Objeto a asignar como nombre de la persona.
     */
    protected void setNombre(String unNombre)
    {
        nombre = unNombre;
    }//end setNombre


    /**
     *
     * @param unaEdad Valor a asignar como edad de la persona. Debe ser un valor entre 1 y 99.
     */
    protected void setEdad(int unaEdad)
    {
        if((unaEdad >0) && (unaEdad < 100))
            edad = unaEdad;
        //end if
        // else: habria que manejar la excepcion.
    }//end setEdad



    /////// Metodos GET /////////////////////////


    /**
     *
     * @return El nombre de la persona.
     */
    protected String getNombre()
    {
        return nombre;
    }//end getNombre


    /**
     *
     * @return La edad de la persona.
     */
    protected int getEdad()
    {
        return edad;
    }//end getEdad


}//end class Persona