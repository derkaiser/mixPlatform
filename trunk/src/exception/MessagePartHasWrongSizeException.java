package exception;


/**
 * Exception thrown when a <code>MessagePart</code> that shall be assigned is 
 * of wrong size.
 * 
 * @see message
 * 
 * @author Karl-Peter Fuchs
 */
public final class MessagePartHasWrongSizeException extends Exception {
	
	/**The serialVersionUID as identifier for this serializable class*/
	private static final long serialVersionUID = 34890534444318L;
	
	
	/**
	 * Constructs a MessagePartHasWrongSizeException (empty constructor).
	 */
	public MessagePartHasWrongSizeException() {
		
	}
	
	
	/**
	 * Returns the String "MessagePart has wrong size".
	 * @return "MessagePart has wrong size"
	 */
	public String getMessage() {
		
		return "MessagePart has wrong size";
		
	}
	
	
	/**
	 * Returns the String "MessagePartHasWrongSizeException".
	 * @return "MessagePartHasWrongSizeException"
	 */
	public String toString() {
		
		return "MessagePartHasWrongSizeException";
		
	}
	
}