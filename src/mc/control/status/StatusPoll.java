package mc.control.status;

import java.io.IOException;
import java.util.List;

import mc.control.comm.MatrixConnection;
import mc.control.provider.ListenerRelay;
import mc.control.provider.listeners.MatrixConnectionListener;
import mc.control.provider.listeners.MatrixDataLineListener;

public abstract class StatusPoll implements Runnable, MatrixConnectionListener, MatrixDataLineListener {
	
	protected Thread t;
	
	private volatile boolean running = true;
	
	protected List<String> commands;
	
	private MatrixConnection con;
	
	private long commandDelay = 100;
	
	private long loopDelay = 5000;
	
	protected ListenerRelay relay;
	
	protected StatusPoll( ListenerRelay relay, MatrixConnection con, long commandDelay, long loopDelay, List<String> commands ) {
		this( relay, con, commandDelay, loopDelay );
		this.commands = commands;
	}
	
	protected StatusPoll( ListenerRelay relay, MatrixConnection con, long commandDelay, long loopDelay ) {
		this( relay, con );
		this.commandDelay = commandDelay;
		this.loopDelay = loopDelay;
	}
	
	protected StatusPoll( ListenerRelay relay, MatrixConnection con ) {
		this.con = con;
		this.relay = relay;
		init();
		setCommands();
		relay.addConnectionListener( this );
		relay.addMatrixDataLineListener( this );
	}
	
	public void setCommands() {}
	
	public void init() {
		t = new Thread( this );	
	}
	
	public void start() {
		t.start();
	}
	
	public void stop() {
		running = false;
	}
	
	@Override
	public void run() {
		while ( running ) {
			try {
				for ( String c : commands ) {
					con.send( c );
					Thread.sleep( commandDelay );
				}
				Thread.sleep( loopDelay );
			} catch ( IOException | InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void connectionChanged( boolean status ) {
		if ( status ) {
			init();
		} else {
			stop();
		}
	}
	
	public void connectionSet( MatrixConnection conn ) {
		//not used here
	}
}