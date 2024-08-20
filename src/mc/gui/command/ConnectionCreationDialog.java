package mc.gui.command;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mc.control.comm.MatrixConnection;
import mc.control.provider.MatrixProvider;

public class ConnectionCreationDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public ConnectionCreationDialog( JFrame frame, MatrixProvider provider ) {
		super( frame, "Connection Info:", true );
		this.setLocationRelativeTo( frame );
		int sp = 5;
		JTextField host = new JTextField( "192.168.0.188" );
		JTextField port = new JTextField( "5000" );
		host.setColumns( 25 );
		port.setColumns( 4 );
		JLabel hostl = new JLabel( "Hostname:", SwingConstants.RIGHT );
		JLabel portl = new JLabel( "Port:", SwingConstants.RIGHT );
		JButton ok = new JButton( "OK" );
		ok.addActionListener( e -> {
			try {
				provider.getConnectionProvider().setConnection( new MatrixConnection( provider.getRelay(), host.getText(), Integer.parseInt( port.getText() ) ) );
			} catch ( NumberFormatException | IOException e1 ) {
				e1.printStackTrace();
			}
			dispose();
		} );
		JButton cancel = new JButton( "Cancel" );
		cancel.addActionListener( e -> dispose() );
		this.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets( sp, sp, sp, sp );
		this.add( hostl, c );
		c.gridx = 1;
		this.add( host, c );
		c.gridx = 0;
		c.gridy = 1;
		this.add( portl, c );
		c.gridx = 1;
		this.add( port, c );
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		JPanel g = new JPanel();
		g.setLayout( new GridLayout( 1, 2 ) );
		g.add( ok );
		g.add( cancel );
		this.add( g, c );
		this.pack();
	}	
}