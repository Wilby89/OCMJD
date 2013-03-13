package suncertify.ui;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import suncertify.db.Room;
/**
 *
 * @author William Brosnan
 */
public class RoomTableModel extends AbstractTableModel {
    
    /**
     * variable to allow serialization and de-serialization
     */
    private static final long serialVersionUID = 1L;    
    /**
     * String array to hold the column names of the JTable that will represent fields in the database
     */
    private String[] COLUMN_NAMES = {"Hotel Name", "City", "Max Occupants", "Smoking", 
        "Price", "Date Available", "Customer ID"};    
    /**
     * ArrayList holding the records for each room, a room record is held as a String array.
     */
    private ArrayList<String[]> roomRecords = new ArrayList<String[]>();
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.ui");
    
    /**
     * This addRoomRecord method takes a Room object as a parameter and de-constructs it into
     * object variables which are passed to the other addRoomRecord method
     * It should only be called from the other addRoomRecord method in this class
     * @param room A room Object
     */
    //public void addRoomRecord(Room room) {
    //    addRoomRecord(room.getHotelName(), room.getCity(), room.getMaxOccupancy(), room.getSmoking()
    //    , room.getPrice(), room.getDate(), room.getCustId());
    //}

    /**
     * This method is used to add a room record into the roomRecords arrayList to populate the JTable.
     * It should only be called from the other addRoomRecord method in this class
     * @param hotelName
     * @param city
     * @param maxOccupancy
     * @param smoking
     * @param price
     * @param date
     * @param custId 
     */
    public void addRoomRecord(String[] roomArray) {              
        this.roomRecords.add(roomArray);
    }
    
    /**
     * Overriding getValueAt method from AbstractTableModel
     * @param row
     * @param column
     * @return Object in specified row and column
     */
    @Override
    public Object getValueAt(int row, int column) {
        //Initialze string array from roomRecords ArrayList
        String[] rowValues = this.roomRecords.get(row);
        //Return the object from the specified column 
        return rowValues[column];
    }
    
    /**
     * Overriding setValueAt method from AbstractTablemodel
     * Sets the value at specified row and column
     * @param obj
     * @param row
     * @param column 
     */
    @Override
    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.roomRecords.get(row);
        rowValues[column] = obj; 
    }
    
    /**
     * Overriding getColumnName from AbstractTableModel
     * @param column
     * @return Name of Column 
     */
    @Override
    public String getColumnName(int column) {
        //Returns column name from  hard-coded String array of names
        return COLUMN_NAMES[column];
    }
    
    /**
     * Overriding getColumnCount from AbstractTableModel
     * @return number of columns in table model
     */
    @Override
    public int getColumnCount() {
        return this.COLUMN_NAMES.length;
    } 
    
    /**
     * Overriding getRowCount from AbstractTableModel
     * @return number of rows in table model
     */
    @Override
    public int getRowCount() {
        return this.roomRecords.size();
    }
    
    /**
     * Specifies if a given cell can be edited
     * @param row
     * @param column
     * @return a boolean that specifies if cell is editable
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
