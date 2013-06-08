package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
 */
public class RoomDBRemoteFactoryImpl extends UnicastRemoteObject implements RoomDBRemoteFactory {
    
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.rmi");
    /**
     * Blank placeholder for the database location
     */
    private static String dbPath = null;
    
    /**
     * Constructor creates instance of the factory and sets the database location
     * @param dbPath
     * @throws RemoteException 
     */
    public RoomDBRemoteFactoryImpl(String dbPath) throws RemoteException{
        this.dbPath = dbPath;
    } 

    /**
     * returns a client to the 
     * @return a new <code>RoomDatabaseRemoteImpl</code>
     * @throws RemoteException 
     */
    @Override
    public RoomDatabaseRemote getClient() throws RemoteException {
        return new RoomDBRemoteImpl(dbPath);
    }
}
