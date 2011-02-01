package architectureInterface;


import message.Reply;
import message.Request;


/**
 * Architecture interface for component <code>InputOutputHandler</code>. 
 * <p>
 * Handles (mix) message-based connections (see package <code>Message</code>) 
 * to communication partners (clients, other mixes and proxy servers) and 
 * therefore abstracts from the physic communication channels.
 * <p>
 * Must be thread-safe.
 * 
 * @author Karl-Peter Fuchs
 */
public interface InputOutputHandlerInterface {
	
	
	/**
	 * Must accept the bypassed (already processed) <code>Request</code> and 
	 * send it to the next communication partner (e. g. mix).
	 * <p>
	 * Must return immediately (asynchronous behavior), the process of sending 
	 * itself may be deferred (e. g. if communication channel is busy).
	 * <p>
	 * Must assure order (queuing strategy).
	 * 
	 * @param request 	Message, that shall be sent to the next communication 
	 * 					partner.
	 * 
	 * @see #addRequests(Request[])
	 */
	public void addRequest(Request request);
	
	
	/**
	 * Must accept the bypassed (already processed) <code>Request</code>s and 
	 * send them to the next communication partner (e. g. mix).
	 * <p>
	 * Must return immediately (asynchronous behavior), the process of sending 
	 * itself may be deferred (e. g. if communication channel is busy).
	 * <p>
	 * Must assure order (queuing strategy).
	 * 
	 * @param requests 	Messages, that shall be sent to the next communication 
	 * 					partner.
	 * 
	 * @see #addRequest(Request)
	 */
	public void addRequests(Request[] requests);
	
	
	/**
	 * Must return an (unprocessed) <code>Request</code>, that was received 
	 * from a communication partner (e. g. client or other mix), previously.
	 * <p>
	 * If no requests are available, the method must block until a new request 
	 * arrives.
	 * <p>
	 * Must assure order (queuing strategy).
	 * 
	 * @return 	Request, that was received from a communication partner, 
	 * 			previously..
	 */
	public Request getRequest();
	
	
	/**
	 * Must accept the bypassed (already processed) <code>Reply</code> and 
	 * send it to the previous communication partner (e. g. mix).
	 * <p>
	 * Must return immediately (asynchronous behavior), the process of sending 
	 * itself may be deferred (e. g. if communication channel is busy).
	 * <p>
	 * Must assure order (queuing strategy).
	 * 
	 * @param reply 	Message, that shall be sent to the previous 
	 * 					communication partner.
	 * 
	 * @see #addReplies(Reply[])
	 */
	public void addReply(Reply reply);
	
	
	/**
	 * Must accept the bypassed (already processed) <code>Reply</code>ies and 
	 * send them to the previous communication partner (e. g. mix). 
	 * <p>
	 * Must return immediately (asynchronous behavior), the process of sending 
	 * itself may be deferred (e. g. if communication channel is busy).
	 * <p>
	 * Must assure order (queuing strategy).
	 * 
	 * @param replies 	Messages, that shall be sent to the previous 
	 * 					communication partner.
	 * 
	 * @see #addReply(Reply)
	 */
	public void addReplies(Reply[] replies);
	
	
	/**
	 * Must return an (unprocessed) <code>Reply</code>, that was received 
	 * from a communication partner (e. g. proxy or other mix), previously.
	 * <p>
	 * If no replies are available, the method must block until a new reply 
	 * arrives.
	 * <p>
	 * Must assure order (queuing strategy).
	 * 
	 * @return 	Reply, the implementing component <code>InputOutputHandler
	 * 			</code> has received from a communication partner, previously.
	 */
	public Reply getReply();

	
	/**
	 * Must make the implementing component listen for connections/messages on 
	 * communication channel(s).
	 */
	public void acceptConnections();
		
}
