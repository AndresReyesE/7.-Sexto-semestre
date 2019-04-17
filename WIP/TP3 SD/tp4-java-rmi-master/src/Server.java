import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Server extends Remote {
  public boolean registraUsuario(String nombre) throws RemoteException;

  public boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) throws RemoteException;

  public boolean agregaOferta(String comprador, String producto, float monto) throws RemoteException;

  public Vector obtieneCatalogo() throws RemoteException;

}
