package example.test;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

public class Client {
    private Client () {}

    public static void main (String args []) {
        String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Inter stub = (Inter) registry.lookup("Hello");
            String response = stub.helloWorld();
            String response2 = stub.heyDude("Benjamin Bufford Blue Ron Damón Hernández Valgamidades");
            Date dateReceived = stub.getDate();
            
            Offer of = new Offer ("AR", 1, new Date(), 2);
            of = stub.sendOffer(of);

            System.out.println("Response: " + response);
            System.out.println("Response2: " + response2);
            System.out.println("Response3: " + dateReceived);
            System.out.println("Response4: " + of.nickname);
        } catch (Exception e) {
            System.err.print("Exception at client side" + e.toString());
            e.printStackTrace();
        }
    }
}