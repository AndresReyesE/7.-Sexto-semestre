
import java.util.Hashtable;
import java.util.Vector;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class SubastaModelo implements Server {
  Hashtable usuarios;
  Hashtable productos;
  Hashtable ofertas;

  public SubastaModelo() {

    usuarios = new Hashtable();
    productos = new Hashtable();
    ofertas = new Hashtable();
  }

  public synchronized boolean registraUsuario( String nombre ) throws RemoteException{
    if(!usuarios.containsKey(nombre) ) {
      System.out.println( "Agregando un nuevo usuario: " + nombre );
      usuarios.put( nombre, nombre );
      return true;
    } else return false;
  }

  public synchronized boolean agregaProductoALaVenta( String vendedor, String producto, float precioInicial ) throws RemoteException {
    if( !productos.containsKey(producto) ) {

      System.out.println( "Agregando un nuevo producto: " + producto );
      productos.put( producto, new InformacionProducto(vendedor, producto, precioInicial));
      return true;
    } else return false;
  }

  public synchronized boolean agregaOferta( String comprador, String producto, float monto ) throws RemoteException {
        if(productos.containsKey(producto)) {
          InformacionProducto infoProd;
          infoProd = (InformacionProducto) productos.get(producto);

          if( infoProd.actualizaPrecio(monto) ) {
            ofertas.put( producto + comprador, new InformacionOferta(comprador, producto, monto));
            return true;
          } else return false;
        } else return false;
  }

  public Vector obtieneCatalogo() throws RemoteException {
	   Vector resultado;
	   resultado = new Vector(productos.values());
     return resultado;
  }

  public static void main(String[] args) {
    try {
      SubastaModelo modelo = new SubastaModelo();
      Server stub = (Server) UnicastRemoteObject.exportObject(modelo, 0);
      Registry registry = LocateRegistry.getRegistry();
      registry.bind("SubastaServer", stub);
      System.out.println("Servidor de subasta registrado en servidor de nombres.");
    }
    catch (Exception ex) {
      StringWriter outError = new StringWriter();
      ex.printStackTrace(new PrintWriter(outError));
      String errorString = outError.toString();
      System.out.println(errorString);
    }

  }
}
