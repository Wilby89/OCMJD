package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import suncertify.util.PropertyManager;

/**
 *
 * @author William Brosnan
 */
public class RoomRMIRegister {
    
    private static final Logger logger = Logger.getLogger("suncertify.rmi");
    
    private RoomRMIRegister() {}
    
    /**
     * Used to register the 
     */
    public static void register() {
        final PropertyManager propManager = PropertyManager.getInstance();
        final String port = propManager.getProperty("rmiport");
        try {
            LocateRegistry.createRegistry(Integer.parseInt(port));
        } catch (RemoteException rex) {
            logger.log(Level.SEVERE, rex.getMessage(), rex);
        }
    }
}
