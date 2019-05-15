/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBean;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import javax.ejb.Remote;

/**
 *
 * @author reyes
 */
@Remote
public interface EntityClassRemote extends EJBObject {
    String doSomething() throws RemoteException;
}
