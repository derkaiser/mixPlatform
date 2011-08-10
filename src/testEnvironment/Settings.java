package testEnvironment;


import internalInformationPort.InternalInformationPortController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * Provides properties from the file <code>
 * <code>TestEnvironmentProperties.txt</code>.
 * 
 * @author Karl-Peter Fuchs
 */
final class Settings {
	
	/** 
	 * Reference on component <code>InternalInformationPort</code>. 
	 * Used to display and/or log data and read general settings.
	 */
	private static InternalInformationPortController internalInformationPort = 
		new InternalInformationPortController();
	
	/** Logger used to log and display information. */
	private final static Logger LOGGER = internalInformationPort.getLogger();
	
	/** <code>Properties</code> object to load values from. */
	private static Properties properties = new Properties();
 
	
	/* Loads property file from local file system. */
    static {
    	
    	try{
    		
    		//TODO: path specification --> see other Setting.java file
			properties.load(
					new FileInputStream(
							"TestEnvironmentProperties.txt" // /Users/daniel/Programmierung/Master/mixPlatform/bin/
							)
					);
			
		} catch(IOException e) {
			
			LOGGER.severe(	"Property file could not be loaded!"
							+e.getMessage()
							);
			
	    	System.exit(1);
	    	
	    }	
    }
    
    
    /**
     * Creates a new <code>Settings</code> object (empty constructor).
     */
	public Settings() {
		
	}
	
	
	/**
     * Searches and returns the property with the specified key in the 
     * <code>property</code> object.
     * 
     * @param key 	The property key.
     * @return 		Property with the specified key in the <code>property
     * 				</code> object.
     */
	public static String getProperty(String key) {
		
		return properties.getProperty(key);
		
	}
	
}
