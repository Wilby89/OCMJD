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
    
    public RoomTableModel getRoom(int recordNumber) {
        RoomTableModel roomModel = new RoomTableModel();
        String[] roomData;
        try {
            roomData = connection.read(5);
            roomModel.addRoomRecord(roomData);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println("Error attempting to get all Rooms: " 
                    + e.getMessage());
        }
        return roomModel;
    }
    
    /**
     * I chose to make use of the getRoomsByCriteria function to get all records
     * By sending in null values as search criteria it will return all valid records
     * in the database
     * @return the RoomTableModel holding all records
     */
    public RoomTableModel getAllRooms() {
        return getRoomsByCriteria(null, null);      
    }
    
    public RoomTableModel getRoomsByCriteria(String hotelName, String location) {
        RoomTableModel criteriaRoomsModel = new RoomTableModel();
        String[] criteriaRoomsData = {hotelName, location};
        int[] recordNumbers;
        try {
            recordNumbers = connection.find(criteriaRoomsData);
            for (int recordNum:recordNumbers) {
                String[] tempRoomData = connection.read(recordNum);
                criteriaRoomsModel.addRoomRecord(tempRoomData);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println("Error finding records with criteria : " 
                    + e.getMessage());
        }
        return criteriaRoomsModel;
    }
    
}
