package suncertify.db;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
 * 
 * This class takes care of locking and unlocking the records in the database
 */
public class LockManager {
    
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.db");    
    /**
     * Map containing record locks
     */
    private final Map<Integer,Long> bookingsMap = new HashMap<Integer,Long>();
    /**
     * Create singleton instance of this <code>LockManager</code>.
     */
    private static final LockManager instance = new LockManager();
    
    /**
     * Return the singleton instance
     * @return 
     */
    public static LockManager getInstance() {
        return instance;
    }
    
    /**
     * Private constructor for the singleton
     */
    private LockManager() {}
    
    /**
     * Locks a record so that it can only be updated or deleted by this client.
     * If the specified record is already locked, the current thread gives up
     * the CPU and consumes no CPU cycles until the record is unlocked.
     * @param recNo
     * @throws RecordNotFoundException 
     */
    public synchronized void lock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "lock", recNo);
        final long ownerCookieValue = Thread.currentThread().getId();
        try {
            while (isLocked(recNo)) {
                wait();
            }
        } catch (InterruptedException inex) {
        throw new RecordNotFoundException("Problem found when attempting"
            + " to lock record: " + inex.getMessage());
        }
        bookingsMap.put(recNo, ownerCookieValue);
        logger.exiting("LockManager", "lock");
    }
    
    /**
     * Releases the lock on a record. 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    public synchronized void unlock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "unlock", recNo);        
        final long ownerCookieValue = Thread.currentThread().getId();
        try {
            if (bookingsMap.get(recNo) == null) {
                throw new RecordNotFoundException
                    ("Record not locked: " + recNo);
            }
            else if (bookingsMap.get(recNo) != ownerCookieValue) {
                throw new RecordNotFoundException
                    ("Record locked by another user: " + recNo);
            }
            else if (bookingsMap.containsKey(recNo)) {
                bookingsMap.remove(recNo);
                notifyAll();
            }
            } catch (Exception ex) {
                throw new RecordNotFoundException("Problem found when attempting"
                        + " to unlock record: " + ex.getMessage());
            }
        logger.exiting("LockManager", "unlock");        
    }
    
    /**
     * Determines if a record is currently locked. Returns true if the
     * record is locked, false otherwise.
     * @param recNo
     * @return boolean signifying record lock status
     * @throws RecordNotFoundException
     */
    public synchronized boolean isLocked(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "isLocked", recNo);
        final long ownerCookieValue = Thread.currentThread().getId();
        if (bookingsMap.containsKey(recNo) && bookingsMap.get(recNo)!=ownerCookieValue) {
            logger.exiting("LockManager", "isLocked");
            return true;
        }
        logger.exiting("LockManager", "isLocked");
        return false;
    }
    
    /**
     * Returns the id of the current owner of a lock
     * @param recNo
     * @return a long representing owner id
     */
    public Long getOwner(int recNo) {
        return bookingsMap.get(recNo);
    }
}
