package message;


import internalInformationPort.InternalInformationPortController;

import java.util.Arrays;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import exception.MessagePartHasWrongSizeException;

import userDatabase.User;

import util.Util;


/**
 * <code>ExternalMessage</code> used to establish a channel, which can transmit 
 * <code>ChannelMessage</code>s and <code>Reply</code>ies. The parts this 
 * message consists of are defined in the enumeration 
 * <code>ChannelEstablishMessagePart</code>. A pair of set and get methods 
 * is present for each defined part.
 * 
 * @author Karl-Peter Fuchs
 */
public final class ChannelEstablishMessage extends Message implements Request, 
		ExternalMessage {
		
	/** 
	 * Reference on component <code>InternalInformationPort</code>. 
	 * Used to display and/or log data and read general settings.
	 */
	private static InternalInformationPortController internalInformationPort = 
		new InternalInformationPortController();
	
	/** Logger used to log and display information. */
	private final static Logger LOGGER = internalInformationPort.getLogger();
	
	/** 
	 * Identifier for this type of message. Necessary since messages are 
	 * transmitted as byte streams which don't support the 
	 * <code>instanceOf</code> operator.
	 */
	public static final byte IDENTIFIER = (byte)50;
	
	/** 
	 * Number of further hops (= mixes), this message must pass to reach its 
	 * destination. Used to find a <code>MessagePart</code>'s position in the 
	 * <code>byteMessage</code> which has (a) dynamic start- and/or 
	 * end-position(s). Note: Message length isn't constant in a cascade.
	 * 
	 * @see Message#byteMessage
	 * @see ChannelEstablishMessagePart
	 */
	private final int NUMBER_OF_FURTHER_HOPS;
	
	
	/**
	 * Message constructor used by a mix.
	 * <p>
	 * Constructs a new <code>ChannelEstablishMessage</code> with the submitted 
	 * content (<code>byteMesssage</code>) for the specified user/channel.
	 * 
	 * @param byteMesssage			Byte representation of the message (as 
	 * 								received from the client/previous mix).
	 * @param channel				User/channel the message shall be created 
	 * 								for.
	 * @param numberOfFurtherHops	Number of further hops (= mixes), this 
	 * 								message must pass.
	 */
	public ChannelEstablishMessage(	byte[] byteMesssage,
										User channel,
										int numberOfFurtherHops
										) {
		
		super(	byteMesssage, 
				channel,
				ChannelEstablishMessagePart.PAYLOAD.
						getStartPosition(numberOfFurtherHops),
				ChannelEstablishMessagePart.PAYLOAD.
						getLength(numberOfFurtherHops)
				);
		
		this.NUMBER_OF_FURTHER_HOPS = numberOfFurtherHops;
		
	}
	
	
	/**
	 * Message constructor used by client.
	 * <p>
	 * Constructs a new <code>ChannelEstablishMessage</code> with the submitted 
	 * content (<code>byteMesssage</code>).
	 * 
	 * @param byteMesssage			Byte representation of the message (can be 
	 * 								empty).
	 * @param numberOfFurtherHops	Number of mixes in the cascade.
	 */
	public ChannelEstablishMessage(	byte[] byteMesssage,
									int numberOfFurtherHops
									) {

		super(byteMesssage);
		this.NUMBER_OF_FURTHER_HOPS = numberOfFurtherHops;
	
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>LEADING_ZERO</code>.
	 * 
	 * @return <code>MessagePart</code> <code>LEADING_ZERO</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#LEADING_ZERO
	 */
	public byte getLeadingZero() {
		
		return getPart(ChannelEstablishMessagePart.LEADING_ZERO)[0];
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>LEADING_ZERO</code> to <code>
	 * 01000000</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#LEADING_ZERO
	 */
	public void setLeadingZero() {
		
		try {
			
			byte[] leadingZero = new byte[1];
			leadingZero[0] = (byte)64; // 01000000 see MessagePart#LEADING_ZERO 
			   						   // for further information
			
			setPart(ChannelEstablishMessagePart.LEADING_ZERO, leadingZero);
			
		} catch (MessagePartHasWrongSizeException e) {
			
			LOGGER.warning(e.getMessage());
			
		}
		
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>MAC</code> = The Message 
	 * Authentication Code (MAC) of this message.
	 * 
	 * @return <code>MessagePart</code> <code>MAC</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#MAC
	 */
	public byte[] getMAC() {
		
		return getPart(ChannelEstablishMessagePart.MAC);
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>MAC</code> to the 
	 * bypassed value.
	 * 
	 * @param mac The Message Authentication Code (MAC) of this message.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#MAC
	 */
	public void setMAC(byte[] mac) throws MessagePartHasWrongSizeException {
		
		setPart(ChannelEstablishMessagePart.MAC, mac);
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>MESSAGE_ID</code> = An 
	 * identifieing number, which is the same for every envelope of a mix 
	 * message. Used to trace messages for debugging. Can only be read 
	 * correctly, when current envelope is decrypted! Note: For testing only 
	 * (MAKES MIX INSECURE)!
	 * 
	 * @return <code>MessagePart</code> <code>MESSAGE_ID</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#MESSAGE_ID
	 */
	@Override
	public int getMessageID() {
		
		return Util.byteArrayToInt(
					getPart(ChannelEstablishMessagePart.MESSAGE_ID)
					);
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>MESSAGE_ID</code> to the 
	 * bypassed value.
	 * 
	 * @param newID	An identifieing number, which is the same for every 
	 * 				envelope of a mix message. Used to trace messages for 
	 * 				debugging. Can only be set correctly, when current envelope 
	 * 				is decrypted! Note: For testing only (MAKES MIX INSECURE)!
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#MESSAGE_ID
	 */
	@Override
	public void setMessageID(int newID) {
		
		try {
			
			setPart(	ChannelEstablishMessagePart.MESSAGE_ID,
						Util.intToByteArray(newID)
						);
			
		} catch (MessagePartHasWrongSizeException e) {

			LOGGER.warning(e.getMessage());
			
		}
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>MAC_KEY</code> = Key used to 
	 * generate Message Authentication Code (MAC).
	 * 
	 * @return <code>MessagePart</code> <code>MAC_KEY</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#MAC_KEY
	 */
	public SecretKey getMACKey() {
		
		String macAlgorithm = 
			internalInformationPort.getProperty("MAC_ALGORITHM");
		
		SecretKeySpec keyAsObject = 
			new SecretKeySpec(
					getPart(ChannelEstablishMessagePart.MAC_KEY),
					macAlgorithm
					);
		
		return keyAsObject;
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>MAC_KEY</code> to the 
	 * bypassed value.
	 * 
	 * @param key	Key used to generate Message Authentication Code (MAC).
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#MAC_KEY
	 */
	public void setMACKey(SecretKey key) {

		try {
			
			byte[] macKey = key.getEncoded();
			setPart(ChannelEstablishMessagePart.MAC_KEY, macKey);
			
		} catch (MessagePartHasWrongSizeException e) {

			LOGGER.warning(e.getMessage());
			
		}
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>SYMMETRIC_KEY</code> = Key 
	 * used to decrypt the symmetrically encrypted message part.
	 * 
	 * @return <code>MessagePart</code> <code>SYMMETRIC_KEY</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SESSION_KEY
	 */
	public SecretKeySpec getSessionKey() {
		
		String symmetricAlgorithm = 
			internalInformationPort.getProperty("NAME_OF_SYM_KEY_GENERATOR");
		
		byte[] symmetricKeyAsByteArray = 
			getPart(ChannelEstablishMessagePart.SESSION_KEY);
		
		return new SecretKeySpec(symmetricKeyAsByteArray, symmetricAlgorithm);
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>SYMMETRIC_KEY</code> to the 
	 * bypassed value.
	 * 
	 * @param key	Key used to decrypt the symmetrically encrypted message 
	 * 				part.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SESSION_KEY
	 */
	public void setSessionKey(SecretKey key) {
			
		try {
			
			byte[] keyAsByteArray = key.getEncoded();
			
			setPart(	ChannelEstablishMessagePart.SESSION_KEY,
						keyAsByteArray
						);
			
		} catch (MessagePartHasWrongSizeException e) {

			LOGGER.warning(e.getMessage());
			
		}
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>SYMMETRIC_IV</code> = 
	 * Initialization vector used to decrypt the symmetrically encryptet 
	 * message part.
	 * 
	 * @return <code>MessagePart</code> <code>SYMMETRIC_IV</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SESSION_IV
	 */
	public IvParameterSpec getSessionIV() {
		
		byte[] symmetricIvAsByteArray = 
			getPart(ChannelEstablishMessagePart.SESSION_IV);
		
		return new IvParameterSpec(symmetricIvAsByteArray);
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>SYMMETRIC_IV</code> to the 
	 * bypassed value.
	 * 
	 * @param sessionIV	Initialization vector used to decrypt the symmetrically 
	 * 					encryptet message part.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SESSION_IV
	 */
	public void setSessionIV(byte[] sessionIV) {
			
		try {

			setPart(ChannelEstablishMessagePart.SESSION_IV, sessionIV);
			
		} catch (MessagePartHasWrongSizeException e) {

			LOGGER.warning(e.getMessage());
			
		}
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>TIMESTAMP</code> = Point of 
	 * time, the message was created.
	 * 
	 * @return <code>MessagePart</code> <code>TIMESTAMP</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#TIMESTAMP
	 */
	public long getTimestamp() {
		
		byte[] timestampAsByteArray = 
			getPart(ChannelEstablishMessagePart.TIMESTAMP);
		
		return Util.byteArrayToLong(timestampAsByteArray);
		
	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>TIMESTAMP</code> to the 
	 * bypassed value.
	 * 
	 * @param newTimestamp	Point of time, the message was created.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#TIMESTAMP
	 */
	public void setTimestamp(long newTimestamp) {
			
		try {
			
			byte[] timestampAsByteArray = Util.longToByteArray(newTimestamp);
			
			setPart(	ChannelEstablishMessagePart.TIMESTAMP, 
						timestampAsByteArray
						);
			
		} catch (MessagePartHasWrongSizeException e) {

			LOGGER.warning(e.getMessage());
			
		}
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>PAYLOAD</code>.
	 * 
	 * @return <code>MessagePart</code> <code>PAYLOAD</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#PAYLOAD
	 */
	public Payload getPayload() {
		
		return new Payload(getPart(ChannelEstablishMessagePart.PAYLOAD));

	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>PAYLOAD</code> to the 
	 * bypassed value.
	 * 
	 * @param newPayload	Payload to be set.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#PAYLOAD
	 */
	public void setPayload(Payload newPayload) throws 
			MessagePartHasWrongSizeException {
			
		setPart(	ChannelEstablishMessagePart.PAYLOAD, 
					newPayload.getBytePayload()
					);
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>SIGNED_DATA</code>.
	 * 
	 * @return <code>MessagePart</code> <code>SIGNED_DATA</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SIGNED_DATA
	 */
	public byte[] getSignedData() {
		
		return getPart(ChannelEstablishMessagePart.SIGNED_DATA);

	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>SIGNED_DATA</code> to the 
	 * bypassed value.
	 * 
	 * @param newSignedData	The signed data to be set.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SIGNED_DATA
	 */
	public void setSignedData(byte[] newSignedData) throws 
			MessagePartHasWrongSizeException {
			
		setPart(ChannelEstablishMessagePart.SIGNED_DATA, newSignedData);
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>ASYMMETRIC_PART</code> = The 
	 * asymmetrically encrypted part of the message.
	 * 
	 * @return <code>MessagePart</code> <code>ASYMMETRIC_PART</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#ASYMMETRIC_PART
	 */
	public byte[] getAsymmetricPart() {
		
		return getPart(ChannelEstablishMessagePart.ASYMMETRIC_PART);

	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>ASYMMETRIC_PART</code> to the 
	 * bypassed value.
	 * 
	 * @param newAsymmetricPart	The asymmetric part of the message to be set.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#ASYMMETRIC_PART
	 */
	public void setAsymmetricPart(byte[] newAsymmetricPart) throws 
			MessagePartHasWrongSizeException {
			
		setPart(ChannelEstablishMessagePart.ASYMMETRIC_PART, newAsymmetricPart);
		
	}
	
	
	/**
	 * Returns the <code>MessagePart</code> <code>SYMMETRIC_PART</code> = The 
	 * symmetrically encrypted part of the message.
	 * 
	 * @return <code>MessagePart</code> <code>SYMMETRIC_PART</code>.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SYMMETRIC_PART
	 */
	public byte[] getSymmetricPart() {
		
		return getPart(ChannelEstablishMessagePart.SYMMETRIC_PART);

	}
	
	
	/**
	 * Sets the <code>MessagePart</code> <code>SYMMETRIC_PART</code> to the 
	 * bypassed value.
	 * 
	 * @param newSymmetricPart	The symmetric part of the message to be set.
	 * 
	 * @see ChannelEstablishMessagePart
	 * @see ChannelEstablishMessagePart#SYMMETRIC_PART
	 */
	public void setSymmetricPart(byte[] newSymmetricPart) throws 
			MessagePartHasWrongSizeException {
			
		setPart(ChannelEstablishMessagePart.SYMMETRIC_PART, newSymmetricPart);
		
	}

	
	/**
	 * Returns a byte representation of a <code>MessagePart</code>. Example of 
	 * usage:
	 * <code>getPart(ChannelEstablishMessagePart.NAME_OF_ENUM_CONSTANT);</code>
	 * 
	 * @param messagePart	Enum constant who's byte representation shall be 
	 * 						retrieved (= "type" of the part).
	 * @return				Byte representation of the <code>MessagePart</code> 
	 * 						suiting the bypassed enum constant.
	 * 
	 * @see ChannelEstablishMessagePart
	 */
	private byte[] getPart(ChannelEstablishMessagePart messagePart) {
			/* 
			 * MessagePart is used to find the correct positions in 
			 * byteMessage.
			 */

		// return the corresponding part of the byte message
		return Arrays.copyOfRange(
				super.getByteMessage(), 
				messagePart.getStartPosition(NUMBER_OF_FURTHER_HOPS),
				messagePart.getEndPosition(NUMBER_OF_FURTHER_HOPS) + 1 
				);
			

	}
	
	

	/**
	 * Saves a byte representation of a <code>MessagePart</code>. Example of 
	 * usage: 
	 * <code>
	 * setPart(ChannelEstablishMessagePart.NAME_OF_ENUM_CONSTANT, data);
	 * </code>
	 * 
	 * @param messagePart	Enum constant who's byte representation shall be 
	 * 						saved.
	 * @param data			Byte representation of the <code>MessagePart</code> 
	 * 						to be saved.
	 * @exception MessagePartHasWrongSizeException Thrown when a <code>
	 * 						MessagePart</code> that shall be assigned is of 
	 * 						wrong size.
	 * 
	 * @see ChannelEstablishMessagePart
	 */
	private void setPart(	ChannelEstablishMessagePart messagePart,
							byte[] data
							) throws MessagePartHasWrongSizeException {
			/* 
			 * MessagePart is used to find the correct positions in 
			 * byteMessage.
			 */		

		if (data.length != messagePart.getLength(NUMBER_OF_FURTHER_HOPS)) {
			
			System.err.println("data.length: " +data.length);
			System.err.println("messagePart.getLength(NUMBER_OF_FURTHER_HOPS): " +messagePart.getLength(NUMBER_OF_FURTHER_HOPS));
			throw new MessagePartHasWrongSizeException();
			
		}
		
		// copy submitted data to byteMessage array
		int startPosition 
			= messagePart.getStartPosition(NUMBER_OF_FURTHER_HOPS);

		for (int i=0; i<data.length; i++) {
			
			super.getByteMessage()[startPosition+i] = data[i];
			
		}
		
	}

	
	/**
	 * Returns a simple String representation of this class featuring several 
	 * message parts. Note: Output is only correct for decrypted messages.
	 * 
	 * @return	A simple String representation of this class featuring several 
	 * 			message parts.
	 */
	@Override
	public String toString() {
		
		String output = "";
		output += "MESSAGE_ID: " +getMessageID() +"\n";
		output += "MAC: " +new String(getMAC()) +"\n";
		output += "MAC_KEY: " +new String(getMACKey().getEncoded()) +"\n";
		
		output += 
			"SYMMETRIC_KEY: " +new String(getSessionKey().getEncoded()) +"\n";
		
		output += "TIMESTAMP: " +getTimestamp() +"\n";
		output += "PAYLOAD: " +new String(getPayload().getBytePayload()) +"\n";

		return output;
	}

}
