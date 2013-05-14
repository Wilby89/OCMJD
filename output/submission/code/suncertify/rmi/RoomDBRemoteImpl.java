package suncertify.rmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import suncertify.db.DBMain;
import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

/**
 * This class implements the remote interface
 * @author William Brosnan
 */
public class RoomDBRemoteImpl extends UnicastRemoteObject implements RoomDatabaseRemote {
    
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.rmi");
    /**
     * Create instance of Data using the DBMain Interface
     */
    private DBMain database = null;
    
    public RoomDBRemoteImpl (String dbLocation) throws RemoteException {
        try {
            database = new Data(dbLocation);
        } catch (FileNotFoundException fex) {
            logger.log(Level.SEVERE, "File not found at: " + dbLocation + 
                    "Exception is: " + fex.getMessage());
        } catch (IOException iex) {
            logger.log(Level.SEVERE, "I/O problem with file at: " + 
                    dbLocation + "Exception is: " + iex.getMessage());
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
        database.lock(recNo);
    }

    /**
     * Releases the lock on a record. 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void unlock(int recNo) throws RecordNotFoundException {
        database.unlock(recNo);
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
        return database.isLocked(recNo);
    }
}
