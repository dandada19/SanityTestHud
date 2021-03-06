package dzhu.settings;

public class ProdSettings {
	private static ProdSettings prodSettings = null;
	public static ProdSettings getInstance() {
		if(prodSettings==null) {
			prodSettings = new ProdSettings();			
		}
		return prodSettings;
	}
	
	private static final String BASE_LINK="https://fxtrades-dl.currenex.net";
	
	public static final String DEVMON_LINK = BASE_LINK+"/webstart/devmon.jnlp";
	public static final String CLASSIC_TAKER_LINK = "https://fxtrades.currenex.net/";
	public static final String CLASSIC_MAKER_LINK = BASE_LINK+"/webstart/provider.jnlp";
	public static final String MDF_LINK = BASE_LINK+"/webstart/marketdatafeedclient.jnlp";
	public static final String ENROLL_APP_LINK = BASE_LINK+"/webstart/enroll.jnlp";
	public static final String ENROLL_PAGE_LINK = "https://enroll.currenex.com/enroll-prod/enroll.html";
	public static final String X2_LINK = BASE_LINK+"/x2/login.html";
	public static final String WEBADMIN_LINK = BASE_LINK+"/webadmin2/app/login/#/";
	
	public static final String FIX_FX_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=FixTests_PROD_Sanity";
	public static final String FIX_TREASURY_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=FixTests_PROD_TreasurySanity";
	public static final String FIX_CRYPTO_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=FixTests_Production_ProdCryptoSanity";
	public static final String FIX_GLOBALCREDIT_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=FixTests_Production_GlobalCreditSanity";
	public static final String SELENIUM_X2_LINK = "https://teamcity.qcnx.eexchange.com/viewType.html?buildTypeId=X2Selenium_Remote_Production_Prod";

	public static String ENROLLMENT_USERNAME;
	public static String ENROLLMENT_PASSWORD;
	public static String X2TAKER_USERNAME;
	public static String X2TAKER_PASSWORD;
	public static String CLASSICTAKER_USERNAME;
	public static String CLASSICTAKER_PASSWORD;
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
