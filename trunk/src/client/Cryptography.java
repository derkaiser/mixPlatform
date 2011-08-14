package client;


import internalInformationPort.InternalInformationPortController;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import message.ChannelEstablishMessage;
import message.ChannelMessage;
import message.Message;

import exception.MessagePartHasWrongSizeException;


/**
 * Provides methods for de- and encrypting messages.
 * 
 * @author Karl-Peter Fuchs
 */
final class Cryptography {

	/** 
	 * Reference on component <code>InternalInformationPortController</code>. 
	 * Used to display and/or log data and read general settings.
	 */
	private static InternalInformationPortController internalInformationPort = 
		new InternalInformationPortController();
	
	/** Logger used to log and display information. */
	private final static Logger LOGGER = internalInformationPort.getLogger();
	
	/** Number of mixes in the cascade. */
	private final int NUMBER_OF_MIXES_IN_CASCADE;
	
	/** Cipher for asymmetric cryptography. */
	private Cipher asymmetricCipher;
	
	/** Key generator for symmetric cryptography and hash algorithm. */
	private KeyGenerator keyGenerator;
	
	/** 
	 * Public keys of the cascade's mixes. The first mix' key is found at 
	 * <code>PUBLIC_KEYS_OF_MIXES[0]</code>.
	 */
	private final Key[] PUBLIC_KEYS_OF_MIXES;
	
	/** 
	 * Session keys for the cascade's mixes. The key for the first mix is found 
	 * at <code>sessionKeys[0]</code>.
	 */
	private SecretKey[] sessionKeys;
	
	/** 
	 * Initialization vectors for the ciphers for each of the cascade's mixes. 
	 * The initialization vector for the first mix is found at 
	 * <code>sessionIVs[0]</code>.
	 * 
	 * @see #symmetricEncryptCiphers
	 * @see #symmetricDecryptCiphers
	 */
	private IvParameterSpec[] sessionIVs;
	
	/** 
	 * Session (encrypt) ciphers for each of the cascade's mixes. 
	 * The cipher for the first mix is found at 
	 * <code>symmetricEncryptCiphers[0]</code>.
	 */
	private Cipher[] symmetricEncryptCiphers;
	
	/** 
	 * Session (decrypt) ciphers for each of the cascade's mixes. 
	 * The cipher for the first mix is found at 
	 * <code>symmetricEncryptCiphers[0]</code>.
	 */
	private Cipher[] symmetricDecryptCiphers;
	
	
	/**
	 * Generates a new <code>Cryptography</code> object that can be used to de- 
	 * and encrypting messages (using the bypassed keys).
	 * 
	 * @param	publicKeysOfMixes	Public keys of the cascade's mixes. The 
	 * 								first mix' key must be located at 
	 * 								<code>publicKeysOfMixes[0]</code>.
	 */
	protected Cryptography(Key[] publicKeysOfMixes) {
		
		this.PUBLIC_KEYS_OF_MIXES = publicKeysOfMixes;
		this.NUMBER_OF_MIXES_IN_CASCADE = publicKeysOfMixes.length;
		
		try { // get asymmetric Cipher and KeyGenerator
			
			asymmetricCipher = 
				Cipher.getInstance(	getProperty("ASYM_CRYPTOGRAPHY_ALGORITHM"), 
									getProperty("CRYPTO_PROVIDER")
									);
			
			keyGenerator = KeyGenerator.getInstance(
					getProperty("NAME_OF_SYM_KEY_GENERATOR"), 
					getProperty("CRYPTO_PROVIDER")
					);
			
			keyGenerator.init(new Integer(getProperty("SYM_KEY_LENGTH")));
			
			this.sessionKeys = new SecretKey[NUMBER_OF_MIXES_IN_CASCADE];
			this.sessionIVs = new IvParameterSpec[NUMBER_OF_MIXES_IN_CASCADE];
			
			this.symmetricEncryptCiphers = 
				new Cipher[NUMBER_OF_MIXES_IN_CASCADE];
			
			this.symmetricDecryptCiphers = 
				new Cipher[NUMBER_OF_MIXES_IN_CASCADE];
			
			// instantiate and initialize ciphers; generate keys
			for (int i=0; i<NUMBER_OF_MIXES_IN_CASCADE; i++) {
				
				symmetricEncryptCiphers[i] = 
					Cipher.getInstance(
							getProperty("SYM_CRYPTOGRAPHY_ALGORITHM"), 
							getProperty("CRYPTO_PROVIDER")
							);
				
				symmetricDecryptCiphers[i] = 
					Cipher.getInstance(
						getProperty("SYM_CRYPTOGRAPHY_ALGORITHM"), 
						getProperty("CRYPTO_PROVIDER")
						);
				
				
				
				sessionKeys[i] = keyGenerator.generateKey();
				
				symmetricEncryptCiphers[i].init(	Cipher.ENCRYPT_MODE,
													sessionKeys[i]
													);
				
				sessionIVs[i] = 
					new IvParameterSpec(symmetricEncryptCiphers[i].getIV());
				
				symmetricDecryptCiphers[i].init(	Cipher.DECRYPT_MODE, 
													sessionKeys[i], 
													sessionIVs[i]
													);
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			
			LOGGER.severe(	"Invalid \"ASYM_CRYPTOGRAPHY_ALGORITHM\" " 
							+"specified in property file!" 
							+e.getMessage()
							);
			
			System.exit(1);
			
		} catch (NoSuchPaddingException e) {
			
			LOGGER.severe(	"Invalid padding mechanism specified in property "
							+"file!"
							+e.getMessage()
							);
	
			System.exit(1);
			
		} catch (NoSuchProviderException e) {
			
			LOGGER.severe(	"Invalid \"CRYPTO_PROVIDER\" " 
							+"specified in property file!" 
							+e.getMessage()
							);
	
			System.exit(1);
			
		} catch (InvalidKeyException e) {
			
			LOGGER.severe(	"(Client) Couldn't generate symmetric key!" 
							+e.getMessage()
							);
	
			System.exit(1);
			
		} catch (InvalidAlgorithmParameterException e) {
			
			LOGGER.severe(	"(Client) Couldn't init cipher!" 
							+e.getMessage()
							);

			System.exit(1);
			
		}
		
	}
	
	
	/**
	 * Returns the specified mix' session key.
	 * 
	 * @param	positionOfMixInCascade	The mix' position in the cascade, who's 
	 * 									session key shall be returned.
	 * 
	 * @return	The specified mix' session key.
	 * 
	 */
	protected SecretKey getSessionKeyOfMix(int positionOfMixInCascade) {
		
		return sessionKeys[positionOfMixInCascade - 1];
		
	}
	
	
	/**
	 * Returns the specified mix' initialization vector.
	 * 
	 * @param	positionOfMixInCascade	The mix' position in the cascade, who's 
	 * 									initialization vector shall be returned.
	 * 
	 * @return	The specified mix' initialization vector.
	 * 
	 */
	protected IvParameterSpec getSessionIVOfMix(int positionOfMixInCascade) {
		
		return sessionIVs[positionOfMixInCascade - 1];
		
	}
	
	
	/**
	 * Encrypts the bypassed message (hybridly) for the specified mix. 
	 * 
	 * @param positionOfMixInCascade	The mix' position in the cascade, the
	 * 									message shall be encrypted for.
	 * @param plaintextMessage			The message to be encrypted.	
	 * 	
	 * @return							The (hybridly) encrypted message.
	 */
	protected Message encryptMessage(	Message plaintextMessage, 
										int positionOfMixInCascade) {
		
		if (plaintextMessage instanceof ChannelEstablishMessage) {
			// encrypt message hybridly
			
			ChannelEstablishMessage message = 
				(ChannelEstablishMessage)plaintextMessage;
			
			try {
				
				// asymmetric part
				
				asymmetricCipher.init(
						Cipher.ENCRYPT_MODE,
						PUBLIC_KEYS_OF_MIXES[positionOfMixInCascade - 1]
						);
				
				byte[] asymmetricCiphertext = 
					asymmetricCipher.doFinal(message.getAsymmetricPart());
				
				// symmetric part
				Cipher cipher = 
					symmetricEncryptCiphers[positionOfMixInCascade - 1];
					
				byte[] symmetricCiphertext = 
					cipher.update(message.getSymmetricPart());
				
				// save both parts
				message.setAsymmetricPart(asymmetricCiphertext);
				message.setSymmetricPart(symmetricCiphertext);
				
			} catch (InvalidKeyException e) {
				
				LOGGER.severe(	"Invalid public key!"
								+e.getMessage()
								);
				
				System.exit(1);
				
			} catch (IllegalBlockSizeException e) {
				
				LOGGER.severe(	"Invalid block size!"
								+e.getMessage()
								);
				
				System.exit(1);
				
			} catch (BadPaddingException e) {
				
				LOGGER.severe(	"Invalid padding mechanism specified in "
								+"property file!"
								+e.getMessage()
								);
				
				System.exit(1);
				
			} catch (MessagePartHasWrongSizeException e) {
				
				LOGGER.severe(e.getMessage());
				System.exit(1);
				
			}

		   return message;
			
		} else { // ForwardChannelMessage -> encrypt message symmetrically
			
			ChannelMessage message = 
				(ChannelMessage)plaintextMessage;
			
			Cipher cipher = symmetricEncryptCiphers[positionOfMixInCascade - 1];
				
			byte[] symmetricCiphertext = 
				cipher.update(message.getByteMessage());
				
			message.setByteMessage(symmetricCiphertext);
				
			return message;
		   
		}
  
	}
	
	
	/**
	 * Decrypts the bypassed reply. 
	 * 
	 * @param reply	The reply to be decrypted.		
	 * 
	 * @return		The decrypted reply.
	 */
	protected byte[] decryptReply(byte[] reply) {

		for (int i=0; i<NUMBER_OF_MIXES_IN_CASCADE; i++) {
			
			Cipher cipher = symmetricDecryptCiphers[i];
			reply = cipher.update(reply);
			
		}

		return reply;
		
	}
	
	
	/**
	 * Simply used to shorten method calls (calls 
	 * <code>internalInformationPort.getProperty(key)</code>). Returns the 
	 * property with the specified key from the property file.
	 * 
	 * @param key	The property key.
	 * 
	 * @return		The property with the specified key in the property file.
	 */
	private static String getProperty(String key) {
		
		return internalInformationPort.getProperty(key);
		
	}
	
}
