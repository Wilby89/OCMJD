package suncertify.rmi;

import java.rmi.Remote;
import suncertify.db.DBMain;

/**
 * Remote interface for network GUI client
 *
 * @author William Brosnan
 */
public interface RoomDatabaseRemote extends Remote, DBMain{
    
}
