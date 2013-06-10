package suncertify.db;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
 * 
 * Object class used as a representation of a Room in the database.
 */
public class Room implements Serializable {

    /**
     * A version number to support serialization and de-serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Maximum length of database field names taken from spec instructions.html
     */
    public static final int HOTEL_NAME_LENGTH = 64;
    public static final int CITY_LENGTH = 64;
    public static final int MAXIMUM_OCCUPANCY_LENGTH = 4;
    public static final int SMOKING_LENGTH = 1;
    public static final int PRICE_LENGTH = 8;
    public static final int DATE_AVAILABLE_LENGTH = 10;
    public static final int CUSTOMER_ID_LENGTH = 8;
    
    /**
     * This constant is used as the maximum size of a full record.
     * It is given default access so all other classes in the package can see the variable
     */
    public static final int MAX_RECORD_LENGTH = HOTEL_NAME_LENGTH + CITY_LENGTH + MAXIMUM_OCCUPANCY_LENGTH + SMOKING_LENGTH
            + PRICE_LENGTH + DATE_AVAILABLE_LENGTH + CUSTOMER_ID_LENGTH;
    
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String hotelName;
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String city;
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String maxOccupancy;
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String smoking;
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String price;
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String date;
    /**
     * Variable used as a representation of the data contained in the database file.
     */
    private String custId;
    /**
     * Logger instance to pass log messages through, this is transient to
     * escape serialization, there is no need to serialize the logger
     */
    private transient Logger logger = Logger.getLogger("suncertify.db");
    
    /**
     * Empty constructor for the room object
     */
    public  Room() {logger.info("Entering empty Room constructor");}
    
    /**
     * Constructor for Room
     * @param hotelName
     * @param city
     * @param maxOccupancy
     * @param smoking
     * @param price
     * @param date
     * @param custId
     */
    public Room(String hotelName, String city, String maxOccupancy
            , String smoking, String price, String date, String custId) {

        logger.entering("Room", "Room", new Object[]{hotelName, city, maxOccupancy
            , smoking, price, date, custId});
        this.hotelName = hotelName;
        this.city = city;
        this.maxOccupancy = maxOccupancy;
        this.smoking = smoking;
        this.price = price;
        this.date = date;
        this.custId = custId;
        logger.exiting("Room", "Room");
    }
    
    /**
     * Constructor for Room
     * @param data 
     */
    public Room(String[] data) {

        logger.entering("Room", "Room", data);
        this.hotelName = data[0];
        this.city = data[1];
        this.maxOccupancy = data[2];
        this.smoking = data[3];
        this.price = data[4];
        this.date = data[5];
        this.custId = data[6];
        logger.exiting("Room", "Room");
    }

    /**
     * Returns the hotel name
     * @return the hotelName
     */
    public String getHotelName() {
        logger.entering("Room", "getHotelName");
        logger.exiting("Room", "getHotelName", this.hotelName);
        return hotelName;
    }

    /**
     * Sets the hotel name
     * @param hotelName the hotelName to set
     */
    public void setHotelName(String hotelName) {
        logger.entering("Room", "setHotelName", hotelName);
        this.hotelName = hotelName;
        logger.exiting("Room", "setHotelName", this.hotelName);
    }

    /**
     * Returns the city
     * @return the city
     */
    public String getCity() {
        logger.entering("Room", "getCity");
        logger.exiting("Room", "getCity", this.city);
        return city;
    }

    /**
     * Sets the city
     * @param city the city to set
     */
    public void setCity(String city) {
        logger.entering("Room", "setCity", city);
        this.city = city;
        logger.entering("Room", "setCity", this.city);
    }

    /**
     * Returns the maximum occupancy
     * @return the maxOccupancy
     */
    public String getMaxOccupancy() {
        logger.entering("Room", "getMaxOccupancy");
        logger.exiting("Room", "getMaxOccupancy", this.maxOccupancy);
        return maxOccupancy;
    }

    /**
     * Sets the maximum occupancy
     * @param maxOccupancy the maxOccupancy to set
     */
    public void setMaxOccupancy(String maxOccupancy) {
        logger.entering("Room", "setMaxOccupancy", maxOccupancy);
        this.maxOccupancy = maxOccupancy;
        logger.entering("Room", "setMaxOccupancy", this.maxOccupancy);
    }

    /**
     * Returns the smoking value
     * @return the smoking
     */
    public String getSmoking() {
        logger.entering("Room", "getSmoking");
        logger.exiting("Room", "getSmoking", this.smoking);
        return smoking;
    }

    /**
     * Sets the smoking value
     * @param smoking the smoking to set
     */
    public void setSmoking(String smoking) {
        logger.entering("Room", "setSmoking", smoking);
        this.smoking = smoking;
        logger.entering("Room", "setSmoking", this.smoking);
    }

    /**
     * Returns the price
     * @return the price
     */
    public String getPrice() {
        logger.entering("Room", "getPrice");
        logger.exiting("Room", "getPrice", this.price);
        return price;
    }

    /**
     * Sets the price
     * @param price the price to set
     */
    public void setPrice(String price) {
        logger.entering("Room", "setPrice", price);
        this.price = price;
        logger.entering("Room", "setPrice", this.price);
    }

    /**
     * Returns the date
     * @return the date
     */
    public String getDate() {
        logger.entering("Room", "getDate");
        logger.exiting("Room", "getDate", this.date);
        return date;
    }

    /**
     * Sets the date
     * @param date the date to set
     */
    public void setDate(String date) {
        logger.entering("Room", "setDate", date);
        this.date = date;
        logger.entering("Room", "setDate", this.date);
    }

    /**
     * Returns the customer id
     * @return the custId
     */
    public String getCustId() {
        logger.entering("Room", "getCustId");
        logger.exiting("Room", "getCustId", this.custId);
        return custId;
    }

    /**
     * Sets the customer id
     * @param custId the custId to set
     */
    public void setCustId(String custId) {
        logger.entering("Room", "setCustId", custId);
        this.custId = custId;
        logger.exiting("Room", "setCustId", this.custId);
    }
    
    /**
     * Returns a String representation of the object
     * @return a <code>String</code> containing the record data
     */
    public String toString() {
        String roomString =
                this.hotelName +
                this.city +
                this.maxOccupancy +
                this.smoking +
                this.price +
                this.date +
                this.custId;
        
        return roomString;
    }
    
    /**
     * returns a String[] representation of the object
     * @return a <code>String[]</code> containing the record data
     */
    public String[] toStringArray() {
        final String[] roomStrArray = {this.hotelName, this.city, 
            this.maxOccupancy, this.smoking, this.price, this.date, this.custId};
        return roomStrArray;
    }
}
