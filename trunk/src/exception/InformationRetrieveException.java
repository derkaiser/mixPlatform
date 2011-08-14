package exception;


/**
 * Exception thrown by <code>InfromationGrabber</code> when a requested 
 * <code>Information</code> could not be retrieved.
 * 
 * @see externalInformationPort
 * 
 * @author Karl-Peter Fuchs
 */
public final class InformationRetrieveException extends Exception {
	
	/** The serialVersionUID as identifier for this serializable class. */
	private static final long serialVersionUID = 124321567889899458L;
	
	
	/**
	 * Constructs an InvalidPortException (empty constructor).
	 */
	public InformationRetrieveException() {
		
	}
	
	
	/**
	 * Returns the String "The requested information could not be retrieved!"
	 * @return "The requested information could not be retrieved!".
	 */
	public String getMessage() {
		
		return 	"The requested information could not be retrieved!";
		
	}
	
	
	/**
	 * Returns the String "InformationNotAvailableException".
	 * @return "InformationNotAvailableException"
	 */
	public String toString() {
		
		return "InformationRetrieveException";
		
	}
	
}
