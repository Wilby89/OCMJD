package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/**
 * Implementation class of the <code>RoomDBRemoteFactory</code> interface
 * 
 * @author William Brosnan
 */
public class RoomDBRemoteFactoryImpl extends UnicastRemoteObject implements RoomDBRemoteFactory {
    
    /**
     * A version number to support serialization and de-serialization.
     */
    private static final long serialVersionUID = 1L;
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
     * Returns a remote reference to <code>RoomDatabaseRemote</code> to the 
     * client attempting to connect allowing the client to remotely call methods
     * on the database
     * @return <code>RoomDatabaseRemote</code>
     * @throws RemoteException 
     */
    @Override
    public RoomDBRemote getClient() throws RemoteException {
        return new RoomDBRemoteImpl(dbPath);
    }
}
