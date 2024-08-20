package mc.gui.command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import mc.control.comm.MatrixConnection;
import mc.control.provider.MatrixProvider;
import mc.control.provider.listeners.MatrixConnectionListener;

public class MainMenuBar implements MatrixConnectionListener {
	
	private JMenuBar bar = new JMenuBar();
	
	private JMenuItem con;
	
	private JMenuItem dis;
	
	private JLabel light = new JLabel();
	
	public MainMenuBar( JFrame parent, MatrixProvider provider ) {
		bar.setLayout( new BorderLayout() );
		provider.getRelay().addConnectionListener( this );
		JMenu conn = new JMenu( "Connection" );
		conn.setMnemonic( 'c' );
		con = new JMenuItem( "Connect" );
		con.addActionListener( e -> new ConnectionCreationDialog( parent, provider ).setVisible( true ) );
		con.setMnemonic( 'c' );
		dis = new JMenuItem( "Disconnect" );
		dis.setMnemonic( 'd' );
		dis.setEnabled( false );
		dis.addActionListener( e -> {
			try {
				provider.getConnectionProvider().getConnection().close();
			} catch ( IOException e1 ) {
				e1.printStackTrace();
			}
		} );
		conn.add( con );
		conn.add( dis );
		bar.add( conn, BorderLayout.WEST );
		
		bar.add( Box.createGlue() );
		light.setOpaque( true );
		light.setBackground( Color.RED );
		JPanel lightPanel = new JPanel();
		lightPanel.add( light );
		Dimension d = new Dimension( 16, 16 );
		light.setPreferredSize( d );
		light.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		bar.add( lightPanel, BorderLayout.EAST );
		
	}
	
	public JComponent getBar() {
		return bar;
	}

	@Override
	public void connectionSet( MatrixConnection conn ) {
		connectionChanged( conn.isConnected() );
	}

	@Override
	public void connectionChanged( boolean status ) {
		con.setEnabled( !status );
		dis.setEnabled( status );
		light.setBackground( status ? Color.GREEN : Color.RED );
	}
}