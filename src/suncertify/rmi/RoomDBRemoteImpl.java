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
 *
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
     * 
     * @param recNo
     * @return
     * @throws RecordNotFoundException 
     */
    @Override
    public String[] read(int recNo) throws RecordNotFoundException {
        return database.read(recNo);
    }

    /**
     * 
     * @param recNo
     * @param data
     * @throws RecordNotFoundException 
     */
    @Override
    public void update(int recNo, String[] data) throws RecordNotFoundException {
        database.update(recNo, data);
    }

    /**
     * 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void delete(int recNo) throws RecordNotFoundException {
        database.delete(recNo);
    }

    /**
     * 
     * @param criteria
     * @return
     * @throws RecordNotFoundException 
     */
    @Override
    public int[] find(String[] criteria) throws RecordNotFoundException {
        return database.find(criteria);
    }

    /**
     * 
     * @param data
     * @return
     * @throws DuplicateKeyException 
     */
    @Override
    public int create(String[] data) throws DuplicateKeyException {
        return database.create(data);
    }

    /**
     * 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void lock(int recNo) throws RecordNotFoundException {
        database.lock(recNo);
    }

    /**
     * 
     * @param recNo
     * @throws RecordNotFoundException 
     */
    @Override
    public void unlock(int recNo) throws RecordNotFoundException {
        database.unlock(recNo);
    }

    /**
     * 
     * @param recNo
     * @return
     * @throws RecordNotFoundException 
     */
    @Override
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        return database.isLocked(recNo);
    }
}
