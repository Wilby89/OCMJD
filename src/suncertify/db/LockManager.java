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
    
    Map<String,Long> bookingsMap = new HashMap<String,Long>();
    
    public void lock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "lock", recNo);
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + "number: " + recNo);
        }
        logger.exiting("LockManager", "lock");
    }
    
    public void unlock(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "unlock", recNo);
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + "number: " + recNo);
        }
        logger.exiting("LockManager", "unlock");
    }
    
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        logger.entering("LockManager", "isLocked", recNo);
        if (recNo < 0) {
            throw new RecordNotFoundException("Record not found for record"
                    + "number: " + recNo);
        }
        logger.exiting("LockManager", "isLocked");
        return false;
    }
}
