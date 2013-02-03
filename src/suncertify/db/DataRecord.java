package suncertify.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 *
 * @author William Brosnan
 */
public class DataRecord implements Serializable {
    
    private String hotelName;
    private String city;
    private short maxOccupancy;
    private char smoking;
    private String price;
    private SimpleDateFormat date;
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
    public SimpleDateFormat getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(SimpleDateFormat date) {
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
