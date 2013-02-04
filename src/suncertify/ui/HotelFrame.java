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
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        //setVisible(true);
    }
}
