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
     * This class will take care of locking/unlocking the database to prevent
     * data corruption
     */
    private Logger logger = Logger.getLogger("suncertify.db");
    private static LockManager lockManager = null;
    private String dbLocation = null;
    
    public Data () {}
    
    public Data(String dbLocation) throws FileNotFoundException, IOException {
        try {
            database = new DataDBAccess(dbLocation);
        } catch (DatabaseException dex) {
            logger.log(Level.SEVERE, dex.getMessage(), dex);
            System.err.println("Exception encountered when attempting to "
                    + "access database: " + dex.getMessage());
        }
    }

    @Override
    public String[] read(int recNo) throws RecordNotFoundException {
        return database.read(recNo);
    }

    @Override
    public void update(int recNo, String[] data) throws RecordNotFoundException {
       database.update(recNo, data);
    }

    @Override
    public void delete(int recNo) throws RecordNotFoundException {
        database.delete(recNo);
    }

    @Override
    public int[] find(String[] criteria) throws RecordNotFoundException {
        return database.find(criteria);
    }

    @Override
    public int create(String[] data) throws DuplicateKeyException {
        return database.create(data);
    }

    @Override
    public void lock(int recNo) throws RecordNotFoundException {
        lockManager.lock(recNo);
    }

    @Override
    public void unlock(int recNo) throws RecordNotFoundException {
        lockManager.unlock(recNo);
    }

    @Override
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        return lockManager.isLocked(recNo);
    }    
}
