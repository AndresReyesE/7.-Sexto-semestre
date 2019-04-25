package example.test;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Client implements ClientInterface, Serializable {
    private String id;
    public int test;

    public Client (String id) {
        this.id = id;
        this.test = 10;
    }

    @Override
    public String getID () {
        return this.id;
    }

    @Override
    public int getTest () {
        return this.getTest();
    }

    @Override
    public void setTest (int news) {
        this.test = news;
    }

    @Override
    public boolean update (String news) {
        System.out.println("Client received: " + news);
        // this.test = news;
        return true;
    }

    public static void main (String args []) {
        // String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry();
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

            Client self = new Client(args[0]);
            ClientInterface selfStub = (ClientInterface) UnicastRemoteObject.exportObject(self, 0);

            System.out.println("Previously: " + self.getTest());
            stub.suscribe(selfStub);
            System.out.println("After: " + self.getTest());
            
        } catch (Exception e) {
            System.err.print("Exception at client side" + e.toString());
            e.printStackTrace();
        }
    }
}