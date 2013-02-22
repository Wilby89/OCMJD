package suncertify.db;

/**
 *
 * @author William Brosnan
 */
public class Data implements DBMain {
    
    /**
     * Constant to signify whether a record is valid in the database or if it has been deleted.
     * Chose to use constants since the values of the constant shouldn't change.
     * Could have read these values from the properties file but would have been outside scope/unnecessary work
     */
    private final static int DELETED = 0;
    /**
     * Constant to signify whether a record is valid in the database or if it has been deleted.
     * Chose to use constants since the values of the constant shouldn't change.
     * Could have read these values from the properties file but would have been outside scope/unnecessary work
     */
    private final static int PRESENT = 1;

    @Override
    public String[] read(int recNo) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(int recNo, String[] data) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int recNo) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int[] find(String[] criteria) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int create(String[] data) throws DuplicateKeyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void lock(int recNo) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unlock(int recNo) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLocked(int recNo) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
