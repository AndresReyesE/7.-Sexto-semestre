package example.test;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface Inter extends Remote {
    String helloWorld () throws RemoteException;
    String heyDude (String name) throws RemoteException;
    Date getDate () throws RemoteException;
    Offer sendOffer (Offer o) throws RemoteException;
}
