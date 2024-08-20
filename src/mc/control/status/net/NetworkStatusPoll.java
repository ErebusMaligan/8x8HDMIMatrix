package mc.control.status.net;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mc.control.comm.MatrixConnection;
import mc.control.provider.ListenerRelay;
import mc.control.status.StatusPoll;

public class NetworkStatusPoll extends StatusPoll {

	private Map<String, String> buffer = new HashMap<>();
	
	public NetworkStatusPoll( ListenerRelay relay, MatrixConnection con ) {
		super( relay, con, 100, 30000, Arrays.asList( "IP?", "PT?", "GW?", "MA?" ) );
	}

	@Override
	public void lineReceived( String line ) {
		line = line.trim();
		for ( String m : Arrays.asList( "IP:", "PT:", "GW:", "MA:" ) ) {
			if ( line.startsWith( m ) ) {
				String[] a = line.split( ":" );
				buffer.put( a[ 0 ], a[ 1 ] );
			}
		}
		if ( line.startsWith( "MA:" ) ) {
			relay.processNetworkStatus( buffer );
		}
	}
}