package mc.control.provider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import mc.control.comm.MatrixConnection;
import mc.control.provider.listeners.LinkStatusListener;
import mc.control.provider.listeners.MatrixConnectionListener;
import mc.control.provider.listeners.MatrixDataLineListener;
import mc.control.provider.listeners.NetworkStatusListener;

/**
 * Master relay for handling all listener messages
 * 
 * @author Daniel J. Rivers
 *
 * Created: Aug 19, 2024, 4:46:37 PM
 */
public class ListenerRelay implements MatrixConnectionListener, LinkStatusListener, NetworkStatusListener, MatrixDataLineListener {

	private List<MatrixConnectionListener> conL = new CopyOnWriteArrayList<>();
	
	private List<LinkStatusListener> linkL = new CopyOnWriteArrayList<>();
	
	private List<NetworkStatusListener> netL = new CopyOnWriteArrayList<>();
	
	private List<MatrixDataLineListener> lineL = new CopyOnWriteArrayList<>();

	public void addNetworkStatusListener( NetworkStatusListener l ) {
		netL.add( l );
	}
	
	public void addConnectionListener( MatrixConnectionListener l ) {
		conL.add( l );
	}
	
	public void addLinkStatusListener( LinkStatusListener l ) {
		linkL.add( l );
	}
	
	public void addMatrixDataLineListener( MatrixDataLineListener l ) {
		lineL.add( l );
	}
	
	@Override
	public void connectionSet( MatrixConnection conn ) {
		conL.forEach( l -> l.connectionSet( conn ) );
	}

	@Override
	public void connectionChanged( boolean status ) {
		conL.forEach( l -> l.connectionChanged( status ) );
	}

	@Override
	public void lineReceived( String line ) {
		lineL.forEach( l -> l.lineReceived( line ) );
	}

	@Override
	public void processLinkStatus( List<String> status ) {
		linkL.forEach( l -> l.processLinkStatus( status ) );
	}

	@Override
	public void processNetworkStatus( Map<String, String> status ) {
		netL.forEach( l -> l.processNetworkStatus( status ) );
	}
}