package architectureInterface;


import java.util.Collection;

import exception.UserAlreadyExistingException;
import exception.UnknownUserException;

import userDatabase.User;


/**
 * Architecture interface for component <code>UserDatabase</code>. 
 * <p>
 * Used to store user-specific data (e. g. identifiers, session keys and 
 * buffers).
 * <p>
 * Must be thread-safe.
 * 
 * @author Karl-Peter Fuchs
 */
public interface UserDatabaseInterface {

	
	/**
	 * Must add the bypassed <code>User</code> to the internal database.
	 * 
	 * @param user	The <code>User</code> to be added. 
	 * 
	 * @throws UserAlreadyExistingException	Thrown when the bypassed <code>
	 * 										User</code> has already been added 
	 * 										(user's identifier already in use).
	 */
	public void addUser(User user) 
		throws UserAlreadyExistingException;
	
	
	/**
	 * Must remove the <code>User</code> with the bypassed identifier.
	 * 
	 * @param identifier	Identifier of the <code>User</code> to be removed 
	 * 						from the internal database.
	 *  
	 * @throws UnknownUserException		Thrown when no <code>User</code> with 
	 * 									the bypassed identifier is existent.
	 */
	public void removeUser(int identifier) throws UnknownUserException;
	
	
	/**
	 * Must return the <code>User</code> with the bypassed identifier.
	 * 
	 * @param identifier	Identifier of the <code>User</code> to be returned.
	 *  
	 * @return				<code>User</code> with the bypassed identifier.
	 * 
	 * @throws UnknownUserException		Thrown when no <code>User</code> with 
	 * 									the bypassed identifier is existent.
	 */
	public User getUser(int identifier) throws UnknownUserException;
	
	
	
	/**
	 * Must return the <code>User</code> with the bypassed identifier.
	 * 
	 * @param nextMixIdentifier	Identifier of the <code>User</code> to be 
	 * 							returned.
	 *  
	 * @return					<code>User</code> with the bypassed identifier.
	 * 
	 * @throws UnknownUserException		Thrown when no <code>User</code> with 
	 * 									the bypassed identifier is existent.
	 */
	public User getUserByNextMixIdentifier(int nextMixIdentifier) throws 
			UnknownUserException;
	
	
	/**
	 * Must return whether a <code>User</code> with the bypassed identifier is 
	 * present in the internal database or not.
	 * 
	 * @param identifier	Identifier to search for.
	 * 
	 * @return	<code>User</code> present or not.
	 */
	public boolean isExistingUser(int identifier);
	
	
	/**
	 * Must return the number of <code>User</code>s currently stored in the 
	 * internal database.
	 * 
	 * @return	Number of <code>User</code>s currently stored in the internal 
	 * 			database.
	 */
	public int getSize();
	
	
	/**
	 * Must return all <code>User</code>s currently active.
	 * 
	 * @return	Collection of all <code>User</code>s currently active.
	 */
	public Collection<User> getActiveUsers();
	
}
