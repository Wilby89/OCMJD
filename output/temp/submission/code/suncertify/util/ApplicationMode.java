package suncertify.util;

/**
 *
 * @author William Brosnan
 * 
 * This Enumeration specifies in which mode the application will run
 */
public enum ApplicationMode {
    /**
     * Server Mode
     */
    SERVER,
    
    /**
     * Standalone mode, client only, no network access
     */
    ALONE,
    
    /**
     * Network mode, network client and gui must run.
     * To run this mode the user will not specify any parameters
     */
    NETWORK
}
