package mc.control.comm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import mc.control.provider.ListenerRelay;

public class MatrixConnection {

	private Socket socket;
	
	private volatile boolean keepReading = true;
	
	private ListenerRelay relay;
	
	public MatrixConnection( ListenerRelay relay, String ip, int port ) throws IOException {
		this.relay = relay;
		socket = new Socket();
		socket.setKeepAlive( true );
		socket.connect( new InetSocketAddress( ip, port ) );
		relay.connectionChanged( true );
		Thread t = new Thread( () -> {
			StringBuilder line = new StringBuilder();
			while ( keepReading ) {
				char c;
				try {
					while ( !socket.isClosed() && ( c = (char)socket.getInputStream().read() ) != -1 ) {
						if ( c == ';' || ( line.toString().equals( "EN" ) && c == 'D' ) || ( c == ':' && line.toString().equals( "LINK" ) ) ) {
							if ( c == 'D' ) {
								line.append( c );
							}
							String send = line.toString();
							System.out.println( "IN: " + send );
							relay.lineReceived( send );
							line = new StringBuilder();
						} else {
							line.append( c );
						}
						Thread.sleep( 5 );
					}
				} catch ( IOException | InterruptedException e ) {
					e.printStackTrace();
				}
			}
		} );
		t.start();
	}
	
	public boolean isConnected() {
		return !socket.isClosed();
	}
	
	public void send( String input ) throws UnsupportedEncodingException, IOException {
		System.out.println( "OUT: " + input );
		socket.getOutputStream().write( input.getBytes( StandardCharsets.UTF_8 ) );
	}
	
	public void close() throws IOException {
		keepReading = false;
		socket.close();
		relay.connectionChanged( false );
	}
}