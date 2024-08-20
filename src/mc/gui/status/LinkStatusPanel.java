package mc.gui.status;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import mc.control.provider.listeners.LinkStatusListener;

public class LinkStatusPanel extends JPanel implements LinkStatusListener {
	
	private static final long serialVersionUID = 1L;

	private Map<String, JLabel> labels = new HashMap<>();
	
	private static Dimension d = new Dimension( 30, 30 );
	
	public LinkStatusPanel() {
		this.setLayout( new GridLayout( 2, 8 ) );
		for ( int j = 0; j < 2; j++ ) {
			for ( int i = 0; i < 8; i++ ) {
				if ( j == 0 ) {
					JLabel l = createLabel( String.valueOf( (char)( 65 + i ) ) );
					l.setBorder( BorderFactory.createEtchedBorder() );
				} else {
					JLabel l = createLabel( String.valueOf( i + 1 ) );
					l.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
					l.setOpaque( true );
					l.setBackground( Color.BLUE );
					labels.put( "O" + ( i + 1 ), l );
				}
			}
		}
		this.setBorder( BorderFactory.createTitledBorder( "Current Status" ) );
	}
	
	private JLabel createLabel( String text ) {
		JLabel l = new JLabel( text, SwingConstants.CENTER );
		l.setFont( l.getFont().deriveFont( 16f ) );
		l.setPreferredSize( d );
		this.add( l );
		return l;
	}

	@Override
	public void processLinkStatus( List<String> status ) {
		status.forEach( s -> labels.get( s.substring( 0, 2 ) ).setText( s.substring( 3 ) ) );
	}
}