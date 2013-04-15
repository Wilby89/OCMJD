package suncertify.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will be used to take care of low level db access
 * This will keep Data.java clean and manageable and will make use of the
 * OOP concept of delegation through the use of the Delegation pattern
 * 
 * @author William Brosnan
 */
public class DataDBAccess {
    
    /**
     * The number of bytes used to hold the magic cookie value
     */
    public static final int MAGIC_COOKIE_LENGTH = 4;
    /**
     * The value of the magic cookie in the database for validation purposes
     */
    public static final int MAGIC_COOKIE_VALUE = 259;
    /**
     * The number of bytes used to hold the number of fields in a record
     */
    public static final int NUMBER_OF_FIELDS_LENGTH = 2;
    /**
     * Length in bytes of field name 
     */
    public static final int FIELD_NAME_LENGTH = 1;
    /**
     * Field length in bytes 
     */
    public static final int ACTUAL_FIELD_LENGTH = 1;
    /**
     * 1 byte flag. 00 implies valid record, 0xFF implies deleted record 
     */
    public static final int RECORD_DELETION_STATUS_LENGTH = 1;
    /**
     * The character encoding is 8 bit US ASCII
     */
    public static final String ENCODING = "US-ASCII";
    /**
     * Constant to hold the database name
     */
    public static final String DATABASE_NAME = "db-1x3.db";
    /**
     * Database reader instance
     */
    private final RandomAccessFile fileObject;
    /**
     * Logger instance to send messages through
     */
    private Logger logger = Logger.getLogger("suncertify.db");
    /**
     * Hold the number of fields
     */
    private int numOfFields;
    /**
     * Array to hold the column names from the database
     */
    private String[] fieldColumnNames;
    /**
     * The location of the database on the system
     */
    private String dbLocation;
    /**
     * <code>HashMap</code> to map field names and sizes
     */
    private HashMap<String, Integer> fieldMap = null;
    /**
     * Variable to hold our location in the database
     */
    private int offset;
    /**
     * Variable to hold max record size + deleted flag size
     */
    private int maxRecord = Room.MAX_RECORD_LENGTH + RECORD_DELETION_STATUS_LENGTH;
    
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
    /**
     * LockManager instance
     */
    private LockManager locker;
    /**
     * Constructor that reads the header information in the database and
     * sets the offset to the start of the first record
     * @param dbLocation
     * @throws FileNotFoundException
     * @throws IOException
     * @throws DatabaseException 
     */    
    public DataDBAccess(String dbLocation, LockManager locker) 
            throws FileNotFoundException, IOException, DatabaseException {
        logger.entering("DataDBAccess", "DataDBAccess", dbLocation);
        this.locker = locker;
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
            fieldMap.put(fieldColumnNames[i], size);   
            offset += FIELD_NAME_LENGTH + ACTUAL_FIELD_LENGTH + nameSize;
            System.out.println("Offset: " + offset);
        }
         logger.exiting("DataDBAccess", "DataDBAccess", locker);
    }
    
    public String[] read(int recNo) throws RecordNotFoundException {
        logger.entering("DataDBAccess", "read", recNo);
        try {
            System.out.println("Offset going into read: " + (offset + recNo * maxRecord));
            fileObject.seek(offset + recNo * maxRecord);
            byte[] record = new byte[maxRecord];
            int tempRecordLength = fileObject.read(record);
            if (tempRecordLength != maxRecord) {
                throw new RecordNotFoundException("Record not found or record size does not match");
            }
            if (record[0] == DELETED) {
                throw new RecordNotFoundException("The record " + recNo + " has been deleted" );
            }
            logger.exiting("DataDBAccess", "read");
            return parseRecord(new String(record, ENCODING));
            
        } catch (Exception e) {
            throw new RecordNotFoundException("The record: " + recNo 
                    + " was not found, " + e.getMessage());
        }
    }
    
    private String[] parseRecord(String record) {
        int startIndex = 1;
        String[] recordValue = new String[fieldColumnNames.length];
        
        for (int i = 0; i < fieldColumnNames.length; i++ ) {
            int fieldLength = fieldMap.get(fieldColumnNames[i]).intValue();
            recordValue[i] = record.substring(startIndex, startIndex + fieldLength);
            startIndex += fieldLength;
        }
        return recordValue;
    }
    
    /**
     * Updates a record in the database
     * @param recNo
     * @param data
     * @throws RecordNotFoundException
     * @throws DatabaseException
     */
    public synchronized void update(int recNo, String[] data) 
            throws RecordNotFoundException, DatabaseException {
        logger.entering("DataDBAccess", "update", recNo);
        final Long lockCookie = Thread.currentThread().getId();
        Long ownerCookieValue = locker.getOwner(recNo);
        
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + " number: " + recNo);
        }
        
        if (ownerCookieValue == null) {
            throw new DatabaseException("Record is not locked: " + recNo);
        }
        
        if (ownerCookieValue.equals(lockCookie)) {
            try {
                fileObject.seek(offset + recNo * maxRecord);
                byte[] record = new byte[maxRecord];
                int tempRecordLength = fileObject.read(record);
                if (tempRecordLength != maxRecord) {
                    throw new RecordNotFoundException("Record not found or record size does not match");
                }
                if (record[0] == DELETED) {
                    throw new RecordNotFoundException("The record " + recNo + " has been deleted" );
                }
                fileObject.seek(offset + recNo * maxRecord);
                fileObject.writeByte(VALID);
                fileObject.write(getDataAsByteArray(data));
            } catch (Exception e) {
                throw new RecordNotFoundException("The record: " + recNo 
                    + " was not found, " + e.getMessage());
            }
        }
        else {
            throw new DatabaseException("Record already locked by another"
                    + " client: " + recNo);
        }
        logger.exiting("DataDBAccess", "update", data);
    }
    
    private byte[] getDataAsByteArray (String[] data) {
        final StringBuilder stringBuilder = new StringBuilder();
        byte[] byteArray = new byte[7];
        for (String str: data) {
            stringBuilder.append(str);
        }
        try {
            byteArray = stringBuilder.toString().getBytes(ENCODING);
        } catch (UnsupportedEncodingException ueex) {
            logger.severe("Unsupported character set: " 
                    + ENCODING + " " + ueex.getMessage());
        }
        return byteArray;
    }
    
    public synchronized void delete(int recNo) throws RecordNotFoundException, DatabaseException {
        logger.entering("DataDBAccess", "delete", recNo);
        final Long lockCookie = Thread.currentThread().getId();
        Long ownerCookieValue = locker.getOwner(recNo);
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + " number: " + recNo);
        }
        if (ownerCookieValue == null) {
            throw new DatabaseException("Record is not locked: " + recNo);
        }
        
        if (ownerCookieValue.equals(lockCookie)) {
            try {
                fileObject.seek(offset + recNo * maxRecord);
                byte[] record = new byte[maxRecord];
                int tempRecordLength = fileObject.read(record);
                if (tempRecordLength != maxRecord) {
                    throw new RecordNotFoundException("Record not found or record size does not match");
                }
                if (record[0] == DELETED) {
                    throw new RecordNotFoundException
                            ("The record " + recNo + " has already been deleted" );
                }
                fileObject.seek(offset + recNo * maxRecord);
                fileObject.writeByte(DELETED);
            } catch (Exception e) {
                throw new RecordNotFoundException("The record: " + recNo 
                    + " was not found, " + e.getMessage());
            }
        }
        else {
            throw new DatabaseException("Record already locked by another"
                    + " client: " + recNo);
        }
        logger.exiting("DataDBAccess", "delete");
    }
    
    public int[] find(String[] criteria) throws RecordNotFoundException {
        logger.entering("DataDBAccess", "find", criteria);
        ArrayList<Integer> foundArray = new ArrayList<Integer>();
        String[] recordData;
        int[] results;
        if (criteria[0] == null && criteria[1] == null) {
            return findAllRecords();
        }
        else if (!criteria[0].equals("") && criteria[1].equals("")) {
            results = findAllRecords();
            for (int i = 0; i < results.length; i++) {
                recordData = this.read(i);            
                if (recordData[0].startsWith(criteria[0])) {
                    foundArray.add(i);
                }
            }
            return intArrayConvert(foundArray);
        }
        else if (criteria[0].equals("") && !criteria[1].equals("")) {
            results = findAllRecords();
            for (int i = 0; i < results.length; i++) {
                recordData = this.read(i);            
                if (recordData[1].startsWith(criteria[1])) {
                    foundArray.add(i);
                }
            }
            return intArrayConvert(foundArray);
        }
        else {
            results = findAllRecords();
            for (int i = 0; i < results.length; i++) {
                recordData = this.read(i);            
                if (recordData[0].startsWith(criteria[0]) && recordData[1].startsWith(criteria[1])) {
                    foundArray.add(i);
                }
            }
            logger.exiting("DataDBAccess", "find");
            return intArrayConvert(foundArray);
        }        
    }
    
    public synchronized int create(String[] data) throws DuplicateKeyException, RecordNotFoundException {
        logger.entering("DataDBAccess", "create", data);
        if (data == null) {
            throw new IllegalArgumentException("Record not found for record"
                    + " number");
        }
        int[] existingRecords = find(data);
        if (existingRecords != null || existingRecords.length > 0) {
            throw new DuplicateKeyException("Record already exists");
        }
        try {            
            int position = getPositionToInsert();  
            fileObject.seek(offset + position * maxRecord);
            fileObject.writeByte(VALID);
            fileObject.write(getDataAsByteArray(data));
            logger.exiting("DataDBAccess", "create",
                    "Created at position: " + position);
            return position;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public int getPositionToInsert() {
        int insertPosition = 0;
        try {
            this.fileObject.seek(offset);
            byte[] record = new byte[maxRecord];
            while (this.fileObject.read(record) == maxRecord) {
                if (record[0] == DELETED) {
                    return insertPosition;
                }
                insertPosition++;
                record = new byte[maxRecord];
            }
            return insertPosition;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * I made the choice to call this function on start-up purely for aesthetic 
     * reasons.
     * All records are displayed on startup.
     * @return an <code>int[]</code> that holds all record numbers that correspond
     * to records in the database
     */
    private int[] findAllRecords() {
        int recNum = 0;
        ArrayList<Integer> recNumArray = new ArrayList<Integer>();
        try {
        for (int i = this.offset; i < this.fileObject.length();
                i += this.maxRecord) {
            recNumArray.add(recNum);
            recNum++;
        }
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, ioe.getMessage(), ioe);
            System.err.println("I/O problem found when attempting to load all"
                    + "records: " + ioe.getMessage());
        }
        return intArrayConvert(recNumArray);
    }
    
    /**
     * This is a private helper method used to convert the <code>ArrayList</code>
     * from <code>findAllRecords</code> to the expected <code>int[]</code>
     * @param intArrayList
     * @return an <code>int[]</code> containing the record numbers corresponding
     * to the records in the database
     */
    private int[] intArrayConvert(ArrayList<Integer> intArrayList) {
        int[] convertedArray = new int[intArrayList.size()];
        int i = 0;        
        for (Integer integer: intArrayList) {
            convertedArray[i] = integer;
            i++;
        }        
        return convertedArray;            
    }
}
