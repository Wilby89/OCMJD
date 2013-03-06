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
    private static final int FIELD_NAME_LENGTH = 1;
    private static final int ACTUAL_FIELD_LENGTH = 1;
    private static final int RECORD_DELETION_STATUS_LENGTH = 1;
    private static final String ENCODING = "US-ASCII";
    private static final String DATABASE_NAME = "db-1x3.db";
    private final RandomAccessFile fileObject;
    private String[] recordList;
    private String dbLocation;
    private int offset;
    
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
    
    
    public DataDBAccess(String dbLocation) throws FileNotFoundException, IOException, DatabaseException {
        fileObject = new RandomAccessFile(new File(dbLocation),"rw");
        final int magicCookie = fileObject.readInt();
        if (magicCookie != MAGIC_COOKIE_VALUE) {
            throw new DatabaseException("Magic cookie values do not match");
        }
        offset = MAGIC_COOKIE_LENGTH + NUMBER_OF_FIELDS_LENGTH + Room.MAX_RECORD_LENGTH;
        final int numOfFields = fileObject.readShort();
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            System.out.println("Name Size: "+nameSize);
            byte[] fieldName = new byte[nameSize];
            fileObject.read(fieldName);
            System.out.println(new String(fieldName, ENCODING)); //The character encoding is 8 bit US ASCII   
            offset += FIELD_NAME_LENGTH + ACTUAL_FIELD_LENGTH + nameSize;
        }
    }
    
    public String[] read(int recNo) throws RecordNotFoundException, FileNotFoundException, IOException {
        ArrayList<String> list = new ArrayList(); 
        try {
            fileObject.seek(offset + recNo + Room.MAX_RECORD_LENGTH + RECORD_DELETION_STATUS_LENGTH);
            byte[] record = new byte[Room.MAX_RECORD_LENGTH];
            int tempRecordLength = fileObject.read(record);
            if (tempRecordLength != Room.MAX_RECORD_LENGTH) {
                throw new RecordNotFoundException("Record not found or record size does not match");
            }
            if (record[0] == DELETED) {
                throw new RecordNotFoundException("Record has been deleted");
            }
            return parseRecord(new String(record, ENCODING));
            
        } catch (EOFException e) {
            System.out.println("End of file");
        }
        fileObject.close();
        recordList = list.toArray(new String[list.size()]);
        return recordList;
    }
    
    private String[] parseRecord(String record) {
        while (true) {
            String tempRecord = new String(record);
            System.out.println(tempRecord); //The character encoding is 8 bit US ASCII
            String tempName = tempRecord.substring(0, Room.HOTEL_NAME_LENGTH);
            System.out.println(tempName);
            String tempLocation = tempRecord.substring(Room.HOTEL_NAME_LENGTH, Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH);
            System.out.println(tempLocation);
            String tempMax = tempRecord.substring(Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH, Room.MAXIMUM_OCCUPANCY_LENGTH + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH);
            System.out.println(tempMax);
            String tempSmoke = tempRecord.substring(
                    Room.MAXIMUM_OCCUPANCY_LENGTH + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH, Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH);
            System.out.println(tempSmoke);
            String tempPrice = tempRecord.substring(
                    Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH, Room.PRICE_LENGTH + Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH
                    + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH);
            System.out.println(tempPrice);
            String tempDate = tempRecord.substring(Room.PRICE_LENGTH + Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH
                    + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH, Room.DATE_AVAILABLE_LENGTH
                    + Room.PRICE_LENGTH + Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH
                    + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH);
            System.out.println(tempDate);
            String tempCust = tempRecord.substring(Room.DATE_AVAILABLE_LENGTH
                    + Room.PRICE_LENGTH + Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH
                    + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH, Room.CUSTOMER_ID_LENGTH
                    + Room.DATE_AVAILABLE_LENGTH
                    + Room.PRICE_LENGTH + Room.SMOKING_LENGTH + Room.MAXIMUM_OCCUPANCY_LENGTH
                    + Room.CITY_LENGTH + Room.HOTEL_NAME_LENGTH);
            System.out.println(tempCust);
            int custInt = 1;//Integer.parseInt(tempCust); 
        }
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
