package suncertify.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import suncertify.util.PropertyManager;

/**
 * @author William Brosnan
 * 
 * This class is used to start the RMI registry and register the 
 * <code>RoomDBRemoteImpl</code> for the RMI naming service
 */
public class RoomRMIStarter {
    
    /**
     * Logger to pass logging messages through
     */
    private static final Logger logger = Logger.getLogger("suncertify.rmi");
    /**
     * The implementation class instance on the server side
     */
    private static RoomDBRemoteImpl roomDBRemoteImpl;
    
    private RoomRMIStarter() {}
    
    /**
     * Binds the server instance Data object to the name "rmi://+host+port+Impl 
     * class for the RMI naming service
     */
    public static void start() {
        final PropertyManager propManager = PropertyManager.getInstance();
        final String hostName = propManager.getProperty("rmiHost");
        final String port = propManager.getProperty("rmiPort");
        final String dbPath = propManager.getProperty("dbPath");
        try {
            roomDBRemoteImpl = new RoomDBRemoteImpl(dbPath);
            LocateRegistry.createRegistry(Integer.parseInt(port));
            Naming.rebind("rmi://"+hostName+":"+port+"/"+"RoomDBRemoteImpl", roomDBRemoteImpl);
            logger.log(Level.INFO, "RMI Server started at " 
                    + hostName + ":" + port);
        } catch (RemoteException rex) {
            System.err.println("Remote Exception found trying to start server"
                    + rex.getMessage());
            logger.log(Level.SEVERE, rex.getMessage(), rex);
        } catch (MalformedURLException muex) {
            System.err.println("Invalid URL when trying to start server"
                    + muex.getMessage());
            logger.log(Level.SEVERE, muex.getMessage(), muex);
        }
    }
}
