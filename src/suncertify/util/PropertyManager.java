package suncertify.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This singleton class is used to provide read/write access to the suncertify.properties
 * file for user configuration
 * 
 * @author William Brosnan
 */
public final class PropertyManager {
    
    /**
     * Constant representing properties file name
     */
    private static final String PROPERTY_FILE_NAME = "suncertify.properties";
    /**
     * Constant representing the directory where the application is run from
     */
    private static final String PROPERTY_FILE_DIR = System.getProperty("user.dir");
    /**
     * File object containing configuration parameters
     */
    private static File propertiesFile = 
            new File(PROPERTY_FILE_DIR, PROPERTY_FILE_NAME);
    
    /**
     * Constant representing the path to the database
     */
    private static final String DATABASE_PATH = "dbPath";
    /**
     * Constant representing the RMI host name
     */
    private static final String RMI_HOST = "rmiHost";
    /**
     * Constant representing the RMI port
     */
    private static final String RMI_PORT = "rmiPort";
    /**
     * Logger instance to send messages through
     */    
    private Logger logger = Logger.getLogger("suncertify.rmi");
    /**
     * Create properties instance that will hold properties and values.
     */
    private Properties properties = null;
    /**
     * Create singleton instance of this <code>PropertyManager</code>.
     */
    private static final PropertyManager instance = new PropertyManager();
    /**
     * Return the singleton instance
     * @return singleton instance of this <code>PropertyManager</code>
     */
    public static PropertyManager getInstance() {
        return instance;
    }
    /**
     * Constructor for the singleton class
     */
    private PropertyManager() {
        properties = loadProperties();
        
        if (properties == null || properties.isEmpty()) {
            properties = new Properties();
            
            properties.setProperty(RMI_HOST, "localhost");
            properties.setProperty(DATABASE_PATH, "");
            properties.setProperty(RMI_PORT, 
                    "" + java.rmi.registry.Registry.REGISTRY_PORT);
        }        
    }
    
    /**
     * Retrieve property from properties file
     * @param propertyName
     * @return a String representation of the required property
     */
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
    
    /**
     * Set property in properties file
     * @param propertyName
     * @param propertyValue 
     */
    public void setProperty(String propertyName, String propertyValue) {
        properties.setProperty(propertyName, propertyValue);
        saveProperties();
    }
    
    /**
     * Save the properties configuration to the properties file
     */
    private void saveProperties() {
        try {
            synchronized (propertiesFile) {
                if (propertiesFile.exists()) {
                    propertiesFile.delete();
                }
                propertiesFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(propertiesFile);
                properties.store(fos, "UrlyBird Configuration");
                fos.close();
            }
        } catch (IOException iex) {
            logger.log(Level.SEVERE, "I/O problem when accessing file: "
                            + propertiesFile + "Exception is: " + iex.getMessage());
        }
    }
    
    /**
     * Load the properties configuration from the properties file
     * @return <code>Properties</code> object containing properties
     * from properties file
     */
    private Properties loadProperties() {
        Properties loadedProperties = null;
        logger.log(Level.INFO, "File is: " + propertiesFile.toString());
        
        if (propertiesFile.exists() && propertiesFile.canRead()) {
            synchronized(propertiesFile) {
                try {
                    loadedProperties = new Properties();
                    FileInputStream fis = new FileInputStream(propertiesFile);
                    loadedProperties.load(fis);
                    fis.close();
                } catch (FileNotFoundException fex) {
                    logger.log(Level.SEVERE, "File not found at: " 
                            + propertiesFile + "Exception is: " + fex.getMessage());
                } catch (IOException iex) {
                    logger.log(Level.SEVERE, "I/O problem when accessing file: "
                            + propertiesFile + "Exception is: " + iex.getMessage());
                }               
            }
        }
        return loadedProperties;
    }    
}
