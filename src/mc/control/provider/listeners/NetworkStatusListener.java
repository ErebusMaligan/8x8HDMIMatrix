package mc.control.provider.listeners;

import java.util.Map;

@FunctionalInterface
public interface NetworkStatusListener {
	public void processNetworkStatus( Map<String, String> status );
}