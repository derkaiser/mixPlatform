package exception;


/**
 * Exception thrown by <code>UserDatabase.addUser()</code> when the 
 * identifier of the user to be added is already in use by another user.
 * 
 * @see userDatabase
 * 
 * @author Karl-Peter Fuchs
 */
public final class UserAlreadyExistingException extends Exception {
	
	/** The serialVersionUID as identifier for this serializable class. */
	private static final long serialVersionUID = 1242342345678899458L;
	
	
	/**
	 * Constructs an InvalidPortException (empty constructor).
	 */
	public UserAlreadyExistingException() {
		
	}
	
	
	/**
	 * Returns the String "This user's identifier is already in use by 
	 * another user!"
	 * @return 	"This user's identifier is already in use by another 
	 * 			user!".
	 */
	public String getMessage() {
		
		return 	"This user's identifier is already in use by another "
				+"user!";
		
	}
	
	
	/**
	 * Returns the String "UserAlreadyExistingException".
	 * @return "UserAlreadyExistingException"
	 */
	public String toString() {
		
		return "UserAlreadyExistingException";
		
	}
	
}