package architectureInterface;


/**
 * Architecture interface for component <code>NetworkClock</code>. 
 * <p>
 * Provides time information from a synchronized clock.
 * <p>
 * Must be thread-safe.
 * 
 * @author Karl-Peter Fuchs
 */
public interface NetworkClockInterface {

	
	/**
	 * Must return the number of milliseconds since January 1, 1970, 00:00:00 
	 * GMT from a synchronized clock.
	 * 
	 * @return	The number of milliseconds since January 1, 1970, 00:00:00 GMT.
	 */
	public long getTime();
	
}
