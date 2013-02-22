package suncertify.ui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import suncertify.db.Room;
/**
 *
 * @author William Brosnan
 */
public class RoomTableModel extends AbstractTableModel {
    
    private static final long serialVersionUID = 1L;
    
    private String[] COLUMN_NAMES = {"Hotel Name","City","Max Occupants","Smoking","Price","Date Available","Customer ID"};
    
    private ArrayList<String[]> roomRecords = new ArrayList<String[]>();
    
    public void addRoomRecord(Room room) {
        addRoomRecord(room.getHotelName(), room.getCity(), room.getMaxOccupancy(), room.getSmoking()
        , room.getPrice(), room.getDate(), room.getCustId());
    }
    
    public void addRoomRecord(String hotelName, String city, String maxOccupancy
            , boolean smoking, String price, String date, int custId) {
        
        String[] tempRoom = {hotelName, city, maxOccupancy, Boolean.toString(smoking)
        , price, date, Integer.toString(custId)};
        
        this.roomRecords.add(tempRoom);
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        String[] rowValues = this.roomRecords.get(row);
        return rowValues[column];
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    @Override
    public int getColumnCount() {
        return this.COLUMN_NAMES.length;
    } 
    
    @Override
    public int getRowCount() {
        return this.roomRecords.size();
    }
}
