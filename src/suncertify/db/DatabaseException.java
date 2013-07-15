package suncertify.db;

/**
 * This is the Exception class used when an exception is caught when failing to find a record.
 * It is extended by both DuplicateKeyException and RecordNotFoundException
 * 
 * @author William Brosnan
 */
public class DatabaseException extends Exception {
    /**
     * This is the default zero argument constructor, used to throw an instance of
     * this exception, e.g. throw new DatabaseException();
     * Generally this will be called by the RecordNotFoundException and DuplicateException classes.
     */
    public DatabaseException() { }
    
    /**
     * Generally this will be called by the RecordNotFoundException and DuplicateException classes.
     * The super call will then call the Exception constructor extended by this class
     * @param message 
     */
    public DatabaseException(final String message) {
        //call Exception class constructor
        super(message);
    }
}
