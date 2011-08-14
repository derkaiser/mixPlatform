package accessControl;


import message.Message;

import architectureInterface.AccessControlInterface;


/**
 * Controller class of component <code>AccessControl</code>. Implements  
 * the architecture interface <code>AccessControlInterface</code>.
 * <p>
 * Used for an integrity check, based on message authentication codes (MAC).
 * 
 * @author Karl-Peter Fuchs
 */
public class AccessControlController implements AccessControlInterface {

	
	/**
	 * Generates a new <code>AccessControl</code> component which can be used 
	 * for an integrity check, based on message authentication codes (MAC).
	 * <p>
	 * Empty constructor.
	 */
	public AccessControlController() {
		
		
	}
	
	
	/**
	 * Initializes the this component.
	 * <p>
	 * Empty initializer.
	 */
	public void initialize() {
		
		
	}
	
	
	/**
	 * Performs an integrity check on the bypassed message.
	 * @param message The message to be checked.
	 * @return Result of integrity check.
	 */
	@Override
	public boolean isMACCorrect(Message message) {

		return IntegrityCheck.isMACCorrect(message);
		
	}

}
