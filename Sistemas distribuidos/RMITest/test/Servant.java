package example.test;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

public class Servant implements Inter {

    private ArrayList <ClientInterface> subscribedClients = new ArrayList<>();

    public Servant () {}

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

    public void suscribe (ClientInterface newClient) {
        int index = 0;
        try {
            System.out.println("Subscribing a new client with id: " + newClient.getID());
            subscribedClients.add(newClient);
            for (ClientInterface c : subscribedClients) {
                c.update(c.getID());
                c.setTest(20);
                index++;
            }
        }
        catch (RemoteException re) {
            subscribedClients.remove(index);
            System.out.println("Client " + index + " eliminated");
        }

    }
}