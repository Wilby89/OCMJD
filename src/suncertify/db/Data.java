package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author William Brosnan
 * 
 * This class implements the interface set out in the exam in the instructions.html page.
 * The interface will not be touched or edited by me, any additional methods I need can be added as part of
 * a facade pattern.
 */
public class Data implements DBMain {
    
    /**
     * The worker class that will contain the code to access the database
     */
    private static DataDBAccess database = null;
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.db");
    /**
     * This class will take care of locking/unlocking the database to prevent
     * data corruption
     */
    private final LockManager lockManager = new LockManager();
    /**
     * Location of database
     */
    private String dbLocation = null;
    
    /**
     * Empty constructor that will call other constructor with the database
     * location pulled from the properties file
     */
    public Data () {
        //this(System.getProperty("dbPath"));
    }
    
    /**
     * This constructor takes in the database location as a param
     * @param dbLocation
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Data(String dbLocation) throws FileNotFoundException, IOException {
        try {
            database = new DataDBAccess(dbLocation);
        } catch (DatabaseException dex) {
            logger.log(Level.SEVERE, dex.getMessage(), dex);
            System.err.println("Exception encountered when attempting to "
                    + "access database: " + dex.getMessage());
        }
    }

    /**
     * Reads a record from the file. Returns an array where each
     * element is a record value.
     * @param recNo
     * @return a <code>String[]</code> holding the field values of a record
     * @throws RecordNotFoundException 
     */
    @Override
    public String[] read(int recNo) throws RecordNotFoundException {
        return database.read(recNo);
    }

    /**
     * Modifies the fields of a record. The new value for field n 
     * appears in data[n].
     * @param recNo
     * @param data
     * @throws RecordNotFoundException 
     */
    @Override
    public void update(int recNo, String[] data) throws RecordNotFoundException {
       database.update(recNo, data);
    }

    /**
     * Deletes a record, making the record number and associated disk
     * storage available for reuse.
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void delete(int recNo) throws RecordNotFoundException {
        database.delete(recNo);
    }

    /**
     *  Returns an array of record numbers that match the specified
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
    @Override
    public int[] find(String[] criteria) throws RecordNotFoundException {
        return database.find(criteria);
    }

    /**
     * Creates a new record in the database (possibly reusing a
     * deleted entry). Inserts the given data, and returns the record
     * number of the new record.
     * @param data
     * @return the record number of the newly created record
     * @throws DuplicateKeyException 
     */
    @Override
    public int create(String[] data) throws DuplicateKeyException {
        return database.create(data);
    }

    /**
     * Locks a record so that it can only be updated or deleted by this client.
     * If the specified record is already locked, the current thread gives up
     * the CPU and consumes no CPU cycles until the record is unlocked.
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void lock(int recNo) throws RecordNotFoundException {
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + "number: " + recNo);
        }
        read(recNo);
        //if the read is unsuccessful a RecordNotFoundException will be thrown
        //and this line will not be reached
        lockManager.lock(recNo);
    }

    /**
     * Releases the lock on a record. 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void unlock(int recNo) throws RecordNotFoundException {
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + "number: " + recNo);
        }
        read(recNo);
        //if the read is unsuccessful a RecordNotFoundException will be thrown
        //and this line will not be reached
        lockManager.unlock(recNo);
    }

    /**
     * Determines if a record is currently locked. Returns true if the
     * record is locked, false otherwise.
     * @param recNo
     * @return boolean signifying record lock status
     * @throws RecordNotFoundException 
     */
    @Override
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + "number: " + recNo);
        }
        return lockManager.isLocked(recNo);
    }    
}
