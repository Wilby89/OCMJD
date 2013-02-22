package suncertify.db;

/**
 *
 * @author William Brosnan
 * 
 * This is the Exception class used when an exception is caught when failing to find a record.
 * It extends another custom class, DatabaseException so that both RecordNotFoundException and
 * DuplicateKeyException can extend the same class.
 */
class RecordNotFoundException extends DatabaseException {
    /**
     * This is the default constructor which is passed no parameters, used to throw an instance of
     * this exception, e.g. throw new RecordNotFoundException();
     */
    public RecordNotFoundException() {}
    
    /**
     * This constructor is passed in an exception message which is passed up to DatabaseException
     * @param message 
     */
    public RecordNotFoundException(String message) {
        //Call DatabaseException class constructor
        super(message);
    }
}