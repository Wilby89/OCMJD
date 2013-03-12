package suncertify.ui;

import java.util.logging.Logger;
import suncertify.db.DBMain;
import suncertify.rmi.RoomConnector;
import suncertify.util.ApplicationMode;
import suncertify.util.RoomDBConnector;

/**
 *
 * @author William Brosnan
 */
public class HotelFrameController {
    
    /**
     * Create instance of Data using the DBMain Interface
     */
    private DBMain connection;
    /*
     * Adding a logger instance for logging and debugging purposes.
     */
    private Logger logger = Logger.getLogger("suncertify.ui");
    
    public HotelFrameController(ApplicationMode appMode, String dbLocation, String port) {
        if (appMode == ApplicationMode.ALONE) {
            connection = RoomDBConnector.getLocalConnection(dbLocation);
        }
        else {
            connection = RoomConnector.getRemoteConnection(dbLocation, port);
        }
    }
    
    public RoomTableModel getAllRooms() {
        RoomTableModel allRoomsModel = new RoomTableModel();
        String[] allRoomsData;
        //allRoomsData = connection.
    }
    
    public RoomTableModel searchRooms(String hotelName, String location) {
        RoomTableModel searchRoomsModel = new RoomTableModel();
        String[] searchRoomsData;
    }
    
}
