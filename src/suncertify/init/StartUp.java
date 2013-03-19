package suncertify.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
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
    private Logger logger = Logger.getLogger("suncertify.init");
    
    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) {
        StartUp startUp = new StartUp(args);             
    }
    
    public StartUp(String[] args) {        
        if (args.length == 0 || "ALONE".equalsIgnoreCase(args[0])) {
            JFrame hotelFrame = new HotelFrame(args);
            hotelFrame.setVisible(true);
        }
        else if ("SERVER".equalsIgnoreCase(args[0])) {
            //JFrame hotelServerFrame = new HotelFrame(args);
            //hotelServerFrame.setVisible(true);
        }
        else {
            logger.log(Level.INFO, "Invalid param supplied to application: " +
                    args[0]);
        }
    }    
}
