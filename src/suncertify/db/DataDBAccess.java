package suncertify.db;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
     * Database reader/writer instance
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
     */
    private static final byte DELETED = (byte) 0xFF;
    /**
     * Constant to signify whether a record is valid in the database or if it has been deleted.
     */
    private static final byte VALID = 00;
    /**
     * LockManager instance
     */
    private LockManager locker = LockManager.getInstance();
    /**
     * Constructor that reads the header information in the database and
     * sets the offset to the start of the first record
     * @param dbLocation
     * @throws FileNotFoundException
     * @throws IOException
     * @throws DatabaseException 
     */    
    public DataDBAccess(String dbLocation) 
            throws FileNotFoundException, IOException, DatabaseException {
        logger.entering("DataDBAccess", "DataDBAccess", dbLocation);
        fileObject = new RandomAccessFile(new File(dbLocation),"rw");
        final int magicCookie = fileObject.readInt();
        if (magicCookie != MAGIC_COOKIE_VALUE) {
            throw new DatabaseException("Magic cookie values do not match");
        }
        offset = MAGIC_COOKIE_LENGTH + NUMBER_OF_FIELDS_LENGTH;
        numOfFields = fileObject.readShort();
        fieldColumnNames = new String[numOfFields];
        fieldMap = new HashMap<String, Integer>();
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            byte[] fieldName = new byte[nameSize];
            fileObject.read(fieldName);
            fieldColumnNames[i] = new String(fieldName, ENCODING);
            int size = fileObject.readByte();
            fieldMap.put(fieldColumnNames[i], size);   
            offset += FIELD_NAME_LENGTH + ACTUAL_FIELD_LENGTH + nameSize;
        }
         logger.exiting("DataDBAccess", "DataDBAccess", locker);
    }
    
    /**
     * Reads a record from the file. Returns an array where each
     * element is a record value.
     * @param recNo
     * @return a <code>String[]</code> holding the field values of a record
     * @throws RecordNotFoundException 
     */
    public synchronized String[] read(int recNo) throws RecordNotFoundException {
        logger.entering("DataDBAccess", "read", recNo);
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
            logger.exiting("DataDBAccess", "read");
            return parseRecord(new String(record, ENCODING));
            
        } catch (Exception e) {
            throw new RecordNotFoundException("The record: " + recNo 
                    + " was not found, " + e.getMessage());
        }
    }
    
    /**
     * Helper method to parse the record string retrieved from the database.
     * @param record
     * @return a String array containing the record values
     */
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
     * Modifies the fields of a record. The new value for field n 
     * appears in data[n].
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
    
    /**
     * Helper method used to convert the String array into a byte array.
     * @param data
     * @return a byte array containing the data that was in the string array.
     */
    private byte[] getDataAsByteArray (String[] data) throws IOException {
        //final StringBuilder stringBuilder = new StringBuilder();
        //byte[] byteArray = new byte[7];
        ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
        DataOutputStream dOutputStream = new DataOutputStream(bOutputStream);
        for (int i = 0; i < fieldColumnNames.length; i++) {
            String str = data[i];
            short fieldLength = (fieldMap.get(fieldColumnNames[i])).shortValue();
            byte[] byteArray = new byte[fieldLength];
            int j = 0;
            for (byte b : str.getBytes()) {
                byteArray[j++] = b;
            }
            for (int k = 0; k < fieldLength; k++) {
                dOutputStream.write(byteArray[k]);
                }
            }
            dOutputStream.flush();
            dOutputStream.close();
            byte[] returnArray = bOutputStream.toByteArray();
            return returnArray;
        //for (String str: data) {
        //    stringBuilder.append(str);
        //}
        //try {
            //byteArray = stringBuilder.toString().getBytes(ENCODING);
        //} catch (UnsupportedEncodingException ueex) {
        //    logger.severe("Unsupported character set: " 
        //            + ENCODING + " " + ueex.getMessage());
        //}        
    }
    
    /**
     * Deletes a record, making the record number and associated disk
     * storage available for reuse.
     * @param recNo
     * @throws RecordNotFoundException 
     */
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
    
    /**
     * Returns an array of record numbers that match the specified
     * criteria. Field n in the database file is described by
     * criteria[n]. A null value in criteria[n] matches any field
     * value. A non-null  value in criteria[n] matches any field
     * value that begins with criteria[n]. (For example, "Fred"
     * matches "Fred" or "Freddy".)
     * @param criteria
     * @return an <code>int[]</code> holding record numbers that correspond to 
     * the records in the database
     * @throws RecordNotFoundException 
     */
    public synchronized int[] find(String[] criteria) throws RecordNotFoundException {
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
    
    /**
     * Creates a new record in the database (possibly reusing a
     * deleted entry). Inserts the given data, and returns the record
     * number of the new record.
     * @param data
     * @return the record number of the newly created record
     * @throws DuplicateKeyException
              if a duplicate key is detected when creating record.
     * @throws RecordNotFoundException
     */
    public synchronized int create(String[] data) throws DuplicateKeyException, RecordNotFoundException {
        logger.entering("DataDBAccess", "create", data);
        if (data == null) {
            throw new IllegalArgumentException("Corrupt or invalid data");
        }
        //Method does not throw a DuplicateKeyException as there is no set criteria
        //on what makes a record unique, two records with the exact same information in the
        //database could possibly be two seperate rooms in the same hotel with the same record
        //data, it's unlikely but possible, see choices.txt for my rationale
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
    
    /**
     * Returns the integer position in the file where the insert should start.
     * @return an int for start position of insert
     */
    private int getPositionToInsert() {
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
     * Helper method to display all records, this method is called by default on
     * GUI startup by the <code>HotelFrameController</code>.
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
