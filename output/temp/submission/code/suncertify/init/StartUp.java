package suncertify.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JFrame;
import suncertify.db.DatabaseException;
import suncertify.db.RecordNotFoundException;
import suncertify.ui.ConfigurationFrame;
import suncertify.ui.HotelFrame;

/**
 *
 * @author William Brosnan
 */
public class StartUp {
    
    /**
     * Logger instance to pass messages through
     */
    private Logger log = Logger.getLogger("suncertify.init");
    
    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) {
       //JFrame configFrame = new ConfigurationFrame();
       JFrame hotelFrame = new HotelFrame(args);
       hotelFrame.setVisible(true);
    }
    
    public StartUp() {
            
    }
    
}
