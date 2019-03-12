/**
 * Created by gerardoayala on 1/21/16.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;


public class Mediador implements Controller
{
    EstructuraDeDatos model;
    Interfaz view;
    //
    Dialogo dialogo;
    int indice;
    boolean interfazEstaLimpia;
    ///////////////////////////


    public Mediador(EstructuraDeDatos theModel, Interfaz theView)
    {
        model = theModel;
        view = theView;
        indice = 0;
        interfazEstaLimpia = false;
    }//end constructor



    ///////// Métodos de obtención de datos del Model /////////////

    public Estudiante obtieneDatoDelModel(int indice)
    {
        Estudiante dato;
        /////////////////
        dato = null;
        if(model.hayDatos())
            dato = model.get(indice);
        //end if
        return dato;
    }//end obtieneDatoDelModel



    ///////// Métodos de obtención de datos del View /////////////

    public Estudiante obtieneDatoDelView()
    {
        Estudiante dato;
        //
        String nombre;
        String carrera;
        int edad;
        int unidades;
        //
        String edadTexto;
        String unidadesTexto;
        Integer objetoInteger;
        /////////////////

        nombre = view.campoNombre.getText();
        carrera = view.campoCarrera.getText();

        edadTexto = view.campoEdad.getText();
        objetoInteger = new Integer(edadTexto);
        edad = objetoInteger.intValue();

        unidadesTexto = view.campoUnidades.getText();
        objetoInteger = new Integer(unidadesTexto);
        unidades = objetoInteger.intValue();

        dato = new Estudiante(nombre, edad);
        dato.setCarrera(carrera);
        dato.setUnidades(unidades);
        return dato;
    }//end obtieneDatoDelView


    ///////// Métodos de actualización del View /////////////


    private double procesamientoPorParteDelModel(int unIndice)
    {
        return model.procesa(unIndice);
    }//end procesamientoPorParteDelModel



    public void actualizaElView()
    {
        Estudiante dato;
        int edad;
        int unidades;
        double colegiatura;
        Double objetoDouble;
        Integer objetoInteger;
        /////////////////

        dato = obtieneDatoDelModel(indice);
        if(dato != null)
        {
            view.campoNombre.setText(dato.getNombre());
            view.campoCarrera.setText(dato.getCarrera());
            edad = dato.getEdad();
            objetoInteger = new Integer(edad);
            view.campoEdad.setText(objetoInteger.toString());
            unidades = dato.getUnidades();
            objetoInteger = new Integer(unidades);
            view.campoUnidades.setText(objetoInteger.toString());
            colegiatura = procesamientoPorParteDelModel(indice);
            objetoDouble = new Double(colegiatura);
            view.campoColegiatura.setText(objetoDouble.toString());
            interfazEstaLimpia = false;
        }//end if
        else
        {
            limpiaInterfaz();
        }//end else
    }//end actualizaElView


    private void limpiaInterfaz()
    {
        view.campoNombre.setText("");
        view.campoCarrera.setText("");
        view.campoEdad.setText("");
        view.campoUnidades.setText("");
        view.campoColegiatura.setText("");
        interfazEstaLimpia = true;
    }//end limpiaInterfaz




    ///////// Métodos de actualización del Model /////////////

    public void solicitaActualizacionDelModel(String accion)
    {
        Estudiante dato;
        /////////////////
        if(accion.equals("agregar"))
        {
            dato = obtieneDatoDelView();
            indice = indice + 1;
            model.agregaDatosALaEstructura(indice, dato);
        }//end if

        if(accion.equals("eliminar"))
        {
            model.eliminaDatosDeLaEstructura(indice);
        }//end if

        if(accion.equals("modificar"))
        {
            dato = obtieneDatoDelView();
            model.modificaDatosEnLaEstructura(indice, dato);
        }//end if

        if(accion.equals("ordenar"))
        {
            model.ordenaLaEstructura();
        }//end if

    }//end actualizaElModel




    ///////// Métodos de control  /////////////

    private void decrementaApuntador()
    {
        if(indice == 0)
            indice = model.size()-1;
        else
            indice = indice - 1;
        //end if else
    }//end decrementaApuntador


    private void incrementaApuntador()
    {
        if(indice == model.size()-1)
            indice = 0;
        else
            indice = indice  + 1;
        //end if else
    }//end incrementaApuntador



    public void actionPerformed(ActionEvent evento)
    {
        Button botonAccionado;
        ///////////////////////

        botonAccionado = (Button) evento.getSource();

        if(botonAccionado == view.botonAnterior)
        {
            decrementaApuntador();
            actualizaElView();
        }//end if


        if(botonAccionado == view.botonSiguiente)
        {
            incrementaApuntador();
            actualizaElView();
        }//end if


        if(botonAccionado == view.botonAgregar)
        {
            if(interfazEstaLimpia)
            {
                solicitaActualizacionDelModel("agregar");
                actualizaElView();
            }//end if
        }//end if


        if(botonAccionado == view.botonEliminar)
        {
            if(model.hayDatos())
            {
                dialogo = new Dialogo(view, "¿Deseas eliminar este registro?");
                if(dialogo.seAceptaAccion())
                {
                    solicitaActualizacionDelModel("eliminar");
                    decrementaApuntador();
                    actualizaElView();
                }//end if
            }//end if
        }//end if


        if(botonAccionado == view.botonModificar)
        {
            solicitaActualizacionDelModel("modificar");
            actualizaElView();
        }//end if


        if(botonAccionado == view.botonOrdenar)
        {
            solicitaActualizacionDelModel("ordenar");
            indice = 0;
            actualizaElView();
        }//end if


        if(botonAccionado == view.botonSalvar)
        {
            dialogo = new Dialogo(view, "¿Deseas salvar  los datos?");
            if(dialogo.seAceptaAccion())
                model.salvaDatosDeLaEstructuraAlRepositorio();
            //end if
        }//end if


        if(botonAccionado == view.botonLimpiar)
        {
            limpiaInterfaz();
        }//end if
    }//end actionPerformed

}//end class Mediator
