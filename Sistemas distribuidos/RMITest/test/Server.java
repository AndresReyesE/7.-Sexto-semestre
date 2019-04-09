package example.test;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server implements Inter {

    public Server () {}

    public String helloWorld () {
        return "Hello World!";
    }

    public String heyDude (String name) {
        return "Hey, what's up, " + name + "!?";
    }

    public Date getDate () {
        return new Date();
    }

    public Offer sendOffer (Offer o) {
        o.nickname += "-";
        return o;
    }

    public static void main (String args[]) {
        try {
            Server obj = new Server ();
            Inter stub = (Inter) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Hello", stub);
            registry.bind("Hey", stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.print("Server exception " + e.toString());
            e.printStackTrace();
        }
    }
}

