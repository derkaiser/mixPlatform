package keyGenerator;


import architectureInterface.KeyGeneratorInterface;


/**
 * Controller class of component <code>KeyGenerator</code>. Implements the 
 * architecture interface <code>KeyGeneratorInterface</code>.
 * <p>
 * Used to generate cryptographic keys. A class constant with 
 * <code>public</code> accessibility is available for each supported type of 
 * key.
 * <p>
 * Example of usage: 
 * <p>
 * <code>
 * KeyGeneratorController kgc = new KeyGeneratorController();
 * KeyPair kp = (KeyPair) kgc.generateKey(KeyGeneratorController.KEY_PAIR);
 * </code>
 * 
 * @author Karl-Peter Fuchs
 */
public class KeyGeneratorController implements KeyGeneratorInterface {

	/** 
	 * <code>SecretKey</code> used to encrypt (header) data between two mixes. 
	 * Key size and type are specified in the property file.
	 */
	public static final int INTER_MIX_KEY = 1;
	
	/** 
	 * Initialization vector (<code>IvParameterSpec</code>) used to encrypt 
	 * (header) data between two mixes.
	 */
	public static final int INTER_MIX_IV = 2;
	
	/** 
	 * <code>KeyPair</code> (public key and a private key) used for asymmetric 
	 * cryptography. Key size and type are specified in the property file.
	 */
	public static final int KEY_PAIR = 3;
	
	
	/**
	 * Generates a new <code>KeyGenerator</code> component, used to generate 
	 * cryptographic keys. A class constant with <code>public</code> 
	 * accessibility is available for each supported type of key.
	 */
	public KeyGeneratorController() {

	}
	
	
	/**
	 * Initializes the this component.
	 */
	public void initialize() {
		
	}
	
	
	/**
	 * Generates a cryptographic key according to the bypassed identifier.
	 * 
	 * @param identifier	Type of key to be generated (see class constants).
	 * 
	 * @return				The generated cryptographic key.
	 */
	@Override
	public Object generateKey(int identifier) {
		
		switch (identifier) {
		
			case INTER_MIX_KEY:
				return KeyGen.generateInterMixKey();
			
			case INTER_MIX_IV:
				return KeyGen.generateInterMixIV();
				
			case KEY_PAIR:
				return KeyGen.generateKeyPair();
				
			default:
				return null;
			
		}	
		
	}
	
}
