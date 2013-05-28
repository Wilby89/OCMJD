package suncertify.ui;

import javax.swing.JFrame;
import suncertify.rmi.RoomRMIStarter;

/**
 *
 * @author William Brosnan
 */
public class HotelServerFrame extends JFrame {
    
    public HotelServerFrame() {
        setTitle("URLyBird Hotel Server Interface");
        setSize(1000,800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        startServer();
    }
    
    private void startServer() {
        RoomRMIStarter.start();
    }
}
