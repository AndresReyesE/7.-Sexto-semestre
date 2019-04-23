package example.test;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.ArrayList;

public class Server {

    public Server () {}

//    Servant servant = new Servant();

    public static void main (String args[]) {
        try {
            Servant obj = new Servant ();
            Inter stub = (Inter) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Hello", stub);
            registry.bind("Hey", stub);

            ArrayList <ClientInterface> subscribedClients;
            


            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.print("Server exception " + e.toString());
            e.printStackTrace();
        }
    }
}

