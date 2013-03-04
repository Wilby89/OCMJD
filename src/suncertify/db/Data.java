package suncertify.db;

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
