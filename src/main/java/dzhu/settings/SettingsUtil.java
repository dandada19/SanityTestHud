package dzhu.settings;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SettingsUtil {
	private static final byte[] keyBytes = "test1234test1234".getBytes();
	public SettingsUtil() {
	}
	public static void initSettings() {
		try {
			Path path = Paths.get("settings.properties");
			if(!Files.exists(path)) {
				Files.createFile(path);
			}
			for( String line : Files.readAllLines(path, StandardCharsets.UTF_8) ) {
				if(line.contains("=")) {
					//GLOBAL_DEVPORTAL_USERNAME=dzhu
					String key = line.split("=")[0];
					String value = "";
					if(line.split("=").length>1) {
						value = line.split("=")[1];
					}
					if(key.equals("GLOBAL_DEVPORTAL_PASSWORD") ||
							key.equals("GLOBAL_FIXPORTAL_PASSWORD")) {
						value=decryptString(value);
					}
					String stackName = key.split("_")[0];
					stackName = stackName.substring(0, 1) + stackName.substring(1, stackName.length()).toLowerCase();
					String fieldName = key.split("_")[1] + "_" + key.split("_")[2];
					Class<?> clsSettings = Class.forName("dzhu.settings." + stackName + "Settings");

					Field fieldA = clsSettings.getDeclaredField( fieldName );
		            fieldA.setAccessible( true );
		            fieldA.set( clsSettings, value );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String encryptString(String message) {
		try {
		    SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, secretKey);		    
		    byte[] bytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));		    		
		    return new String(Base64.getEncoder().encode(bytes));
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	public static String decryptString(String encryptedMessage) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte [] decodedBytes = Base64.getDecoder().decode(encryptedMessage.getBytes());
			String ret=new String( cipher.doFinal(decodedBytes) );
			return ret;
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
}
