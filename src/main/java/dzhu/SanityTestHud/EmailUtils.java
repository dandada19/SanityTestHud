package dzhu.SanityTestHud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import dzhu.settings.GlobalSettings;

public class EmailUtils {
	private static ChromeDriver driver;
	private static Map<String, String> StackName2SpanId;
	static {
		StackName2SpanId = new HashMap<>();
		StackName2SpanId.put("INT2", "stackInfo_int2");
		StackName2SpanId.put("PROD", "stackInfo_prod");
		StackName2SpanId.put("LOFX", "stackInfo_lofx");
		StackName2SpanId.put("BRET", "stackInfo_bret");
		StackName2SpanId.put("DRET", "stackInfo_demofx");
		StackName2SpanId.put("PRET", "stackInfo_pret");
		StackName2SpanId.put("TKFX", "stackInfo_tkfx");
	}
	
	public static void initializeDriver() {
		Thread t = new Thread(()-> {
			try {
				ChromeDriverService service = ChromeDriverService.createDefaultService();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--window-position=-32000,-32000");
				options.addArguments("--headless");
				driver = new ChromeDriver(service, options);		
				driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
	
				String url = "https://"+GlobalSettings.DEVPORTAL_USERNAME
						+":"+GlobalSettings.DEVPORTAL_PASSWORD+"@"+GlobalSettings.DEV_PORTAL_LINK+"home.html";
				driver.get(url);
			}catch(Exception e) {
				e.printStackTrace();
				driver.quit();
				driver = null;
				System.out.println("Timeout while navigating to DEV portal, "
						+ "you might set wrong password for your DEV portal user");
			}
		});
		t.start();
	}
	
	//stackName: {PROD, LOFX, PRET, DRET..}
	public static String getStackVersion(String stackName) {
		int maxWaitTime = 6;//3 seconds
		int elapsedTime = 0;
		while(elapsedTime<maxWaitTime &&
				driver!=null && 
				driver.getPageSource()!=null && 
				driver.getPageSource().contains("?/?")) {
			elapsedTime ++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String text="";
		try {
			WebElement spanVersion = driver.findElement(By.id(StackName2SpanId.get(stackName)));
			text = spanVersion.getText();
		}catch (Exception e) {
			System.out.println("Can't find version number of stack: " + stackName);
		}
		return text;
	}
	
	public static void closeDriver() {
		if(driver!=null) {
			driver.quit();
		}
	}

	
	public static String sendGetRequest(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection uc = url.openConnection();
		String userpass = "username" + ":" + "password";
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
		uc.setRequestProperty ("Authorization", basicAuth);		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		InputStream in = uc.getInputStream();		
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(in));
   
		String responseLine;
		StringBuffer response = new StringBuffer();
		while ((responseLine = responseReader.readLine()) != null) {
			response.append(responseLine+"\n");
		}
		responseReader.close();
		return response.toString();
	}
}
