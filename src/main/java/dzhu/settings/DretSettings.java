package dzhu.settings;

public class DretSettings {
	private static DretSettings dretSettings = null;
	public static DretSettings getInstance() {
		if(dretSettings==null) {
			dretSettings = new DretSettings();
		}
		return dretSettings;
	}
	
	private static final String BASE_LINK="https://dret-dl.currenex.com";
	
	public static final String DEVMON_LINK = BASE_LINK+"/webstart/devmon.jnlp";
	public static final String CLASSIC_TAKER_LINK = BASE_LINK+"/webstart/requester.jnlp";
	public static final String CLASSIC_MAKER_LINK = BASE_LINK+"/webstart/provider.jnlp";
	public static final String MDF_LINK = BASE_LINK+"/webstart/marketdatafeedclient.jnlp";
	public static final String ENROLL_APP_LINK = BASE_LINK+"/webstart/enroll.jnlp";
	public static final String ENROLL_PAGE_LINK = BASE_LINK+"/enroll/";
	public static final String X2_LINK = BASE_LINK+"/x2/login.html";
	public static final String WEBADMIN_LINK = BASE_LINK+"/webadmin2/app/login";
	
	public static final String FIX_FX_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=FixTests_DRET_Sanity";
	public static final String SELENIUM_X2_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=X2Selenium_Remote_Integration_DretSanity";
	
	public static String ENROLLMENT_USERNAME;
	public static String ENROLLMENT_PASSWORD;
	public static String X2TAKER_USERNAME;
	public static String X2TAKER_PASSWORD;
	public static String VIKING_USERNAME;
	public static String VIKING_PASSWORD;
	public static String MOBILE_USERNAME;
	public static String MOBILE_PASSWORD;
	public static String DEVMONADMIN_USERNAME;
	public static String DEVMONADMIN_PASSWORD;
	public static String MDFADMIN_USERNAME;
	public static String MDFADMIN_PASSWORD;
	public static String WEBADMIN_USERNAME;
	public static String WEBADMIN_PASSWORD;
	
	public static String EMAIL_ENROLL = "qa@currenex.com";
}
