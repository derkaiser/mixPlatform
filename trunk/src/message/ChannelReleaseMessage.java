package message;


import userDatabase.User;


/**
 * <code>ExternalMessage</code> used to release an existing channel.
 * 
 * @author Karl-Peter Fuchs
 */
public final class ChannelReleaseMessage extends Message implements Request, 
		ExternalMessage {
		
	/** 
	 * Identifier for this type of message. Necessary since messages are 
	 * transmitted as byte streams which don't support the 
	 * <code>instanceOf</code> operator.
	 */
	public static final byte IDENTIFIER = (byte)150;
	
	
	/**
	 * Constructs a new <code>ChannelReleaseMessage</code> for the specified 
	 * user/channel.
	 * 
	 * @param channel				User/channel the message shall be created 
	 * 								for.
	 */
	public ChannelReleaseMessage(User channel) {
		
		super(new byte[0], channel, 0, 0);
		
	}


	/**
	 * Returns a simple String representation of this object.
	 * 
	 * @return	A simple String representation of this object.
	 */
	@Override
	public String toString() {

		return "ChannelReleaseMessage (no data).\n";
		
	}
	
	
	/**
	 * Returns the message id -1111 (all <code>ChannelReleaseMessages</code> 
	 * have the same id).
	 * 
	 * @return	-1111.
	 */
	@Override
	public int getMessageID() {
		
		return -1111;
		
	}
	
}
