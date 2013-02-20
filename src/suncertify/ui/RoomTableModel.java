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
    
    private Room room;
    
    public RoomTableModel() {
                    
    }
    
    public void addRoomRecord(Room room) {
        this.room = room;
        
        String tempRoom = {room.getHotelName(), room.getCity(), room.getMaxOccupancy(), Boolean.toString(room.getSmoking())
                , room.getPrice(), room.getDate(), Integer.toString(room.getCustId())};
    }
    
    public Object getValueAt(int row, int column) {
        String[] rowValues = this.roomRecords.get(row);
        return rowValues[column];
    }
    
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    public int getColumnCount() {
        return this.COLUMN_NAMES.length;
    } 
    
    public int getRowCount() {
        return this.roomRecords.size();
    }
}
