package suncertify.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Separate class for ActionListener that can be
 * used by both HotelFrame main screen and ConfigurationFrame 
 * GUI.
 * Contains the code used to exit the application.
 * 
 * @author William Brosnan
 */
public class QuitApplication implements ActionListener {
        /**
         * Quits the application when invoked.
         *
         * @param ae The event triggering the quit operation.
         */
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
}
