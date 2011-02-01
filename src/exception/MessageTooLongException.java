package exception;


/**
 * Exception thrown when a <code>Client</code> tries to generate a mix message, 
 * but the bypassed message (= payload) is larger than the maximum message size 
 * (see package <code>message</code> for the maximum allowed size).
 * 
 * @see message
 * 
 * @author Karl-Peter Fuchs
 */
public final class MessageTooLongException extends Exception {
	
	/**The serialVersionUID as identifier for this serializable class*/
	private static final long serialVersionUID = 34896789939458L;
	
	
	/**
	 * Constructs a MessageTooLongException (empty constructor).
	 */
	public MessageTooLongException() {
		
	}
	
	
	/**
	 * Returns the String "Message is too long".
	 * @return "Message is too long"
	 */
	public String getMessage() {
		
		return "Message is too long";
		
	}
	
	
	/**
	 * Returns the String "MessageTooLongException".
	 * @return "MessageTooLongException"
	 */
	public String toString() {
		
		return "MessageTooLongException";
		
	}
	
}
