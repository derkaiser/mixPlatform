package client;


import internalInformationPort.InternalInformationPortController;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;


/**
 * Provides methods for generating message authentication codes (MACs) and 
 * MAC keys.
 * 
 * @author Karl-Peter Fuchs
 */
final class MacGenerator {

	/** 
	 * Reference on component <code>InternalInformationPortController</code>. 
	 * Used to display and/or log data and read general settings.
	 */
	private static InternalInformationPortController internalInformationPort = 
		new InternalInformationPortController();
	
	/** Logger used to log and display information. */
	private final static Logger LOGGER = internalInformationPort.getLogger();
	
	/** Name of the algorithm used to generate a message authentication code. */
	private final static String MAC_ALGORITHM;
	
	/** Mac generator object used to generate MACs. */
	private static Mac macGenerator = null;
	
	/** Key generator. */
	private static KeyGenerator keyGenerator;
	
	/* 
	 * Reads values from property file and instantiates macGenerator and 
	 * keyGenerator
	 */
	static {
		
		MAC_ALGORITHM = internalInformationPort.getProperty("MAC_ALGORITHM");
		
		try {
			
			macGenerator = Mac.getInstance(MAC_ALGORITHM);
			
		} catch (NoSuchAlgorithmException e) {
			
			LOGGER.severe(	"(IntegrityCheck) Invalid \"MAC_ALGORITHM\" "
							+"specified in property file!" 
							+e.getMessage()
							);
			
			System.exit(1);
			
		}
		
		try {
			
			keyGenerator = KeyGenerator.getInstance(MAC_ALGORITHM);
			
			keyGenerator.init(
				new Integer(
					internalInformationPort.getProperty("SYM_KEY_LENGTH")
					)
				);
			
		} catch (NoSuchAlgorithmException e) {
			
			LOGGER.severe(	"Invalid \"ASYM_CRYPTOGRAPHY_ALGORITHM\" " 
							+"specified in property file!" 
							+e.getMessage()
							);
			
			System.exit(1);
			
		}
		
	}
	
	
	/**
	 * Empty, private constructor (never used since all methods are static).
	 */
	private MacGenerator() {
		
	}
	
	
	/**
	 * Generates and returns a MAC key. Method is thread-safe.
	 * 
	 * @return	The generated MAC key.
	 */
	protected static SecretKey generateMacKey() {
		
		synchronized (keyGenerator) {
			
			return keyGenerator.generateKey();
			
		}
		
	}
	
	
	/**
	 * Generates and returns a message authentication code for the bypassed 
	 * message. Method is thread-safe.
	 * 
	 * @param key		Key to generate message authentication code with.
	 * @param message	Message to be authenticated.
	 * 
	 * @return			Message authentication code for the bypassed message.	
	 */
	protected static byte[] generateMAC(Key key, byte[] message) {
		
		synchronized (macGenerator) {
			/* 
			 * Must be synchronized due to the use of the static variable 
			 * "macGenerator" ("init()" and "doFinal()" mustn't be separated).
			 */
			
			try {
				
				macGenerator.init(key);
				
			} catch (InvalidKeyException e) {
				
				LOGGER.fine(	"(IntegrityCheck) Invalid key!"
								+e.getMessage()
								);
				
			}
	
			return macGenerator.doFinal(message);
			
		}
		
	}
	
}
