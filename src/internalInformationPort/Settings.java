package internalInformationPort;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.crypto.Cipher;


/**
 * Provides properties from the file <code>Properties.txt</code>.
 * 
 * @author Karl-Peter Fuchs
 */
final class Settings {
	
	/** Logger used to log and display information. */
	private final static Logger LOGGER = LogFileGenerator.getLogger();
	
	/** <code>Properties</code> object to load values from. */
	private static Properties properties = new Properties();
 
	
	/* Loads property file from local file system. */
    static {
    	
    	try {
    		
    		//TODO: path specification
			properties.load(new FileInputStream("/Users/daniel/Programmierung/Master/mixPlatform/bin/Properties.txt"));
			
		} catch(IOException e) {
			
			//TODO: path specification
			//when properties could not be loaded, there is no way to use the logger as it is not instantiated...
			System.out.println("Property file could not be loaded! "
							+e.getMessage()
							);
			
			/*LOGGER.severe(	"Property file could not be loaded!"
							+e.getMessage()
							);*/

	    	System.exit(1);
	    	
	    }	
		
		addFurterProperties();
		
    }
    
    
    /**
	 * Empty constructor. Never used since all methods are static.
	 */
    private Settings() {
		
	}
	
	
	/**
     * Searches and returns the property with the specified key in the 
     * <code>property</code> object.
     * 
     * @param key 	The property key.
     * @return 		Property with the specified key in the <code>property
     * 				</code> object.
     */
	protected static String getProperty(String key) {
		
		return properties.getProperty(key);
		
	}
	
	
	/**
     * Sets the property with the specified key in the <code>property
     * </code> object to the bypassed value.
     * 
     * @param key	The key to be placed into the property list.
     * @param value	The value corresponding to the key.
     * @return		The previous value of the specified key, or null if it did 
     * 				not have one.
     */
	protected static Object setProperty(String key, String value) {
		
		return properties.setProperty(key, value);
		
	}
	
	
	/**
     * Returns this <code>Settings</code>' <code>Properties</code> object.
     * 
     * @return This <code>Settings</code>' <code>Properties</code> object.
     */
	protected static Properties getProperties() {
		
		return properties;
		
	}
	
	
	/**
	 * Adds further properties to the <code>Properties</code> object, that can 
	 * be calculated from existing properties and therefore shouldn't occur 
	 * in the property file itself.
	 */
	private static void addFurterProperties() {
		
		// INTER_MIX_BLOCK_SIZE:
		try {
			
			Cipher tempCipher = Cipher.getInstance(
					properties.getProperty("INTER_MIX_CRYPTOGRAPHY_ALGORITHM"), 
					properties.getProperty("CRYPTO_PROVIDER")
					);
			
			System.out.println("OK");
			
			int interMixBlockSize = tempCipher.getBlockSize();
			

			
			tempCipher = null;
			
			properties.setProperty(	"INTER_MIX_BLOCK_SIZE", 
									""+interMixBlockSize
									);
			
		} catch (Exception e) {
			
			System.out.println("(Settings) Couldn't detect block size for inter-" 
							+"mix cryptography" + e.getMessage()
							);

			/*LOGGER.severe(	"(Settings) Couldn't detect block size for inter-" 
							+"mix cryptography"
							);*/
			
			System.exit(1);
			
		}
		
		// SYMMETRIC_CYPHER_BLOCK_SIZE:
		try {
			
			Cipher tempCipher = Cipher.getInstance(
					properties.getProperty("SYM_CRYPTOGRAPHY_ALGORITHM"), 
					properties.getProperty("CRYPTO_PROVIDER")
					);
			
			int symmetricCipherBlockSize = tempCipher.getBlockSize();
			tempCipher = null;
			
			properties.setProperty(	"SYMMETRIC_CYPHER_BLOCK_SIZE", 
									""+symmetricCipherBlockSize
									);
			
		} catch (Exception e) {

			LOGGER.severe(	"(Settings) Couldn't detect block size for " 
							+"symmetric cipher!"
							);
			
			System.exit(1);
			
		}
		
		
	}

}
