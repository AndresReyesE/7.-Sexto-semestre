package example.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    String getID () throws RemoteException;

    int getTest () throws RemoteException;

    void setTest (int news) throws RemoteException;

    boolean update (String news) throws RemoteException;
}