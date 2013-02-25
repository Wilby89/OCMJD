package suncertify.db;

/**
 *
 * @author William Brosnan
 * This is the Exception class used when an exception is caught when failing to find a record.
 * It is extended by both DuplicateKeyException and RecordNotFoundException
 */
class DatabaseException extends Exception {
    /**
     * This is the default constructor which is passed no parameters, used to throw an instance of
     * this exception, e.g. throw new DatabaseException();
     * This Exception won't be thrown by me directly but will be called through the Exception classes 
     * which extend this exception, e.g. RecordNotFoundException and DuplicateKeyException
     */
    public DatabaseException() { }
    
    /**
     * This Exception won't be thrown by me directly but will be called through the Exception classes 
     * which extend this exception, e.g. RecordNotFoundException and DuplicateKeyException
     * The super call will then call the Exception constructor extended by this class
     * @param message 
     */
    public DatabaseException(final String message) {
        //call Exception class constructor
        super(message);
    }
}
