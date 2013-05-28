package suncertify.init;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import suncertify.ui.HotelFrame;
import suncertify.ui.HotelServerFrame;
import suncertify.util.ApplicationMode;

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
        if (args.length == 0 || ApplicationMode.ALONE.name().equalsIgnoreCase(args[0])) {
            JFrame hotelFrame = new HotelFrame(args);
            hotelFrame.setVisible(true);
        }
        else if (ApplicationMode.SERVER.name().equalsIgnoreCase(args[0])) {
            JFrame hotelServerFrame = new HotelServerFrame();
            hotelServerFrame.setVisible(true);
        }
        else {
            logger.log(Level.INFO, "Invalid param supplied to application: " +
                    args[0]);
        }
    }
}
