package mc.control.provider.listeners;

import mc.control.comm.MatrixConnection;

public interface MatrixConnectionListener {
	
	public void connectionSet( MatrixConnection conn );
	
	public void connectionChanged( boolean status );
}