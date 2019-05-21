package dzhu.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;


public class SettingsUtil {
	public SettingsUtil() {
	}
	public static void initSettings() {
		try {
			File propFile = new File("src/main/resources/settings.properties");
			BufferedReader br = new BufferedReader(new FileReader(propFile));
			String line;
			while((line = br.readLine()) != null) {
				if(line.contains("=")) {
					//GLOBAL_DEVPORTAL_USERNAME=dzhu
					String key = line.split("=")[0];
					String value = line.split("=")[1];
					String fieldName = key.split("_")[1] + "_" + key.split("_")[2];
					if(key.contains("GLOBAL")) {
						Field fieldA = GlobalSettings.class.getDeclaredField( fieldName );
			            fieldA.setAccessible( true );
			            fieldA.set( GlobalSettings.class, value );
					}else if(key.contains("INT2")) {
						Field fieldA = Int2Settings.class.getDeclaredField( fieldName );
			            fieldA.setAccessible( true );
			            fieldA.set( Int2Settings.class, value );
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
