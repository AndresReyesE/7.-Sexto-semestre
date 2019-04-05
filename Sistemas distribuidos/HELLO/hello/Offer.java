package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable {
    String nickname;
    int IDProduct;
    Date date;
    double amount;

    public Offer (String nick, int id, Date d, double cant) {
        nickname = nick;
        IDProduct = id;
        date = d;
        amount = cant;
    }

}
