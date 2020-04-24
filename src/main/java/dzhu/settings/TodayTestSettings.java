package dzhu.settings;

public class TodayTestSettings {
	private static TodayTestSettings todayTestSettings = null;
	public static TodayTestSettings getInstance() {
		if(todayTestSettings==null) {
			todayTestSettings = new TodayTestSettings();			
		}
		return todayTestSettings;
	}
	
	public static String CHANGE_TICKET_NUMBER;
	public static String STACK_VERSION_NUMBER;
	public static boolean IS_STACK_REBOOT = true;
	public static boolean IS_MAJOR_RELEASE;
	public static boolean IS_MINOR_RELEASE;
	public static boolean IS_PATCH;
}
