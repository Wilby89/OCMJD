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
    static final private int HOTEL_NAME_LENGTH = 64;
    static final private int CITY_LENGTH = 64;
    static final private int MAXIMUM_OCCUPANCY_LENGTH = 4;
    static final private int SMOKING_LENGTH = 1;
    static final private int PRICE_LENGTH = 8;
    static final private int DATE_AVAILABLE_LENGTH = 10;
    static final private int CUSTOMER_ID_LENGTH = 8;
    
    /**
     * This constant is used as the maximum size of a full record.
     */
    static final private int MAX_RECORD_LENGTH = HOTEL_NAME_LENGTH + CITY_LENGTH + MAXIMUM_OCCUPANCY_LENGTH + SMOKING_LENGTH
            + PRICE_LENGTH + DATE_AVAILABLE_LENGTH + CUSTOMER_ID_LENGTH;
    
    /**
     * Java variables used as a representation of the data contained in the database file.
     */
    private String hotelName;
    private String city;
    private String maxOccupancy;
    private boolean smoking;
    private String price;
    private String date;
    private int custId;

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
    public boolean getSmoking() {
        return smoking;
    }

    /**
     * @param smoking the smoking to set
     */
    public void setSmoking(boolean smoking) {
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
}
