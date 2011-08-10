package outputStrategy;


import inputOutputHandler.InputOutputHandlerController;
import message.Reply;
import message.Request;
import architectureInterface.OutputStrategyInterface;


/**
 * Controller class of component <code>OutputStrategy</code>. 
 * <p>
 * Collects messages until an output criterion is fulfilled (certain number of 
 * messages collected or timeout reached).
 * <p>
 * Messages are added by component <code>MessageProcessor</code>. When the 
 * output criterion is fulfilled, the collected messages are bypassed to the 
 * <code>InputOutputHandler</code> (component), which sends them to their 
 * destination.
 * 
 * @author Karl-Peter Fuchs
 */
public class OutputStrategyController implements OutputStrategyInterface {
	
	//TODO: Beschreibung hinzufügen; hier kann später auch eine andere Klasse eingebunden werden
	private BatchController batchController;
	
	
	/**
	 * Initialization method for this component. Makes this component ready 
	 * for accepting messages.
	 * 
	 * @param inputOutputHandler	Reference on component 
	 * 								<code>InputOutputHandler</code> (used to 
	 * 								send messages after output).
	 */
	public void initialize(InputOutputHandlerController inputOutputHandler) {
		//TODO: get info about inputoutputHandler
		System.out.println(inputOutputHandler.toString());
		
		batchController = new BatchController();
		
		batchController.initialize(inputOutputHandler);
		System.out.println("Batchcontroller... initialized");
	}

	
	/**
	 * Can be used to add a <code>Request</code>, that shall be put out 
	 * according to the underlying output strategy.
	 * <p>
	 * Return immediately (asynchronous behavior), internal output 
	 * decision is deferred.
	 * 
	 * @param request	<code>Request</code>, that shall be put out according 
	 * 					to the underlying output strategy.
	 */
	@Override
	public void addRequest(Request request) {
		batchController.addRequest(request);
	}
	
	
	/**
	 * Can be used to add a <code>Reply</code>, that shall be put out 
	 * according to the underlying output strategy.
	 * <p>
	 * Returns immediately (asynchronous behavior), internal output 
	 * decision is deferred.
	 * 
	 * @param reply	<code>Reply</code>, that shall be put out according 
	 * 				to the underlying output strategy.
	 */
	@Override
	public void addReply(Reply reply) {
		batchController.addReply(reply);
	}
	
	
	/** 
	 * Used by 
	 * <code>PreviousMixConnectionHandler</code> component to change the batch size.
	 * <p>
	 * Only relevant for batch output.
	 * 
	 * @see message.BatchSizeMessage
	 */
	public void setBatchSize(int newSize) {
		//TODO: check whether batch is used
		
		//Mix kann Nachricht auch mit normalem "normaler" Message an den nächsten Mix schicken.
		
		batchController.setBatchSize(newSize);
		//this.batchSize = newSize;
		
	}

	
}
