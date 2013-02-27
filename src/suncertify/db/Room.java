package suncertify.db;

import java.io.Serializable;

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
    static final int HOTEL_NAME_LENGTH = 64;
    static final int CITY_LENGTH = 64;
    static final int MAXIMUM_OCCUPANCY_LENGTH = 4;
    static final int SMOKING_LENGTH = 1;
    static final int PRICE_LENGTH = 8;
    static final int DATE_AVAILABLE_LENGTH = 10;
    static final int CUSTOMER_ID_LENGTH = 8;
    
    /**
     * This constant is used as the maximum size of a full record.
     * It is given default access so all other classes in the package can see the variable
     */
    static final int MAX_RECORD_LENGTH = HOTEL_NAME_LENGTH + CITY_LENGTH + MAXIMUM_OCCUPANCY_LENGTH + SMOKING_LENGTH
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
    private int custId;
    
    /**
     * Empty constructor for the room object
     */
    public  Room() {}
    
    /**
     * Constructor for the room Object
     */
    public Room(String hotelName, String city, String maxOccupancy
            , String smoking, String price, String date, int custId) {
        
        this.hotelName = hotelName;
        this.city = city;
        this.maxOccupancy = maxOccupancy;
        this.smoking = smoking;
        this.price = price;
        this.date = date;
        this.custId = custId;
    }

    /**
     * @return the hotelName
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * @param hotelName the hotelName to set
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the maxOccupancy
     */
    public String getMaxOccupancy() {
        return maxOccupancy;
    }

    /**
     * @param maxOccupancy the maxOccupancy to set
     */
    public void setMaxOccupancy(String maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    /**
     * @return the smoking
     */
    public String getSmoking() {
        return smoking;
    }

    /**
     * @param smoking the smoking to set
     */
    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the custId
     */
    public int getCustId() {
        return custId;
    }

    /**
     * @param custId the custId to set
     */
    public void setCustId(int custId) {
        this.custId = custId;
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
