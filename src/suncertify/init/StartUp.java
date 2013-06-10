package suncertify.init;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import suncertify.ui.HotelFrame;
import suncertify.ui.HotelServerFrame;
import suncertify.util.ApplicationMode;

/**
 * This is the entrypoint of the application. The argument passed in by the user
 * is dealt with in the main method of this class.
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
     * Parses argument passed in by user and starts one of two GUIs depending on arg
     * value.
     * 
     *  ALONE or no argument supplied:
     *      Starts the main <code>HotelFrame</code> GUI
     *  SERVER:
     *      Starts the <code>HotelServerFrame</code> GUI
     * @param args
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
