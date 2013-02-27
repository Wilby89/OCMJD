package suncertify.db;

import java.io.File;
import java.io.FileNotFoundException;
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
    private static final int NUMBER_OF_FIELDS_LENGTH = 2;
    private static final String ENCODING = "US-ASCII";
    private static final String DATABASE_NAME = "db-1x3.db";
    private static RandomAccessFile fileObject;
    private String[] recordList;
    private String dbLocation;
    
    
    public DataDBAccess(String dbLocation) {
        this.dbLocation = dbLocation;
    }
    
    public String[] read(int recNo) throws RecordNotFoundException, FileNotFoundException {
        fileObject = new RandomAccessFile(new File(dbLocation),"r");
        return recordList;
    }
}
