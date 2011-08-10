package architectureInterface;


/**
 * Architecture interface for component <code>KeyGenerator</code>. 
 * <p>
 * Used to generate cryptographic keys.
 * 
 * @author Karl-Peter Fuchs
 */
public interface KeyGeneratorInterface {
	
	
	/**
	 * Must generate a cryptographic keys of the specified type. The 
	 * implementing component must provide <code>public</code> constants 
	 * (=identifiers) for each type of key.
	 * 
	 * @param identifier	Type of key to be generated.
	 * 
	 * @return				The generated key.
	 */
	public Object generateKey(int identifier); 

}
