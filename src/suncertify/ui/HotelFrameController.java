package suncertify.ui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import suncertify.db.DBMain;
import suncertify.db.RecordNotFoundException;
import suncertify.db.Room;
import suncertify.rmi.RoomConnector;
import suncertify.rmi.RoomDBRemote;
import suncertify.util.ApplicationMode;
import suncertify.util.RoomDBConnector;

/**
 *
 * @author William Brosnan
 * 
 * Controller for the <code>HotelFrame</code> GUI class, used as a broker between
 * the GUI and the database code.
 */
public class HotelFrameController {
    
    /**
     * Create instance of Data using the DBMain Interface for local connections
     */
    private DBMain localConnection;
    /**
     * Create instance of Data using the DBMain Interface for remote connections
     */
    private RoomDBRemote remoteConnection;
    /*
     * Adding a logger instance for logging and debugging purposes.
     */
    private Logger logger = Logger.getLogger("suncertify.ui");
    /**
     * Application mode 
     */
    ApplicationMode appMode;
    
    /**
     * Constructor which creates the connection to the database. Connection can
     * be one of two types: 
     *  LocalConnection:
     *      Used for stand alone mode and access the database directly.
     *  RemoteConnection:
     *      Establishes a remote connection over RMI.
     * @param appMode
     * @param dbLocation
     * @param port 
     */
    public HotelFrameController(ApplicationMode appMode, String dbLocation, String port) {
        this.appMode = appMode;
        try {
            if (appMode == ApplicationMode.ALONE ) {
                localConnection = RoomDBConnector.getLocalConnection(dbLocation);
            }
            else {
                remoteConnection = RoomConnector.getRemoteConnection(dbLocation, port);
            }
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "Unable to connect: " + ioex.getMessage(), ioex);                 
            throw new RuntimeException (ioex);
        }
    }
    
    /**
     * Retrieves all rooms from the database, this method uses the 
     * getRoomsByCriteria method by passing in two null criteria.
     * @return the <code>RoomTableModel</code> holding all records
     */
    public RoomTableModel getAllRooms() {
        return getRoomsByCriteria(null, null);      
    }
    
    /**
     * Retrieves a specific record or records depending on criteria passed into
     * the method.
     * @param hotelName
     * @param location
     * @return a <code>RoomTableModel</code> containing the desired records
     */
    public RoomTableModel getRoomsByCriteria(String hotelName, String location) {
        RoomTableModel criteriaRoomsModel = new RoomTableModel();
        String[] criteriaRoomsData = {hotelName, location};
        int[] recordNumbers;
        try {    
            if (appMode == ApplicationMode.ALONE ) {
                recordNumbers = localConnection.find(criteriaRoomsData);
                for (int recordNum:recordNumbers) {
                    String[] tempRoomData = localConnection.read(recordNum);
                    criteriaRoomsModel.addRoomRecord(tempRoomData);
                }
            }
            else {
                recordNumbers = remoteConnection.find(criteriaRoomsData);
                for (int recordNum:recordNumbers) {
                    String[] tempRoomData = remoteConnection.read(recordNum);
                    criteriaRoomsModel.addRoomRecord(tempRoomData);
                }
            }            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println("Error finding records with criteria : " 
                    + e.getMessage());
        }
        return criteriaRoomsModel;
    }
    
    /**
     * Reserve a room in the database
     * I chose to use a conversion method in my room object to get CSR, set CSR 
     * and then return a String[] containing to existing record with a new CSR
     * @param recordNumber
     * @param CSRNumber 
     */
    public void reserveRoom(int recordNumber, String CSRNumber) {
        logger.entering("HotelFrameController", "reserveRoom",
                "Record number to be reserved: " + recordNumber);
        String[] data = null;
        try {
            if (appMode == ApplicationMode.ALONE ) {
                data = localConnection.read(recordNumber);
                if (data[6] != null && data[6].trim().length() > 0) {
                    JOptionPane.showMessageDialog(null, "You cannot reserve a room "
                            + "that has been reserved by someone else ");
                    return;
                }
            }
            else {                
                    data = remoteConnection.read(recordNumber);   
                    if (data[6] != null && data[6].trim().length() > 0) {
                    JOptionPane.showMessageDialog(null, "You cannot reserve a room "
                            + "that has been reserved by someone else ");
                    return;
                }
            }
        } catch (RemoteException rex) {
            logger.log(Level.SEVERE, rex.getMessage(), rex);
            System.err.println("Problem with remote connection : " 
                    + rex.getMessage());
        } catch (RecordNotFoundException rnfex) {
            logger.log(Level.SEVERE, rnfex.getMessage(), rnfex);
            System.err.println("Error reading record at position : " 
                    + rnfex.getMessage());
        }
        Room room = new Room(data);
        room.setCustId(CSRNumber);
        try {
            if (appMode == ApplicationMode.ALONE ) {
                localConnection.lock(recordNumber);
                localConnection.update(recordNumber, room.toStringArray());
                localConnection.unlock(recordNumber);
            }
            else {
                remoteConnection.lock(recordNumber);
                remoteConnection.update(recordNumber, room.toStringArray());
                remoteConnection.unlock(recordNumber);
            }
        } catch (RemoteException rex) {
            logger.log(Level.SEVERE, rex.getMessage(), rex);
            System.err.println("Problem with remote connection : " 
                    + rex.getMessage());
        } catch (RecordNotFoundException rnfex) {
            logger.log(Level.SEVERE, rnfex.getMessage(), rnfex);
            System.err.println("Error attempting to update : " 
                    + rnfex.getMessage());
        }
        logger.exiting("HotelFrameController", "reserveRoom");
    }    
}