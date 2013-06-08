package suncertify.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author William Brosnan
 */
public interface RoomDBRemoteFactory extends Remote {
    
    public RoomDatabaseRemote getClient() throws RemoteException;
}
