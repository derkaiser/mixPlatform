package outputStrategy;


import java.util.logging.Logger;
import ifaceloading.OutputStrategyLoader;
import inputOutputHandler.InputOutputHandlerController;
import internalInformationPort.InternalInformationPortController;
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
	//private BatchController batchController;
	private OutputStrategyInterface outputStrategyIF;
	
	/** 
	 * Reference on component <code>InputOutputHandler</code>. 
	 * Used to bypass collected messages, when output criterion fulfilled.
	 */
	private InputOutputHandlerController inputOutputHandler;
	
	/** 
	 * Reference on component <code>InternalInformationPort</code>. 
	 * Used to display and/or log data and read general settings.
	 */
	private static InternalInformationPortController internalInformationPort = 
		new InternalInformationPortController();
	
	/** Logger used to log and display information. */
	private final static Logger LOGGER = internalInformationPort.getLogger();
	
	/**
	 * Initialization method for this component. Makes this component ready 
	 * for accepting messages.
	 * 
	 * @param inputOutputHandler	Reference on component 
	 * 								<code>InputOutputHandler</code> (used to 
	 * 								send messages after output).
	 */
	public void initialize(InputOutputHandlerController inputOutputHandler) {
		
		//TODO: relativen Pfad einführen
		outputStrategyIF = OutputStrategyLoader.getClass( "/Users/daniel/Programmierung/Master/mixPlatformComponents/bin/", "outputStrategy.BatchController" );
		
		//batchController = new BatchController();
		
		this.inputOutputHandler = inputOutputHandler;
		
		outputStrategyIF.initialize();
		LOGGER.fine("Controller... initialized");
		
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
		outputStrategyIF.addRequest(request);
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
		outputStrategyIF.addReply(reply);
	}


	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
}
