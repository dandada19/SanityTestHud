package dzhu.settings;

public class GlobalSettings {
	private static GlobalSettings globalSettings = null;
	public static GlobalSettings getInstance() {
		if(globalSettings==null) {
			globalSettings = new GlobalSettings();			
		}
		return globalSettings;
	}
	
	public static final String DEV_PORTAL_LINK="https://dev.eexchange.com/";
	public static final String FIX_TEAMCITY_LINK="https://teamcity.qcnx.eexchange.com";
	public static final String QTP_PORTAL_LINK="http://autotest-qtp-14.curnx.com/QAPortal/index.html";
	

	public static String FIXPORTAL_USERNAME;
	public static String FIXPORTAL_PASSWORD;
	public static String DEVPORTAL_USERNAME;
	public static String DEVPORTAL_PASSWORD;
}
