/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import javax.ejb.Remote;

/**
 *
 * @author reyes
 */
@Remote
public interface EntityClassHome extends EJBHome {
    EntityClassRemote create() throws CreateException, RemoteException;
    
    EntityClassRemote findByPrimaryKey(Long primaryKey) throws FinderException, RemoteException;
    
    
}
