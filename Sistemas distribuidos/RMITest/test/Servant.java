package example.test;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Servant implements Inter {

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
}