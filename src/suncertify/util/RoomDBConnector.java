package suncertify.util;

import suncertify.db.Data;

/**
 * 
 * @author William Brosnan
 * 
 * This class will be used to create a connection to the database when run in
 * standalone mode, using this class will result in a direct connection to the database
 */
public class RoomDBConnector {
    
    /**
     * I chose not to use private constructor
     * No need for a constructor in a utility class, the only function that will
     * be called is <code>getLocalConnection</code>
     * @param location the location of the local database
     * @return A <code>Data</code> instance
     */
    public static Data getLocalConnection(String dbLocation) {
        return new Data(dbLocation);
    }
}
