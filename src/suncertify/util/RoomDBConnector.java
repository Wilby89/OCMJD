package suncertify.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import suncertify.db.DBMain;
import suncertify.db.Data;

/**
 * This class will be used to create a connection to the database when run in
 * standalone mode, using this class will result in a direct connection to the database
 * 
 * @author William Brosnan
 */
public class RoomDBConnector {
    
    /**
     * I chose not to use private constructor
     * No need for a constructor in a utility class, the only function that will
     * be called is <code>getLocalConnection</code>
     * @param dbLocation the location of the local database
     * @return A <code>Data</code> instance
     */
    public static DBMain getLocalConnection(String dbLocation) throws FileNotFoundException, IOException {
        return new Data(dbLocation);
    }
}
