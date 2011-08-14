package architectureInterface;


import java.net.InetAddress;

import exception.InformationRetrieveException;

import externalInformationPort.Information;


/**
 * Architecture interface for component <code>ExternalInformationPort</code>. 
 * <p>
 * <code>ExternalInformationPort</code> is used for <code>Information</code> 
 * (for example a public key) exchange with "external" communication partners 
 * (for example other mixes or clients). This component doesn't affect the 
 * sending of mix messages directly (That's done by the 
 * <code>InputOutputHandler</code>: see 
 * <code>architectureInterface.InputOutputHandlerInterface</code>).
 * <p>
 * Each exchangeable type of information is specified in the enumeration 
 * <code>Information</code>.
 * <p>
 * Must be thread-safe.
 * 
 * @author Karl-Peter Fuchs
 */
public interface ExternalInformationPortInterface {

	
	/**
	 * Generic method to receive some <code>Information</code> from a 
	 * specified <code>InformationProvider</code> component.
	 * <p>
	 * If communication channel isn't safe, but transmitted data is sensitive, 
	 * cryptographic measures (like authentication and encryption) should be 
	 * applied.
	 * 
	 * @param informationProviderAddress	Address of the <code>
	 * 										InformationProvider</code> 
	 * 										component to receive data from.
	 * @param informationProviderPort		Port the <code>InformationProvider
	 * 										</code>-component to receive data 
	 * 										from runs on.
	 * @param informationOfInterest			Type of <code>Information</code>,
	 * 										that shall be received.
	 * 
	 * @return								The requested <code>Information
	 * 										</code>.
	 * 
	 * @throws	InformationRetrieveException 	Thrown, when requested <code>
	 * 											Information</code> not 
	 * 											available.
	 */
	public Object getInformation(	
			InetAddress informationProviderAddress,
			int informationProviderPort, 
			Information informationOfInterest
			) throws InformationRetrieveException;
				
	
	/**
	 * Generic method to receive some <code>Information</code> from a 
	 * specified <code>InformationProvider</code> component, sending the 
	 * bypassed data.
	 * <p>
	 * If communication channel isn't safe, but transmitted data is sensitive, 
	 * cryptographic measures (like authentication and encryption) should be 
	 * applied.
	 * 
	 * @param informationProviderAddress	Address of the <code>
	 * 										InformationProvider</code> 
	 * 										component to receive data from.
	 * @param informationProviderPort		Port the <code>InformationProvider
	 * 										</code> component to receive data 
	 * 										from runs on.
	 * @param informationOfInterest			Type of <code>Information</code>,
	 * 										that shall be received.
	 * @param data							Data to be transmitted.
	 * 
	 * @return								The requested <code>Information
	 * 										</code>.
	 * 
	 * @throws	InformationRetrieveException 	Thrown, when requested <code>
	 * 											Information</code> not 
	 * 											available.
	 */
	public Object getInformation(	
			InetAddress informationProviderAddress,
			int informationProviderPort, 
			Information informationOfInterest,
			byte[] data
			) throws InformationRetrieveException;
	
	
	/**
	 * Generic method to receive some <code>Information</code> from several 
	 * <code>ExternalInformationPort</code> components. The first component's 
	 * address and port number must be specified. The other components' 
	 * addresses are received by their predecessor (= telescope implementation).
	 * <p>
	 * If communication channel isn't safe, but transmitted data is sensitive, 
	 * cryptographic measures (like authentication and encryption) should be 
	 * applied.
	 * 
	 * @param informationProviderAddress	Address of the first <code>
	 * 										InformationProvider</code> 
	 * 										component to receive data from.
	 * @param informationProviderPort		Port the first 
	 * 										<code>InformationProvider</code> 
	 * 										component to receive data from runs 
	 * 										on.
	 * @param informationOfInterest			Type of <code>Information</code>,
	 * 										that shall be received.
	 * 
	 * @return								The requested <code>Information
	 * 										</code>s.
	 * 
	 * @throws	InformationRetrieveException 	Thrown, when requested <code>
	 * 											Information</code> not 
	 * 											available.
	 */
	public Object[] getInformationFromAll(
			InetAddress informationProviderAddress,
			int informationProviderPort, 
			Information informationOfInterest
			) throws InformationRetrieveException;
	
	
	/**
	 * Generic method to receive some <code>Information</code> from several 
	 * <code>ExternalInformationPort</code> components, sending the bypassed 
	 * data. The first component's address and port number must be specified. 
	 * The other components' addresses are received by their predecessor  (= 
	 * telescope implementation).
	 * <p>
	 * If communication channel isn't safe, but transmitted data is sensitive, 
	 * cryptographic measures (like authentication and encryption) should be 
	 * applied.
	 * 
	 * @param informationProviderAddress	Address of the first <code>
	 * 										InformationProvider</code> 
	 * 										component to receive data from.
	 * @param informationProviderPort		Port the first 
	 * 										<code>InformationProvider</code> 
	 * 										component to receive data from runs 
	 * 										on.
	 * @param informationOfInterest			Type of <code>Information</code>,
	 * 										that shall be received.
	 * @param data							Data to be transmitted.
	 * 
	 * @return								The requested <code>Information
	 * 										</code>s.
	 * 
	 * @throws	InformationRetrieveException 	Thrown, when requested <code>
	 * 											Information</code> not 
	 * 											available.
	 */
	public Object[] getInformationFromAll(
			InetAddress informationProviderAddress,
			int informationProviderPort, 
			Information informationOfInterest,
			byte[] data
			) throws InformationRetrieveException;
	
	
	/**
	 * Must make component start listening for requests (on communication 
	 * channel).
	 */
	public void acceptRequests();
	
}
