package suncertify.db;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
 */
public class LockManager {
    
    /**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.db");
    
    /**
     * {@link}Map containing record locks
     */
    private static Map<Integer,Long> bookingsMap = new HashMap<Integer,Long>();
    
    /**
     * Locks a record so that it can only be updated or deleted by this client.
     * If the specified record is already locked, the current thread gives up
     * the CPU and consumes no CPU cycles until the record is unlocked.
     * @param recNo
     * @throws RecordNotFoundException 
     */
    public void lock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "lock", recNo);
        synchronized (bookingsMap) {
            final long ownerCookieValue = Thread.currentThread().getId();
            try {
                while (isLocked(recNo)) {
                    bookingsMap.wait();
                }
            } catch (InterruptedException inex) {
                throw new RecordNotFoundException("Problem found when attempting"
                        + " to lock record: " + inex.getMessage());
            }
            bookingsMap.put(recNo, ownerCookieValue);
        }
        logger.exiting("LockManager", "lock");
    }
    
    /**
     * Releases the lock on a record. 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    public void unlock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "unlock", recNo);
        synchronized (bookingsMap) {
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
                    bookingsMap.notifyAll();
                }
            } catch (Exception ex) {
                throw new RecordNotFoundException("Problem found when attempting"
                        + " to unlock record: " + ex.getMessage());
            }
        logger.exiting("LockManager", "unlock");
        }
    }
    
    /**
     * Determines if a record is currently locked. Returns true if the
     * record is locked, false otherwise.
     * @param recNo
     * @return boolean signifying record lock status
     * @throws RecordNotFoundException
     */
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "isLocked", recNo);
        if (bookingsMap.containsKey(recNo)) {
            logger.exiting("LockManager", "isLocked");
            return true;
        }
        logger.exiting("LockManager", "isLocked");
        return false;
    }
    
    public Long getOwner(int recNo) {
        return bookingsMap.get(recNo);
    }
}
