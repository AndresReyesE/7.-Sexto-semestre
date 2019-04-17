import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Principal {
  public static void main( String args[] ) {
    /*
    SubastaVista vista;
    SubastaControlador controlador;
    SubastaModelo modelo;



    vista = new SubastaVista();
    modelo = new SubastaModelo();
    controlador = new SubastaControlador( vista, modelo );

    vista.asignarActionListener( controlador );
    vista.asignarListSelectionListener( controlador );
    */
    String host = (args.length < 1) ? null : args[0];
    try {
      SubastaVista view = new SubastaVista();


      Registry registry = LocateRegistry.getRegistry(host);
      Server modelStub = (Server) registry.lookup("SubastaServer");

      SubastaControlador controller = new SubastaControlador(view, modelStub);

      view.asignarActionListener(controller);
      view.asignarListSelectionListener(controller);




	  } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	   }
   }
}
