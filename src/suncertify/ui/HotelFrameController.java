package suncertify.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import suncertify.db.DBMain;
import suncertify.db.DatabaseException;
import suncertify.db.RecordNotFoundException;
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
        try {
            if (appMode == ApplicationMode.ALONE) {
                connection = RoomDBConnector.getLocalConnection(dbLocation);
            }
            else {
                //connection = RoomConnector.getRemoteConnection(dbLocation, port);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Caught IOException: " + ex.getMessage(), ex);
            System.err.println("Caught IOException: " + ex.getMessage());
        }
    }
    
    public RoomTableModel getAllRooms() {
        RoomTableModel allRoomsModel = new RoomTableModel();
        String[] allRoomsData;
        try {
            allRoomsData = connection.read(5);
            allRoomsModel.addRoomRecord(allRoomsData);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println("Error attempting to get all Rooms: " 
                    + e.getMessage());
        }
        return allRoomsModel;
    }
    
    //public RoomTableModel searchRooms(String hotelName, String location) {
    //    RoomTableModel searchRoomsModel = new RoomTableModel();
    //    String[] searchRoomsData;
    //}
    
}
