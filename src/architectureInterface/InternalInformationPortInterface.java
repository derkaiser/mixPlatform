package architectureInterface;


import java.util.logging.Logger;


/**
 * Architecture interface for component <code>InternalInformationPort</code>. 
 * <p>
 * Used for information exchange (for example via log files (output), property 
 * files (input) or display output) with "internal" communication partners 
 * (usually the system administrator).
 * <p>
 * Must be thread-safe.
 * 
 * @author Karl-Peter Fuchs
 */
public interface InternalInformationPortInterface {

	
	/**
	 * Must return a <code>Logger</code>, that can be used to log messages in a 
	 * file and/or display them on the standard output (depending on the log 
	 * <code>Level</code>s, specified in property file). 
	 * 
	 * @return	<code>Logger</code> that can be used to log messages in a file 
	 * 			and/or display them on the standard output.
	 */
	public Logger getLogger();
	
	
	/**
     * Must return the property with the specified key from the property file.
     * 
     * @param key 	The property key.
     * 
     * @return 		The property with the specified key in the property file.
     */
	public String getProperty(String key);
	
	
	/**
     * Must set the property with the specified key to the bypassed value.
     * 
     * @param key	Identifier of the property.
     * @param value	The value corresponding to the key.
     * 
     * @return		The previous value of the specified key, or null if it did 
     * 				not have one.
     */
	public Object setProperty(String key, String value);
	
}
