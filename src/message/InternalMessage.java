package message;


/**
 * Interface, all messages must implement, that are sent only between the mixes 
 * of a cascade (e. g. to transmit control information between two components 
 * located on different mixes). No external communication partner (e. g. a 
 * client or server) may be involved.
 * 
 * @author Karl-Peter Fuchs
 * 
 * @see ExternalMessage
 */
public interface InternalMessage {

}
