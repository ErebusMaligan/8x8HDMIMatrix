package mc.control.provider;

/**
 * Provider for core objects
 * 
 * @author Daniel J. Rivers
 *
 * Created: Aug 19, 2024, 4:51:32 PM
 */
public class MatrixProvider {

	private ListenerRelay relay = new ListenerRelay();
	
	private MatrixConnectionProvider conP = new MatrixConnectionProvider( relay );
	
	public ListenerRelay getRelay() {
		return relay;
	}
	
	public MatrixConnectionProvider getConnectionProvider() {
		return conP;
	}
}
