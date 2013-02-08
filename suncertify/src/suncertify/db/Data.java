package suncertify.db;

/**
 *
 * @author William Brosnan
 */
public class Data implements DBMain {

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
