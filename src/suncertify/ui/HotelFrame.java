package suncertify.ui;

import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author William Brosnan
 */
public class HotelFrame extends JFrame{
    
    private JTable hotelTable;
    
    public HotelFrame() {
        setTitle("URLyBird Hotel User Interface");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        //setVisible(true);
        hotelTable = loadTable();
        hotelTable.setVisible(true);
        add(hotelTable);
    }
    
    private JTable loadTable() {
        JTable table = new HotelTable();
        return table;
    }
}
