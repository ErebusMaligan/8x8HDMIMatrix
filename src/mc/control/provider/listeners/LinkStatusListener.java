package mc.control.provider.listeners;

import java.util.List;

@FunctionalInterface
public interface LinkStatusListener {
	public void processLinkStatus( List<String> status );
}