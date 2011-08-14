package message;


/**
 * <code>InternalMessage</code> used for communication between two
 * <code>OutputStrategy</code> components, located on neighbored mixes. 
 * Transmits the batch size (the first mix used for the batch it is about to 
 * send) to the second mix.
 * 
 * @author Karl-Peter Fuchs
 */
public class BatchSizeMessage extends Message implements Request, 
		InternalMessage {

	
	/** 
	 * Identifier for this type of message. Necessary since messages are 
	 * transmitted as byte streams which don't support the 
	 * <code>instanceOf</code> operator.
	 */
	public static final byte IDENTIFIER = (byte)250;
	
	/** 
	 * Batch size the sending mix used for the upcoming batch (= batch he will 
	 * send after this message).
	 */
	private int batchSize;
	
	
	/**
	 * Creates a new <code>BatchSizeMessage</code> containing the bypassed 
	 * <code>batchSize</code>.
	 * 
	 * @param batchSize	Batch size the sending mix used for the upcoming batch 
	 * 					(= batch he will send after this message)
	 */
	public BatchSizeMessage(int batchSize) {
		
		super(new byte[0]);
		
		this.batchSize = batchSize;
		
	}

	
	/** 
	 * Returns the variable <code>batchSize</code>'s value (= Batch size the 
	 * sending mix used for the upcoming batch (= batch he will send after this 
	 * message)).
	 * 
	 * @return	The variable <code>batchSize</code>'s value.
	 */
	public int getBatchSize() {
		
		return batchSize;
		
	}
	
}
