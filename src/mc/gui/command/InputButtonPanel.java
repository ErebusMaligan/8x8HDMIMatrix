package mc.gui.command;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import mc.control.comm.MatrixCommConstants;
import mc.control.comm.MatrixConnection;
import mc.control.provider.MatrixConnectionProvider;
import mc.control.provider.listeners.MatrixConnectionListener;
import statics.GUIUtils;

public class InputButtonPanel extends JPanel implements MatrixConnectionListener {
	
	private static final long serialVersionUID = 1L;

	private List<JButton> startButtons = new ArrayList<>();
	
	private List<JButton> endButtons = new ArrayList<>();
	
	private StringBuilder commandString;
	
	private static Dimension d = new Dimension( 40, 40 );
	
	private MatrixConnectionProvider con;
	
	public InputButtonPanel( MatrixConnectionProvider con ) {
		this.con = con;
		this.setLayout( new GridLayout( 2, 8 ) );
		for ( int j = 0; j < 2; j++ ) {
			for ( int i = 0; i < 8; i++ ) {
				if ( j == 0 ) {
					createStartButton( String.valueOf( (char)( 65 + i ) ), "0" + ( i + 1 ) );
				} else {
					createEndButton( String.valueOf( i + 1 ), "0" + ( i + 1 ) );
				}
			}
		}
		endButtons.forEach( b -> b.setEnabled( false ) );
		startButtons.forEach( b -> b.setEnabled( false ) );
		this.setBorder( BorderFactory.createTitledBorder( "Switch Inputs" ) );
	}
	
	private JButton createStartButton( String text, String command ) {
		JButton b = new JButton( text );
		b.setFont( b.getFont().deriveFont( 16f ) );
		GUIUtils.setSizes( b, d );
		b.addActionListener( e -> sendStartCommand( command ) );
		startButtons.add( b );
		this.add( b );
		return b;
	}
	
	private JButton createEndButton( String text, String command ) {
		JButton b = new JButton( text );
		b.setFont( b.getFont().deriveFont( 16f ) );
		GUIUtils.setSizes( b, d );
		b.addActionListener( e -> sendEndCommand( command ) );
		endButtons.add( b );
		this.add( b );
		return b;
	}
	
	private void sendStartCommand( String command ) {
		commandString = new StringBuilder( command );
		startButtons.forEach( b -> b.setEnabled( false ) );
		endButtons.forEach( b -> b.setEnabled( true ) );
	}
	
	private void sendEndCommand( String command ) {
		commandString.insert( 0, command ); //switch commands are encoded backward, you click dest then source, but need source then dest ordering
		try {
			con.sendCommand( MatrixCommConstants.requestSwitchSingle( commandString.toString() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		endButtons.forEach( b -> b.setEnabled( false ) );
		startButtons.forEach( b -> b.setEnabled( true ) );
		commandString = new StringBuilder();
	}

	@Override
	public void connectionChanged( boolean status ) {
		startButtons.forEach( b -> b.setEnabled( status ) );
		if ( !status ) {
			endButtons.forEach( b -> b.setEnabled( false ) );
		}
		
	}
	
	@Override
	public void connectionSet( MatrixConnection conn ) { 
		//not used here
	}
}