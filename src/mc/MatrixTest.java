package mc;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mc.control.provider.MatrixProvider;
import mc.gui.MatrixFrame;

public class MatrixTest {

	public static void main( String[] args ) throws UnknownHostException, IOException {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			e.printStackTrace();
		}
		new MatrixFrame( new MatrixProvider() ).setVisible( true );
	}
}
