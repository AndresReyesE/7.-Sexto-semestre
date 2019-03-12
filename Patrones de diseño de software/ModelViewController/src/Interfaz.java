/**
 * Created by gerardoayala on 1/21/16.
 */
import java.awt.Frame;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Color;


public class Interfaz extends Frame implements View
{
    Button botonSiguiente;
    Button botonAnterior;
    Button botonSalvar;
    Button botonOrdenar;
    Button botonEliminar;
    Button botonLimpiar;
    Button botonAgregar;
    Button botonModificar;

    TextField campoNombre;
    TextField campoEdad;
    TextField campoCarrera;
    TextField campoUnidades;
    TextField campoColegiatura;

    Label etiquetaNombre;
    Label etiquetaEdad;
    Label etiquetaCarrera;
    Label etiquetaUnidades;
    Label etiquetaColegiatura;

    Dialogo dialogo;
    ////////////////////////


    public Interfaz ()
    {
        Color color;
        /////////////

        setTitle("INTERFAZ --- VIEW de la Aplicacion");
        setBounds(100, 100, 430, 300);
        color = new Color(51,153,255);
        setBackground(color);
        setLayout(null);
        constuyeComponentes();
    }//end constructor


    ///////// Construcción de los componentes de la interfaz //////////////

    public void constuyeComponentes()
    {
        botonAnterior = new Button("Anterior");
        botonOrdenar = new Button("Ordenar");
        botonSalvar = new Button("Salvar");
        botonSiguiente = new Button("Siguiente");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");
        botonAgregar = new Button("Agregar");
        botonModificar = new Button("Modificar");

        botonAnterior.setBounds(20, 260, 80, 20);
        add(botonAnterior);
        botonOrdenar.setBounds(120, 260, 80, 20);
        add(botonOrdenar);
        botonSalvar.setBounds(230, 260, 80, 20);
        add(botonSalvar);
        botonSiguiente.setBounds(330, 260, 80, 20);
        add(botonSiguiente);
        botonEliminar.setBounds(20, 230, 80, 20);
        add(botonEliminar);
        botonLimpiar.setBounds(120, 230, 80, 20);
        add(botonLimpiar);
        botonAgregar.setBounds(330, 230, 80, 20);
        add(botonAgregar);
        botonModificar.setBounds(230, 230, 80, 20);
        add(botonModificar);

        campoNombre = new TextField();
        campoEdad = new TextField();
        campoCarrera = new TextField();
        campoUnidades = new TextField();
        campoColegiatura = new TextField();

        campoNombre.setBounds(200, 50, 200, 20);
        add(campoNombre);
        campoEdad.setBounds(200, 80, 100, 20);
        add(campoEdad);
        campoCarrera.setBounds(200, 110, 200, 20);
        add(campoCarrera);
        campoUnidades.setBounds(200,140, 100, 20);
        add(campoUnidades);
        campoColegiatura.setBounds(200,170, 100, 20);
        add(campoColegiatura);

        etiquetaNombre = new Label("NOMBRE:");
        etiquetaEdad = new Label("EDAD:");
        etiquetaCarrera = new Label("CARRERA:");
        etiquetaUnidades = new Label("UNIDADES:");
        etiquetaColegiatura = new Label("COLEGIATURA:");

        etiquetaNombre.setBounds(100, 50, 200, 20);
        add(etiquetaNombre);
        etiquetaEdad.setBounds(100, 80, 100, 20);
        add(etiquetaEdad);
        etiquetaCarrera.setBounds(100, 110, 200, 20);
        add(etiquetaCarrera);
        etiquetaUnidades.setBounds(100,140, 100, 20);
        add(etiquetaUnidades);
        etiquetaColegiatura.setBounds(100,170, 100, 20);
        add(etiquetaColegiatura);

    }//end constuyeComponentes



    /// Asociación del view con el controller, para responder a los eventos
    public void setActionListener(Controller theController)
    {
        botonAnterior.addActionListener(theController);
        botonOrdenar.addActionListener(theController);
        botonSalvar.addActionListener(theController);
        botonSiguiente.addActionListener(theController);
        botonEliminar.addActionListener(theController);
        botonLimpiar.addActionListener(theController);
        botonAgregar.addActionListener(theController);
        botonModificar.addActionListener(theController);
    }//end setActionListener


    /////// Inicio de el proceso de la Interfaz (aplicación)

    public void inicia()
    {
        setVisible(true);
    }//end inicia

}//end class Interfaz