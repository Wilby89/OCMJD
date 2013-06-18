package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
 */
public class RemoteDBMainImpl implements RemoteDBMain {
    
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.db");
    /**
     * Create instance of Data using the DBMain Interface
     */
    private DBMain database = null;
    
    public RemoteDBMainImpl (String dbLocation) throws RemoteException {
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

    @Override
    public String[] read(int recNo) throws RecordNotFoundException, RemoteException {
        return database.read(recNo);
    }

    @Override
    public void update(int recNo, String[] data) throws RecordNotFoundException, RemoteException {
        database.update(recNo, data);
    }

    @Override
    public void delete(int recNo) throws RecordNotFoundException, RemoteException {
        database.delete(recNo);
    }

    @Override
    public int[] find(String[] criteria) throws RecordNotFoundException, RemoteException {
        return database.find(criteria);
    }

    @Override
    public int create(String[] data) throws DuplicateKeyException, RemoteException {
        return database.create(data);
    }

    @Override
    public void lock(int recNo) throws RecordNotFoundException, RemoteException {
        database.lock(recNo);
    }

    @Override
    public void unlock(int recNo) throws RecordNotFoundException, RemoteException {
        database.unlock(recNo);
    }

    @Override
    public boolean isLocked(int recNo) throws RecordNotFoundException, RemoteException {
        return database.isLocked(recNo);
    }
    
}
