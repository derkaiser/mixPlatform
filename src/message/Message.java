package message;


import networkClock.NetworkClockController;

import userDatabase.User;


/**
 * Basic <code>abstract</code> class for all types of messages the 
 * <code>InputOutputHandler</code> is capable of transmitting.
 * <p>
 * Provides a byte array containing the message itself in an exchangeable form 
 * (see <code>byteMessage</code>) and several manipulable properties for 
 * internal usage only (e. g. by a mix or client).
 * 
 * @author Karl-Peter Fuchs
 */
public abstract class Message implements BasicMessage, Comparable<Message> {
	/*
	 * The message itself is saved in the byte[] byteMessage. A truly object 
	 * oriented approach with a serializable message class (including variables 
	 * for each message part) was discarted due to its overhead and 
	 * inflexibility (in Java the length of a serialized message depends not 
	 * just on the length of its variables, but also on their quantity and 
	 * type. Therefore a change of the message format would always force a new 
	 * calculation of the message overhead. Otherwise, it can't be assured that 
	 * the transmitted messages are smaller than the network's maximum 
	 * transmission unit).
	 */
	
	/** Identifier of the channel/user this message belongs to. */
	private final int CHANNEL_ID;
	
	/** 
	 * Identifier of the channel/user this message belongs to for the next 
	 * mix of the cascade.
	 */
	private final int NEXT_MIX_CHANNEL_ID;
	
	/** Identifier for this message. */
	private int messageID;
	
	/** 
	 * Reference on component <code>NetworkClock</code> (used to set variable 
	 * <code>timeOfReceival</code>).
	 * 
	 * @see #timeOfReceival
	 */
	private static NetworkClockController clock = new NetworkClockController();
	
	/** 
	 * Point of time (timestamp), this message was received by the 
	 * corresponding mix.
	 */
	private long timeOfReceival;
	
	/** 
	 * Only a certain part of <code>byteMessage</code> must be transmitted to 
	 * the cascade's next mix (Some parts (e. g. the session key for the 
	 * corresponding mix) are not supposed to be sent). Which part is relevant 
	 * depends on the type of message.
	 * <p>
	 * This variable defines the offset for the part to be transmitted.
	 * <p>
	 * This variable must/can be set automatically in the constructor of any 
	 * subclass.
	 * 
	 * @see #payloadLength
	 */
	private int startIndexOfPayload;
	
	/** 
	 * Only a certain part of <code>byteMessage</code> must be transmitted to 
	 * the cascade's next mix (Some parts (e. g. the session key for the 
	 * corresponding mix) are not supposed to be sent). Which part is relevant 
	 * depends on the type of message.
	 * <p>
	 * This variable defines the length for the part to be transmitted.
	 * <p>
	 * This variable must/can be set automatically in the constructor of any 
	 * subclass.
	 * 
	 * @see #payloadLength
	 */
	private int payloadLength;
	
	/** Reference on the user/channel. this message belongs to. */
	private User channel;
	
	/** 
	 * Byte representation of this message. Contains all data that was 
	 * received or shall be sent via network.
	 */
	private byte[] byteMessage;
	
	
	/**
	 * Message constructor used by mix.
	 * <p>
	 * Constructs a new <code>Message</code> with the submitted content (<code>
	 * byteMesssage</code>) for the bypassed user/channel.
	 * 
	 * @param byteMesssage			Byte representation of the message (as 
	 * 								received from the client/previous mix).
	 * @param channel				Reference on the user/channel this message 
	 * 								belongs to.
	 * @param startIndexOfPayload	See instance variable 
	 * 								<code>startIndexOfPayload</code>.
	 * @param payloadLength			See instance variable 
	 * 								<code>payloadLength</code>.
	 */
	public Message(	byte[] byteMesssage,
					User channel,
					int startIndexOfPayload,
					int payloadLength
					) {
		
		this.byteMessage = byteMesssage;
		this.startIndexOfPayload = startIndexOfPayload;
		this.payloadLength = payloadLength;
		this.timeOfReceival = clock.getTime();
		this.channel = channel;
		
		if (channel !=  null) {
			
			this.CHANNEL_ID = channel.getIdentifier();
			this.NEXT_MIX_CHANNEL_ID = channel.getIdentifierForNextMix();
			
		} else {
			
			this.CHANNEL_ID = -1;
			this.NEXT_MIX_CHANNEL_ID = -1;
			
		}
		
	}
	
	
	/**
	 * Message constructor used by client.
	 * <p>
	 * Constructs a new <code>Message</code> with the submitted content (<code>
	 * byteMesssage</code>).
	 * 
	 * @param byteMesssage	Byte representation of the message (can be empty).
	 */
	public Message(byte[] byteMesssage) {
		
		this.byteMessage = byteMesssage;
		this.CHANNEL_ID = -1;
		this.NEXT_MIX_CHANNEL_ID = -1;
		this.timeOfReceival = clock.getTime();
		
	}
	
	
	/**
	 * Returns an identifier for the channel/user this message belongs to, 
	 * used by the next mix of the cascade.
	 * 
	 * @return	Identifier for the channel/user this message belongs to, used 
	 * 			by the next mix of the cascade.
	 */
	@Override
	public int getNextMixChannelID() {
		
		return this.NEXT_MIX_CHANNEL_ID;
		
	}
	
	
	/**
	 * Returns an identifier for the channel/user this message belongs to.
	 * 
	 * @return	Identifier for the channel/user this message belongs to.
	 */
	@Override
	public int getChannelID() {
		
		return this.CHANNEL_ID;
		
	}
	
	
	/**
	 * Returns an identifier for this message.
	 * 
	 * @return	Identifier for this message.
	 */
	@Override
	public int getMessageID() {
		
		return this.messageID;
		
	}


	/**
	 * Sets this message's identifier to the bypassed value.
	 * 
	 * @param newIdentifier	Value this message's identifier shall be set to.
	 */
	@Override
	public void setMessageID(int newIdentifier) {
		
		this.messageID = newIdentifier;
		
	}
	
	
	/** 
	 * Only a certain part of the message array must be transmitted to 
	 * the cascade's next mix (Some parts (e. g. the session key for the 
	 * corresponding mix) are not supposed to be sent). Which part is relevant 
	 * depends on the type of message.
	 * <p>
	 * This method defines the offset and length of the part to be 
	 * transmitted.
	 * 
	 * @param	startIndexOfPayload	Offset of the part to be transmitted.
	 * @param	payloadLength		Length of the part to be transmitted.
	 */
	@Override
	public void setPayloadRange(	int startIndexOfPayload,
									int payloadLength
									) {
		
		this.startIndexOfPayload = startIndexOfPayload;
		this.payloadLength = payloadLength;
		
	}
	
	
	/**
	 * Returns a reference on the channel/user this message belongs to.
	 * 
	 * @return	Reference on the channel/user this message belongs to.
	 */
	@Override
	public User getChannel() {
		
		return this.channel;
		
	}
	
	
	/** 
	 * Returns the point of time (timestamp), this message was received.
	 * 
	 * @return	Point of time (timestamp), this message was received.
	 */
	@Override
	public long getTimeOfReceival() {
		
		return timeOfReceival;
		
	}
	
	
	/** 
	 * Sets the point of time (timestamp), this message was received to the 
	 * bypassed value.
	 * 
	 * @param newTimeOfReceival	Point of time (timestamp), this message was 
	 * 							received.
	 */
	@Override
	public void setTimeOfReceival(long newTimeOfReceival) {

		this.timeOfReceival = newTimeOfReceival;
		
	}
	
	
	/**
	 * Returns the byte representation of this message 
	 * (<code>byteMessage</code>).
	 * 
	 * @return Byte representation of this message.
	 */
	@Override
	public byte[] getByteMessage() {
		
		return this.byteMessage;
		
	}
	
	
	/**
	 * Sets the byte representation of this message (<code>byteMessage</code>) 
	 * to the bypassed array.
	 * 
	 * @param byteMessage	New content for this message.
	 */
	@Override
	public void setByteMessage(byte[] byteMessage) {
		
		this.byteMessage = byteMessage;
		
	}
	
	
	/**
	 * Implements the <code>Comparable</code> interface's <code>compareTo()
	 * </code> method. Compares this <code>Message</code> with the specified 
	 * <code>Message</code> for order (criterion: alphabetic order of this 
	 * <code>Message</code>'s payload. Returns a negative integer, zero, or a 
	 * positive integer as this <code>Message</code> is less than, equal to, or 
	 * greater than the specified <code>Message</code>.
	 * 
	 * @param message	The <code>Message</code> to be compared.
	 * 
	 * @return			-1, 0, or 1 as this <code>Message</code> is less than, 
	 * 					equal to, or greater than the specified <code>Message
	 * 					</code>.
	 * 
	 * @see #setPayloadRange(int, int)
	 */
	@Override
	public int compareTo(Message message) {
		/*
		 * This method copies no elements for performance reasons (it gets
		 * called frequently to sort messages).
		 */

		if (this.payloadLength < message.payloadLength) {
				
			return -1;
				
		} else if (this.payloadLength > message.payloadLength) {
			
			return 1;
				
		} else { // both payloads have the same length
				
			for (int i=0; i<payloadLength; i++) {
					
				if (	this.byteMessage[this.startIndexOfPayload + i] < 
						message.byteMessage[message.startIndexOfPayload + i]
						) {
					
					return -1;
						
				} else if (	this.byteMessage[this.startIndexOfPayload + i] > 
							message.byteMessage[message.startIndexOfPayload + i]
							) {
						
					return 1;
				} 
					
			}
				
			// both payloads contain the same message
			return 0;
		}	
			
	}
	
}
