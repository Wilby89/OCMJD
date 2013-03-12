package suncertify.db;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class will be used to take care of low level db access
 * This will keep Data.java and will make use of the
 * OOP concept of delegation through the use of the Delegation pattern
 * 
 * @author William Brosnan
 */
public class DataDBAccess {
    
    public static final int MAGIC_COOKIE_LENGTH = 4;
    public static final int MAGIC_COOKIE_VALUE = 259;
    public static final int NUMBER_OF_FIELDS_LENGTH = 2;
    public static final int FIELD_NAME_LENGTH = 1;
    public static final int ACTUAL_FIELD_LENGTH = 1;
    public static final int RECORD_DELETION_STATUS_LENGTH = 1;
    public static final String ENCODING = "US-ASCII";
    public static final String DATABASE_NAME = "db-1x3.db";
    private final RandomAccessFile fileObject;
    private Logger log = Logger.getLogger("suncertify.db");
    private int numOfFields;
    private String[] fieldColumnNames;
    private String[] recordList;
    private String dbLocation;
    private HashMap<String, Integer> fieldMap = null;
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
        offset = MAGIC_COOKIE_LENGTH + NUMBER_OF_FIELDS_LENGTH;
        System.out.println("Offset: " + offset);
        numOfFields = fileObject.readShort();
        fieldColumnNames = new String[numOfFields];
        fieldMap = new HashMap<String, Integer>();
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            System.out.println("Name Size: " + nameSize);
            byte[] fieldName = new byte[nameSize];
            fileObject.read(fieldName);
            fieldColumnNames[i] = new String(fieldName, ENCODING);
            int size = fileObject.readByte();
            //System.out.println("Size: " + size);
            fieldMap.put(fieldColumnNames[i], size);
            //System.out.println(new String(fieldName, ENCODING)); //The character encoding is 8 bit US ASCII   
            offset += FIELD_NAME_LENGTH + ACTUAL_FIELD_LENGTH + nameSize;
            System.out.println("Offset: " + offset);
        }
    }
    
    public String[] read(int recNo) throws RecordNotFoundException {
        ArrayList<String> list = new ArrayList(); 
        try {
            System.out.println("Offset going into read: " + (offset + recNo * Room.MAX_RECORD_LENGTH));
            fileObject.seek(offset + recNo * Room.MAX_RECORD_LENGTH + 1);
            byte[] record = new byte[Room.MAX_RECORD_LENGTH];
            int tempRecordLength = fileObject.read(record);
            if (tempRecordLength != Room.MAX_RECORD_LENGTH) {
                throw new RecordNotFoundException("Record not found or record size does not match");
            }
            if (record[0] == DELETED) {
                throw new RecordNotFoundException("The record " + recNo + " has been deleted" );
            }
            return parseRecord(new String(record, ENCODING));
            
        } catch (Exception e) {
            throw new RecordNotFoundException("The record: " + recNo 
                    + " was not found, " + e.getMessage());
        }
    }
    
    private String[] parseRecord(String record) {
        int startIndex = 0;
        String[] recordValue = new String[fieldColumnNames.length];
        
        for (int i = 0; i < fieldColumnNames.length; i++ ) {
            int fieldLength = fieldMap.get(fieldColumnNames[i]).intValue();
            recordValue[i] = record.substring(startIndex, startIndex + fieldLength);
            startIndex += fieldLength;
        }
        return recordValue;
    }
    
    public void update(int recNo, String[] data) throws RecordNotFoundException {
        
    }
    
    public void delete(int recNo) throws RecordNotFoundException {
        
    }
    
    public int[] find(String[] criteria) throws RecordNotFoundException {
        int[] temp = {6,5,6};
        return temp;
    }
    
    public int create(String[] data) throws DuplicateKeyException {
        int temp = 1;
        return temp;
    }
}
