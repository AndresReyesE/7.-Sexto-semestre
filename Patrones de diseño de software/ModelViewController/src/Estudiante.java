/**
 * Modelo computacional del concepto Estudiante.
 * <p>
 * La clase hereda de la clase Persona e implementa la Interface Comparable.
 *
 * @author Gerardo Ayala
 * @version 1.1
 * @since December 2014
 *
 */
public class Estudiante extends Persona implements Comparable<Estudiante>
{
    /**
     * Numero de unidades que cursa el estudiante.
     */
    private int unidades;
    /**
     * Carrera que cursa el estudiante.
     */
    private String carrera;
    /**
     * Colegiatura a cubrir por el estudiante.
     */
    private double colegiatura;
    ////////////////////////
    /**
     * Metodo constructor.
     * @param unNombre Nombre a asignar al estudiante.
     * @param unaEdad Edad a asignar al estudiante.
     */
    public Estudiante(String unNombre, int unaEdad)
    {
        // Se invoca, antes que nada, al constructor de la superclase
        super();
        setNombre(unNombre);
        setEdad(unaEdad);
        unidades = -1;
        colegiatura = -1;
        carrera = "";
    }//end constructor



    ///// Metodos GET ////////////////////////////////////////////////

    /**
     *
     * @return Numero de unidades que cursa el estudiante.
     */
    public int getUnidades()
    {
        return unidades;
    }// end getId


    /**
     *
     * @return La carrera que estudia el estudiante.
     */
    public String getCarrera()
    {
        return carrera;
    }//end getCarrera

    /**
     *
     * @return La colegiatura a cubrir por el estudiante.
     */
    public double getColegiatura()
    {
        return colegiatura;
    }//end getTareas


    ///// Metodos SET ////////////////////////////////////////////////

    /**
     *
     * @param unasUnidades Numero de unidades a asignar al estudiante.
     */
    public void setUnidades(int unasUnidades)
    {
        unidades = unasUnidades;
    }//end setUnidades

    /**
     *
     * @param unaCarrera Carrera a asignar al estudiante.
     */
    public void setCarrera(String unaCarrera)
    {
        carrera = unaCarrera;
    }//end setCarrera

    /**
     *
     * @param unaColegiatura Colegiatura a asignar al estudiante.
     */
    public void setColegiatura(double unaColegiatura)
    {
        colegiatura = unaColegiatura;
    }//end setCarrera



    /**
     * Implementacion de la Interface Comparable.
     * @param otroO Otro objeto de clase Estudiante, con el cual se va a comparar.
     * @return 1 si el nombre del objeto es mayor al del otro objeto;
     * 0 si son iguales; -1 si el nombre del objeto es menor al del otro objeto.
     */
    public int compareTo(Estudiante otroEstudiante)
    {
        int otraEdad;
        //Estudiante otroEstudiante;
        //////////////////
        //otroEstudiante = (Estudiante) otroObjeto;
        otraEdad = otroEstudiante.getEdad();
        // ?? ? ???
        if(edad > otraEdad)
            return 1;
        else
        if(edad < otraEdad)
            return -1;
        else
            return 0;
        //end if else
        //end if else
    }//end compareTo

}//end class Estudiante