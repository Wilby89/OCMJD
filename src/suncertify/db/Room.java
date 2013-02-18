package suncertify.db;

import java.io.Serializable;

/**
 *
 * @author William Brosnan
 */
public class Room implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //Maximum length of database field names taken from spec
    static final int HOTEL_NAME_LENGTH = 64;
    static final int CITY_LENGTH = 64;
    static final int MAXIMUM_OCCUPANCY_LENGTH = 4;
    static final int SMOKING_LENGTH = 1;
    static final int PRICE_LENGTH = 8;
    static final int DATE_AVAILABLE_LENGTH = 10;
    static final int CUSTOMER_ID_LENGTH = 8;
    
    static final int MAX_RECORD_LENGTH = HOTEL_NAME_LENGTH + CITY_LENGTH + MAXIMUM_OCCUPANCY_LENGTH + SMOKING_LENGTH +
            PRICE_LENGTH + DATE_AVAILABLE_LENGTH + CUSTOMER_ID_LENGTH;
    
    
    private String hotelName;
    private String city;
    private short maxOccupancy;
    private char smoking;
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
    public short getMaxOccupancy() {
        return maxOccupancy;
    }

    /**
     * @param maxOccupancy the maxOccupancy to set
     */
    public void setMaxOccupancy(short maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    /**
     * @return the smoking
     */
    public char getSmoking() {
        return smoking;
    }

    /**
     * @param smoking the smoking to set
     */
    public void setSmoking(char smoking) {
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
