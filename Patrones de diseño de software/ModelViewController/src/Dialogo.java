/**
 * Created by gerardoayala on 1/21/16.
 */

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Dialogo extends Dialog implements ActionListener
{
    Button botonSi;
    Button botonNo;
    String mensaje;
    Label etiquetaDelMensaje;
    Color color;
    boolean hacerAccion;
    ////////////////////


    public Dialogo(Interfaz view, String unMensaje)
    {
        super(view);
        setModal(true);
        mensaje = unMensaje;
        hacerAccion = false;

        setBounds(200, 300, 300, 150);
        color = new Color(255,255,200);
        setBackground(color);
        setLayout(null);

        construyeComponentes();
        setVisible(true);
    }//end constructor



    private void construyeComponentes()
    {
        etiquetaDelMensaje = new Label(mensaje);
        etiquetaDelMensaje.setBounds(20, 60, 360, 20);
        add(etiquetaDelMensaje);

        botonSi = new Button("Sí");
        botonNo = new Button("No");

        botonNo.setBounds(20, 110, 80, 20);
        add(botonNo);

        botonSi.setBounds(200, 110, 80, 20);
        add(botonSi);

        botonSi.addActionListener(this);
        botonNo.addActionListener(this);

    }//end construyeComponentes



    public void actionPerformed(ActionEvent evento)
    {
        Button botonAccionado;
        ///////////////////////

        botonAccionado = (Button) evento.getSource();

        if(botonAccionado == botonSi)
        {
            setVisible(false);
            hacerAccion = true;
        }//end if

        if(botonAccionado == botonNo)
        {
            setVisible(false);
            hacerAccion = false;
        }//end if
    }//end actionPerformed



    public boolean seAceptaAccion()
    {
        return hacerAccion;
    }//end


}//end class Dialogo
