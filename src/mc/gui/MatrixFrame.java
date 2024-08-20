package mc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import mc.control.comm.MatrixConnection;
import mc.control.provider.MatrixProvider;
import mc.control.provider.listeners.MatrixConnectionListener;
import mc.gui.command.InputButtonPanel;
import mc.gui.command.MainMenuBar;
import mc.gui.command.SendPanel;
import mc.gui.status.LinkStatusPanel;

public class MatrixFrame extends JFrame implements MatrixConnectionListener {

	private static final long serialVersionUID = 1L;
	
	private SendPanel send;
	
	private LinkStatusPanel status;
	
	private MatrixProvider provider;
	
	public MatrixFrame( MatrixProvider provider ) {
		this.provider = provider;
		send = new SendPanel( provider.getRelay() );
		status = new LinkStatusPanel();
		InputButtonPanel command = new InputButtonPanel( provider.getConnectionProvider() );
		this.setLayout( new BorderLayout() );
		this.setSize( new Dimension( 640, 480 ) );
		JPanel center = new JPanel();
		center.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		int sp = 5;
		c.insets = new Insets( sp, sp, sp, sp ); 
		center.add( status, c );
		c.gridy = 1;
		center.add( command );
		c.gridy = 1;
		center.add( send, c );
		this.add( center, BorderLayout.CENTER );
		this.add( new MainMenuBar( this, provider ).getBar(), BorderLayout.NORTH );
		this.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		this.addWindowListener( new WindowAdapter() {			
			@Override
			public void windowClosed( WindowEvent e ) {
				try {
					if ( provider.getConnectionProvider().getConnection() != null ) {
						provider.getConnectionProvider().getConnection().close();
					}
				} catch ( IOException e1 ) {
					e1.printStackTrace();
				}
			}
		} );
		provider.getRelay().addConnectionListener( command );
		provider.getRelay().addConnectionListener( this );
	}

	@Override
	public void connectionSet( MatrixConnection conn ) {
		send.connectionSet( conn );
		provider.getRelay().addLinkStatusListener( status );
	}

	@Override
	public void connectionChanged( boolean status ) {
		// TODO Auto-generated method stub
		
	}
}