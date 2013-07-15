package suncertify.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for factory implementation design pattern.
 * 
 * @author William Brosnan
 */
public interface RoomDBRemoteFactory extends Remote {
    
    /**
     * Returns a remote reference to <code>RoomDatabaseRemote</code> to the 
     * client attempting to connect allowing the client to remotely call methods
     * on the database
     * @return <code>RoomDatabaseRemote</code>
     * @throws RemoteException 
     */
    public RoomDBRemote getClient() throws RemoteException;
}
