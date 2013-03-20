package suncertify.db;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
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
    private transient Logger log = Logger.getLogger("suncertify.db");
    
    /**
     * Empty constructor for the room object
     */
    public  Room() {log.info("Entering empty Room constructor");
    }
    
    /**
     * Constructor for the room Object
     */
    public Room(String hotelName, String city, String maxOccupancy
            , String smoking, String price, String date, String custId) {

        log.entering("Room", "Room", new Object[]{hotelName, city, maxOccupancy
            , smoking, price, date, custId});
        this.hotelName = hotelName;
        this.city = city;
        this.maxOccupancy = maxOccupancy;
        this.smoking = smoking;
        this.price = price;
        this.date = date;
        this.custId = custId;
        log.exiting("Room", "Room");
    }

    /**
     * @return the hotelName
     */
    public String getHotelName() {
        log.entering("Room", "getHotelName");
        log.exiting("Room", "getHotelName", this.hotelName);
        return hotelName;
    }

    /**
     * @param hotelName the hotelName to set
     */
    public void setHotelName(String hotelName) {
        log.entering("Room", "setHotelName", hotelName);
        this.hotelName = hotelName;
        log.exiting("Room", "setHotelName", this.hotelName);
    }

    /**
     * @return the city
     */
    public String getCity() {
        log.entering("Room", "getCity");
        log.exiting("Room", "getCity", this.city);
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        log.entering("Room", "setCity", city);
        this.city = city;
        log.entering("Room", "setCity", this.city);
    }

    /**
     * @return the maxOccupancy
     */
    public String getMaxOccupancy() {
        log.entering("Room", "getMaxOccupancy");
        log.exiting("Room", "getMaxOccupancy", this.maxOccupancy);
        return maxOccupancy;
    }

    /**
     * @param maxOccupancy the maxOccupancy to set
     */
    public void setMaxOccupancy(String maxOccupancy) {
        log.entering("Room", "setMaxOccupancy", maxOccupancy);
        this.maxOccupancy = maxOccupancy;
        log.entering("Room", "setMaxOccupancy", this.maxOccupancy);
    }

    /**
     * @return the smoking
     */
    public String getSmoking() {
        log.entering("Room", "getSmoking");
        log.exiting("Room", "getSmoking", this.smoking);
        return smoking;
    }

    /**
     * @param smoking the smoking to set
     */
    public void setSmoking(String smoking) {
        log.entering("Room", "setSmoking", smoking);
        this.smoking = smoking;
        log.entering("Room", "setSmoking", this.smoking);
    }

    /**
     * @return the price
     */
    public String getPrice() {
        log.entering("Room", "getPrice");
        log.exiting("Room", "getPrice", this.price);
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        log.entering("Room", "setPrice", price);
        this.price = price;
        log.entering("Room", "setPrice", this.price);
    }

    /**
     * @return the date
     */
    public String getDate() {
        log.entering("Room", "getDate");
        log.exiting("Room", "getDate", this.date);
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        log.entering("Room", "setDate", date);
        this.date = date;
        log.entering("Room", "setDate", this.date);
    }

    /**
     * @return the custId
     */
    public String getCustId() {
        log.entering("Room", "getCustId");
        log.exiting("Room", "getCustId", this.custId);
        return custId;
    }

    /**
     * @param custId the custId to set
     */
    public void setCustId(String custId) {
        log.entering("Room", "setCustId", custId);
        this.custId = custId;
        log.exiting("Room", "setCustId", this.custId);
    }
    
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
}
