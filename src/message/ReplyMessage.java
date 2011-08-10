package message;


import userDatabase.User;


/**
 * <code>ExternalMessage</code> used to send data to a <code>Client</code>.
 * 
 * @author Karl-Peter Fuchs
 */
public final class ReplyMessage extends Message implements Reply, 
		ExternalMessage {
		
	/** 
	 * Identifier for this type of message. Necessary since messages are 
	 * transmitted as byte streams which don't support the 
	 * <code>instanceOf</code> operator.
	 */
	public static final byte IDENTIFIER = (byte)200;
	

	/**
	 * Constructs a new <code>ReplyMessage</code> with the submitted content 
	 * (<code>byteMesssage</code>) for the bypassed user/channel.
	 * 
	 * @param byteMesssage	Byte representation of this message.
	 * @param user			User, this message belongs to.
	 */
	public ReplyMessage(	byte[] byteMesssage,
							User user
							) {
		
		super(	byteMesssage, 
				user, 
				0, 
				byteMesssage.length
				);
		
		super.setPayloadRange(0, byteMesssage.length);
		
	}
	
	
	/**
	 * Returns the message id -2222 (all <code>ReplyMessage</code> s have the 
	 * same id).
	 * 
	 * @return	-2222.
	 */
	@Override
	public int getMessageID() {
		
		return -2222;
		
	}
	
	
	/**
	 * Returns a String representation of this class.
	 * 
	 * @return	A String representation of this class.
	 */
	@Override
	public String toString() {

		return "CONTENT: " +new String(super.getByteMessage()) +"\n";
		
	}
	
}
