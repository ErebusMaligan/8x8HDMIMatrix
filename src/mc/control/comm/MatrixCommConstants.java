package mc.control.comm;

public class MatrixCommConstants {
	
	public static final String COM_PREFIX = "MT00";
	
	public static final String COM_SUFFIX = "NT";
	
	public static final String COM_SWITCH = "SW";
	
	public static final String COM_REQUEST = "RD";
	
	public static final String COM_SAVE = "SV";
	
	public static final String COM_BUZZER = "BZEN0";
	
	public static final String COM_REQ_LINK_STATUS = COM_PREFIX + "RD0000" + COM_SUFFIX;
	
	public static final String COM_REQ_BUZZER_ON = COM_PREFIX + COM_BUZZER + 1 + COM_SUFFIX;
	
	public static final String COM_REQ_BUZZER_OFF = COM_PREFIX + COM_BUZZER + 0 + COM_SUFFIX;
	
	public static final String COM_REQ_MIRROR_ALL = COM_PREFIX + COM_SWITCH + "0000" + COM_SUFFIX;
	
	public static String requestSwitchAll( String dest ) {
		return COM_PREFIX + COM_SWITCH + dest + "00" + COM_SUFFIX;
	}
	
	public static String requestSwitchSingle( String destSource ) {
		return COM_PREFIX + COM_SWITCH + destSource + COM_SUFFIX;
	}
	
//	public static String saveConfig( String num ) {
//		
//	}
	
	

}
