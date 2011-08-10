package architectureInterface;


import message.Message;


/**
 * Architecture interface for component <code>AccessControl</code>. 
 * <p>
 * Must be thread-safe.
 * 
 * @author Karl-Peter Fuchs
 */
public interface AccessControlInterface {

	
	/**
	 * Must perform an integrity check on the bypassed message.
	 * 
	 * @param message The message to be checked.
	 * 
	 * @return Result of integrity check.
	 */
	public boolean isMACCorrect(Message message);
	
}
