package dzhu.settings;

public class LofxSettings {
	private static LofxSettings lofxSettings = null;
	public static LofxSettings getInstance() {
		if(lofxSettings==null) {
			lofxSettings = new LofxSettings();			
		}
		return lofxSettings;
	}
	
	private static final String BASE_LINK="https://dl.lofx.currenex.com";
	
	public static final String DEVMON_LINK = BASE_LINK+"/webstart/devmon.jnlp";
	public static final String CLASSIC_TAKER_LINK = "https://lofx.currenex.com/";
	public static final String CLASSIC_MAKER_LINK = BASE_LINK+"/webstart/provider.jnlp";
	public static final String MDF_LINK = BASE_LINK+"/webstart/marketdatafeedclient.jnlp";
	public static final String ENROLL_APP_LINK = BASE_LINK+"/webstart/enroll.jnlp";
	public static final String ENROLL_PAGE_LINK = "https://enroll.currenex.com/enroll-lofx/enroll.html";
	public static final String X2_LINK = BASE_LINK+"/x2/login.html";
	public static final String WEBADMIN_LINK = BASE_LINK+"/webadmin2/app/login/#/";
	
	public static final String FIX_FX_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=FixTests_LOFX_Sanity";

	public static String ENROLLMENT_USERNAME;
	public static String ENROLLMENT_PASSWORD;
	public static String CLASSICTAKER_USERNAME;
	public static String CLASSICTAKER_PASSWORD;
	public static String DEVMONADMIN_USERNAME;
	public static String DEVMONADMIN_PASSWORD;
	public static String MDFADMIN_USERNAME;
	public static String MDFADMIN_PASSWORD;
	public static String WEBADMIN_USERNAME;
	public static String WEBADMIN_PASSWORD;
	
	public static String EMAIL_ENROLL = "qa@currenex.com";
}
