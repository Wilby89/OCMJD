package suncertify.ui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author William Brosnan
 */
public class RoomTableModel extends AbstractTableModel {
    
    private static final long serialVersionUID = 1L;
    
    private String[] COLUMN_NAMES = {"Hotel Name","City","Max Occupants","Smoking","Price","Date Available","Customer ID"};
    
    private ArrayList<String[]> roomRecords = new ArrayList<String[]>();
    
    public RoomTableModel() {
                    
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
