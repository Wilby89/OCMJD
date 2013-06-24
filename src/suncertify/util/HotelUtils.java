package suncertify.util;

/**
 * Utility class for exposing access to common methods needed by the 
 * entire code base.
 *
 * @author William Brosnan
 */
public class HotelUtils {
    
    /**
     * Helper method to verify if the port number entered is a recognized number
     * @param possibleNumeric - The contents of the portField <code>JTextField</code>
     * entered by the user.
     * @return a boolean signifying if value is a valid number
     */
    public static boolean isNumeric(String possibleNumeric) {
        try {
            double dub = Double.parseDouble(possibleNumeric);
        }
        catch(NumberFormatException nfex) {
            return false;
        }
        return true;
    }
}
