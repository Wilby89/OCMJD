package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the DBMain interface supplied as part of the instructions.
 * 
 * @author William Brosnan
 * 
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
    private LockManager lockManager = LockManager.getInstance();
    /**
     * Location of database
     */
    private String dbLocation = null;
    
    /**
     * Empty constructor
     */
    public Data () {logger.info("Entering empty Room constructor");}
    
    /**
     * This constructor takes in the database location as a parameter
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
        try {
            database.update(recNo, data);
        } catch (DatabaseException dex) {
            logger.log(Level.SEVERE, dex.getMessage(), dex);
            System.err.println("Locking problem found when attempting update "
                     + dex.getMessage());
        }
    }

    /**
     * Deletes a record, making the record number and associated disk
     * storage available for reuse.
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void delete(int recNo) throws RecordNotFoundException {
        try {
            database.delete(recNo);
        } catch (DatabaseException dex) {
            logger.log(Level.SEVERE, dex.getMessage(), dex);
            System.err.println("Locking problem found when attempting deletion "
                     + dex.getMessage());
        }
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
        int creationPointer = 0;
        try {
            creationPointer =  database.create(data);
        } catch (RecordNotFoundException rex) {
            logger.log(Level.SEVERE, rex.getMessage(), rex);
            System.err.println("Problem found when attempting creation "
                     + rex.getMessage());
        }
        return creationPointer;
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
        try {
        read(recNo);
        } catch (Exception ex) {
            //The problem will be that the record has been deleted, we do not want
            //to lock in this case.
            logger.log(Level.SEVERE, "Problem found: " + ex.getMessage(), ex);
            throw new RecordNotFoundException(ex.getMessage());
        }
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
        try {
        read(recNo);
        } catch (Exception ex) {
            //The problem will be that the record has been deleted or the record number does not exist,
            //we do not want to lock in this case.
            logger.log(Level.SEVERE, "Problem found: " + ex.getMessage(), ex);
            throw new RecordNotFoundException(ex.getMessage());
        } finally {
            //if the record has been deleted we still want to unlock the record
            //in case a create wants to overwrite the deleted record
            lockManager.unlock(recNo);
        }
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
