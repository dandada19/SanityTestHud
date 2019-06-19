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
	public static final String QTP_PORTAL_LINK="http://autotest-qtp-14.curnx.com/QAPortal";
	

	public static String FIXPORTAL_USERNAME;
	public static String FIXPORTAL_PASSWORD;
	public static String DEVPORTAL_USERNAME;
	public static String DEVPORTAL_PASSWORD;
	
	public static String EMAIL_TO_LIST_PROD = "IQA-internal <QA-internal@globallink.com>; TechOps <techops@globallink.com>; "
			+ "Development <dev@globallink.com>; tradeops2 <tradeops@currenex.com>; DBAs <DBAs@globallink.com>; network <network@eexchange.com>";
	public static String EMAIL_CC_LIST_PROD = "Siby John STT <SJohn@StateStreet.com>; Integration Support <IntSupport@eexchange.com>";
	

	public static String EMAIL_TO_LIST_INT2 = "Integration Support <IntSupport@eexchange.com>; TechOps <techops@globallink.com>; "
    		+ "Product Management <ProductManagement@globallink.com>";
	public static String EMAIL_CC_LIST_INT2 = "Siby John STT <SJohn@StateStreet.com>; QA-internal <QA-internal@globallink.com>";
	
	public static String EMAIL_TO_LIST_RET = "Retail Development <RetailDevelopment@globallink.com>; TechOps <techops@globallink.com>";
	public static String EMAIL_CC_LIST_RET = "TokyoAcctMgr <TokyoAcctMgr@globallink.com>; SingaporeAcctMgr <SingaporeAcctMgr@globallink.com>;"
			+ " QA-internal <QA-internal@globallink.com>; tradeops2 <tradeops@currenex.com>";
}
