package mc.control.provider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import mc.control.comm.MatrixCommConstants;
import mc.control.comm.MatrixConnection;
import mc.control.status.link.LinkStatusPoll;
import mc.control.status.net.NetworkStatusPoll;

public class MatrixConnectionProvider {
	
	private MatrixConnection conn;
	
	private ListenerRelay relay;
	
	public MatrixConnectionProvider( ListenerRelay relay ) {
		this.relay = relay;
	}

	public MatrixConnection getConnection() {
		return conn;
	}
	
	public void setConnection( MatrixConnection conn ) {
		this.conn = conn;
		new LinkStatusPoll( relay, conn ).start();
		new NetworkStatusPoll( relay, conn ).start();
		relay.connectionSet( conn );
	}

	public void sendCommand( String string ) throws UnsupportedEncodingException, IOException {
		conn.send( string );
		conn.send( MatrixCommConstants.COM_REQ_LINK_STATUS );  //reload link info after sending any command
	}
}