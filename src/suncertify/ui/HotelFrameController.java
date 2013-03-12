package suncertify.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    
    public HotelFrameController(ApplicationMode appMode, String dbLocation, String port) throws FileNotFoundException, IOException, DatabaseException {
        if (appMode == ApplicationMode.ALONE) {
            connection = RoomDBConnector.getLocalConnection(dbLocation);
        }
        else {
            //connection = RoomConnector.getRemoteConnection(dbLocation, port);
        }
    }
    
    public RoomTableModel getAllRooms() throws RecordNotFoundException {
        RoomTableModel allRoomsModel = new RoomTableModel();
        String[] allRoomsData;
        allRoomsData = connection.read(0);
        allRoomsModel.addRoomRecord(allRoomsData);
        return allRoomsModel;
    }
    
    //public RoomTableModel searchRooms(String hotelName, String location) {
    //    RoomTableModel searchRoomsModel = new RoomTableModel();
    //    String[] searchRoomsData;
    //}
    
}
