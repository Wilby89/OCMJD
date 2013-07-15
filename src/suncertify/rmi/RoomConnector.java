package suncertify.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to allow a client to make an RMI connection
 * 
 * @author William Brosnan
 */
public class RoomConnector {
    
    /**
     * Logger instance to pass messages through
     */
    private static final Logger logger = Logger.getLogger("suncertify.rmi");
    
    /**
     * return RMI connection in the form of a remote object
     * @param hostName
     * @param port
     * @return interface to the database
     * @throws RemoteException 
     */
    public static RoomDBRemote getRemoteConnection(String hostName, String port) 
            throws RemoteException {
        final String url = "rmi://" + hostName + ":" + port + "/RoomBroker";
        logger.log(Level.INFO, "URL is: " + url);
        try {
            RoomDBRemoteFactory factory = (RoomDBRemoteFactory) Naming.lookup(url);
            return (RoomDBRemote) factory.getClient();
        } catch (NotBoundException nbex) {
            System.err.println("No associated binding found for broker at " + url
                    + " " + nbex.getMessage());
            logger.log(Level.SEVERE, nbex.getMessage(), nbex);
            throw new RemoteException("Problem with Room broker connection: ", nbex);
        } catch (MalformedURLException muex) {
            System.err.println("Invalid URL when trying to retrieve connection "
                    + muex.getMessage());
            logger.log(Level.SEVERE, muex.getMessage(), muex);
            throw new RemoteException("Problem with Room connection: ", muex);
        }
    }
    
}
