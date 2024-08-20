package mc.gui.command;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mc.control.comm.MatrixConnection;
import mc.control.provider.ListenerRelay;
import mc.control.provider.listeners.MatrixConnectionListener;

public class SendPanel extends JPanel implements MatrixConnectionListener {

	private static final long serialVersionUID = 1L;

	private MatrixConnection mat;

	private JButton send = new JButton( "Send" );
	
	private ListenerRelay relay;
	
	public SendPanel( ListenerRelay relay ) {
		this.relay = relay;
		JTextField input = new JTextField();
		input.setColumns( 25 );
		JLabel com = new JLabel( "Command: ", SwingConstants.RIGHT );
		send.addActionListener( e -> {
			try {
				mat.send( input.getText() );
			} catch ( IOException e1 ) {
				e1.printStackTrace();
			}
			input.setText( "" );
		} );
		send.setEnabled( false );
		this.setLayout( new GridBagLayout() );
		int sp = 5;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets( sp, sp, sp, sp );
		this.add( com, c );
		c.gridx = 1;
		this.add( input, c );
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add( send, c );
	}

	@Override
	public void connectionChanged( boolean status ) {
		send.setEnabled( status );
	}

	@Override
	public void connectionSet( MatrixConnection mat ) {
		this.mat = mat;
		relay.addConnectionListener( this );
		connectionChanged( mat.isConnected() );
	}
}