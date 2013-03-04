package suncertify.db;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * This class will be used to take care of low level db access
 * This will keep Data.java and will make use of the
 * OOP concept of delegation through the use of the Delegation pattern
 * 
 * @author William Brosnan
 */
public class DataDBAccess {
    
    private static final int MAGIC_COOKIE_LENGTH = 4;
    private static final int MAGIC_COOKIE_VALUE = 259;
    private static final int NUMBER_OF_FIELDS_LENGTH = 2;
    private static final String ENCODING = "US-ASCII";
    private static final String DATABASE_NAME = "db-1x3.db";
    private static RandomAccessFile fileObject;
    private String[] recordList;
    private String dbLocation;
    
    /**
     * Constant to signify whether a record is valid in the database or if it has been deleted.
     * I chose to use constants since the values of the constant shouldn't change.
     * Could have read these values from the properties file but would have been outside scope/unnecessary work
     */
    private static final byte DELETED = (byte) 0xFF;
    /**
     * Constant to signify whether a record is valid in the database or if it has been deleted.
     * I chose to use constants since the values of the constant shouldn't change.
     * Could have read these values from the properties file but would have been outside scope/unnecessary work
     */
    private static final byte VALID = 00;
    
    
    public DataDBAccess(String dbLocation) {
        this.dbLocation = dbLocation;
    }
    
    public String[] read(int recNo) throws RecordNotFoundException, FileNotFoundException, IOException {
        ArrayList<String> list = new ArrayList();
        fileObject = new RandomAccessFile(new File(dbLocation),"r");
        //byte[] magicCookieArray = new byte[MAGIC_COOKIE_LENGTH];
        //fileObject.read(magicCookieArray); //magicCookie Value
        final int magicCookie = fileObject.readInt();
        final int numOfFields = fileObject.readShort();
        System.out.println("Magic Cookie: "+magicCookie);
        System.out.println("Num of Fields: "+numOfFields); 
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            System.out.println("Name Size: "+nameSize);
            byte[] fieldName = new byte[nameSize];
            fileObject.read(fieldName);
            System.out.println(new String(fieldName, ENCODING)); //The character encoding is 8 bit US ASCII
            fileObject.readByte(); //fieldLength, not needed to display            
        }
        System.out.println("===========Records============");
        try {
            while (true) {
                fileObject.readByte();
                byte[] fieldName = new byte[Room.MAX_RECORD_LENGTH];
                fileObject.read(fieldName);
                String tempRecord = new String(fieldName, ENCODING);
                System.out.println(tempRecord); //The character encoding is 8 bit US ASCII
                String tempName = tempRecord.substring(0,Room.HOTEL_NAME_LENGTH);
                System.out.println(tempName);
                String tempLocation = tempRecord.substring(Room.HOTEL_NAME_LENGTH,Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH);
                System.out.println(tempLocation);
                String tempMax = tempRecord.substring(Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH 
                        ,Room.MAXIMUM_OCCUPANCY_LENGTH+Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH);
                System.out.println(tempMax);
                String tempSmoke = tempRecord.substring(
                        Room.MAXIMUM_OCCUPANCY_LENGTH+Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH
                        , Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH+Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH);
                System.out.println(tempSmoke);
                String tempPrice = tempRecord.substring(
                        Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH+Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH
                        , Room.PRICE_LENGTH+Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH
                        +Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH);
                System.out.println(tempPrice);
                String tempDate = tempRecord.substring(Room.PRICE_LENGTH+Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH
                        +Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH, Room.DATE_AVAILABLE_LENGTH
                        +Room.PRICE_LENGTH+Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH
                        +Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH);
                System.out.println(tempDate);
                String tempCust = tempRecord.substring(Room.DATE_AVAILABLE_LENGTH
                        +Room.PRICE_LENGTH+Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH
                        +Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH, Room.CUSTOMER_ID_LENGTH
                        +Room.DATE_AVAILABLE_LENGTH
                        +Room.PRICE_LENGTH+Room.SMOKING_LENGTH+Room.MAXIMUM_OCCUPANCY_LENGTH
                        +Room.CITY_LENGTH+Room.HOTEL_NAME_LENGTH);
                System.out.println(tempCust);
                int custInt = 1;//Integer.parseInt(tempCust);
                Room newRoom = new Room(tempName, tempLocation, tempMax, tempSmoke
                        , tempPrice, tempDate, custInt);
                list.add(newRoom.toString());
            }
        } catch (EOFException e) {
            System.out.println("End of file");
        }
        fileObject.close();
        recordList = list.toArray(new String[list.size()]);
        return recordList;
    }
    
    public void update(int recNo, String[] data) throws RecordNotFoundException {
        
    }
    
    public void delete(int recNo) throws RecordNotFoundException {
        
    }
    
    public int[] find(String[] criteria) throws RecordNotFoundException {
        
    }
    
    public int create(String[] data) throws RecordNotFoundException {
        
    }
    
    public void lock(int recNo) throws RecordNotFoundException {
        
    }
    
    public void unlock(int recNo) throws RecordNotFoundException {
        
    }
    
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        
    }
}
