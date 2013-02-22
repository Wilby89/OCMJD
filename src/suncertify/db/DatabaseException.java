package suncertify.db;

/**
 *
 * @author William Brosnan
 */
class DatabaseException extends Exception {
    /**
     * This is the default constructor which is passed no parameters, used to throw an instance of
     * this exception, e.g. throw new DatabaseException();
     * This Exception won't be thrown by me directly but will be called through the Exception classes 
     * which extend this exception, e.g. RecordNotFoundException and DuplicateKeyException
     */
    public DatabaseException () {}
    
    /**
     * This Exception won't be thrown by me directly but will be called through the Exception classes 
     * which extend this exception, e.g. RecordNotFoundException and DuplicateKeyException
     * The super call will then call the Exception constructor extended by this class
     * @param message 
     */
    public DatabaseException (String message) {
        //call Exception class constructor
        super(message);
    }
    
}
