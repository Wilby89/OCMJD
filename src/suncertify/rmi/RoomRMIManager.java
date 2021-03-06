package suncertify.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import suncertify.util.PropertyManager;

/**
 * This class is used to start the RMI registry and register the 
 * <code>RoomDBRemoteImpl</code> for the RMI naming service
 * 
 * @author William Brosnan
 */
public class RoomRMIManager {
    
    /**
     * Logger to pass logging messages through
     */
    private static final Logger logger = Logger.getLogger("suncertify.rmi");
    /**
     * The implementation class instance on the server side
     */
    private static RoomDBRemoteFactory roomDBRemoteFactoryImpl;
    /**
     * Enum to know if the server is running
     */
    private enum RunningStatus {
        /**
         * Server started and running
         */
        RUNNING,
        /**
         * Server stopped
         */
        STOPPED
    }
    
    /**
     * Variable to hold the current running status
     */
    private static RunningStatus status = RunningStatus.STOPPED;
    
    /**
     * Empty constructor
     */
    private RoomRMIManager() {}
    
    /**
     * Binds the server instance Data object to the name "rmi://+host+port/RoomBroker
     * identity for the RMI naming service
     */
    public static void start() {
        final PropertyManager propManager = PropertyManager.getInstance();
        final String dbPath = propManager.getProperty("dbPath");
        final String rmiHost = propManager.getProperty("rmiHost");
        final String rmiPort = propManager.getProperty("rmiPort");        
        try {
            roomDBRemoteFactoryImpl = new RoomDBRemoteFactoryImpl(dbPath);
            LocateRegistry.createRegistry(Integer.parseInt(rmiPort));
            Naming.rebind("rmi://" + rmiHost 
                    + ":" + rmiPort + "/RoomBroker", roomDBRemoteFactoryImpl);
            logger.log(Level.INFO, "RMI Server started at " 
                    + rmiHost + ":" + rmiPort);
            status = RunningStatus.RUNNING;
        } catch (RemoteException rex) {
            System.err.println("Remote Exception found trying to start server "
                    + rex.getMessage());
            logger.log(Level.SEVERE, rex.getMessage(), rex);
        } catch (MalformedURLException muex) {
            System.err.println("Invalid URL when trying to start server "
                    + muex.getMessage());
            logger.log(Level.SEVERE, muex.getMessage(), muex);
        }
    }
    
    /**
     * Returns the running state of the server
     * @return a boolean signifying if the server has been started
     */
    public static boolean isServerStarted() {
        if (status.equals(RunningStatus.RUNNING)) {
            return true;
        }
        return false;
    }        
}
