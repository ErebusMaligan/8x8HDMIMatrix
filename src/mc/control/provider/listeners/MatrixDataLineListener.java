package mc.control.provider.listeners;

@FunctionalInterface
public interface MatrixDataLineListener {
	public void lineReceived( String line );
}
