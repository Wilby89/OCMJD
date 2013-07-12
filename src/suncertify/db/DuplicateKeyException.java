package suncertify.db;

/**
 *
 * @author William Brosnan
 * 
 * This is the Exception class used when a duplicate key is found.
 * It extends another custom class, DatabaseException so that both RecordNotFoundException and
 * DuplicateKeyException can extend the same class.
 */
public class DuplicateKeyException extends DatabaseException {
    /**
     * This is the default zero argument constructor, used to throw an instance of
     * this exception, e.g. throw new DuplicateKeyException();
     */
    public DuplicateKeyException() { }
    
    /**
     * This constructor is passed in an exception message which is passed up to DatabaseException
     * @param message 
     */
    public DuplicateKeyException(String message) {
        //Call DatabaseException class constructor
        super(message);
    }
}
