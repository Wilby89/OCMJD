package suncertify.db;

import java.util.logging.Logger;

/**
 *
 * @author William Brosnan
 */
public class LockManager {
    
    private Logger logger = Logger.getLogger("suncertify.db");
    
    public void lock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "lock", recNo);
        logger.exiting("LockManager", "lock");
    }
    
    public void unlock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "unlock", recNo);
        logger.exiting("LockManager", "unlock");
    }
    
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "isLocked", recNo);
        logger.exiting("LockManager", "isLocked");
        return false;
    }
}
