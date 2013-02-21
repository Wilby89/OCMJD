/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suncertify.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import suncertify.ui.HotelFrame;

/**
 *
 * @author William Brosnan
 */
public class StartUp {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
       JFrame hotelFrame = new HotelFrame();
       hotelFrame.setVisible(true);
    }
    
}
