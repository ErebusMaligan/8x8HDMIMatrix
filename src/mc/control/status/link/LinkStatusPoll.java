package mc.control.status.link;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mc.control.comm.MatrixCommConstants;
import mc.control.comm.MatrixConnection;
import mc.control.provider.ListenerRelay;
import mc.control.status.StatusPoll;

public class LinkStatusPoll extends StatusPoll {

	private List<String> buffer = new ArrayList<>();
	
	public LinkStatusPoll( ListenerRelay relay, MatrixConnection con ) {
		super( relay, con, 100, 10000, Arrays.asList( MatrixCommConstants.COM_REQ_LINK_STATUS ) );
	}

	@Override
	public void lineReceived( String line ) {
		if ( line.contains( "LINK:" ) ) {
			buffer.clear();
		} else if ( line.contains( "END" ) ) {
			relay.processLinkStatus( new ArrayList<>( buffer ) );
		} else if ( line.startsWith( "O" ) ) {
			buffer.add( line );
		}
	}
}