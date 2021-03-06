package suncertify.db;

/**
 * This is the Exception class used when failing to find a record.
 * It extends another custom class, DatabaseException so that both RecordNotFoundException and
 * DuplicateKeyException can extend the same class.
 * 
 * @author William Brosnan
 */
public class RecordNotFoundException extends DatabaseException {
    /**
     * This is the default zero argument constructor, used to throw an instance of
     * this exception, e.g. throw new RecordNotFoundException();
     */
    public RecordNotFoundException() { }
    
    /**
     * This constructor is passed in an exception message which is passed up to DatabaseException
     * @param message 
     */
    public RecordNotFoundException(String message) {
        //Call DatabaseException class constructor
        super(message);
    }
}
