package dzhu.controller;

import java.util.Date;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Int2FixController {
	@FXML
	private Label labelInt2FixFxStatus = null;
	@FXML
	private Label labelInt2FixTreasStatus = null;
	@FXML
	private Button btnInt2FixFX = null;
	@FXML
	private Button btnInt2FixTreas = null;
	@FXML
	private Button btnInt2LaunchTeamcity = null;
	
	private WebDriver driver=null;
	final private String cookieFileName = "FixTeamcity.cookie";
	
	public WebDriver getDriver() {
		if(driver==null) {
			//driver = new ChromeDriver();
			//driver.get("https://teamcity.qcnx.eexchange.com");
			//addCookieToDriver();		
			driver = new ChromeDriver();
			return driver;
		}else {
			try {
				driver.get("https://teamcity.qcnx.eexchange.com");
			}catch(Exception e){
				//browser may be closed manually.
				driver = new ChromeDriver();
			}
			return driver;
		}
	}
	
	@FXML
	public void btnInt2FixFXClicked(Event e) {
		driver = getDriver();
        driver.get(Int2Settings.FIX_FX_LINK);
        if(isLoginPageShown()) {
        	login();
        }

        WebDriverWait wait = new WebDriverWait(driver, 30);
        By byRun = By.cssSelector(".btn-group_run > .btn:nth-child(1)");
        wait.until(ExpectedConditions.elementToBeClickable(byRun));
        WebElement run = driver.findElement(byRun);
        run.click();
                
		btnInt2FixFX.getStyleClass().add("success");
	}
	@FXML
	public void btnInt2FixTreasClicked(Event e) {
		driver = getDriver();
		driver.get(Int2Settings.FIX_TREASURY_LINK);        
        if(isLoginPageShown()) {
        	login();
        }

        WebDriverWait wait = new WebDriverWait(driver, 30);
        By byRun = By.cssSelector(".btn-group_run > .btn:nth-child(1)");
        wait.until(ExpectedConditions.elementToBeClickable(byRun));
        WebElement run = driver.findElement(byRun);
        run.click();
        
		btnInt2FixTreas.getStyleClass().add("success");
	}
	@FXML
	public void btnInt2LaunchTeamcityClicked(Event e) {
		driver = getDriver();
        driver.get(GlobalSettings.FIX_TEAMCITY_LINK);
        if(isLoginPageShown()) {
        	login();
        }
	}
	
	private void login() {
		WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("dzhu");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("Nihaoma1@");
        WebElement submitLogin = driver.findElement(By.name("submitLogin"));
        submitLogin.click();
        
        saveCookieDate();
	}
	
	private boolean isLoginPageShown() {
        By byLoginPage = By.name("username");
        if(driver.findElements(byLoginPage).size()!=0) {
        	return true;
        }
        return false;
	}
	
	private void saveCookieDate() {
		File file = new File(cookieFileName);
		try {
			file.delete();
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(Cookie k : driver.manage().getCookies()) {
				bw.write(k.getName()+";"+k.getValue()+";"+k.getDomain()+";"+
						 k.getPath()+";"+k.getExpiry()+";"+k.isSecure());
				bw.newLine();
			}
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	private void addCookieToDriver() {
		try{
			File file = new File(cookieFileName);
			if(!file.exists()) {
				return;
			}
			driver.manage().deleteAllCookies();
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String strLine;
			while((strLine = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(strLine, ";");
				while(tokens.hasMoreTokens()) {
					String name = tokens.nextToken();
					String value = tokens.nextToken();
					String domain = tokens.nextToken();
					String path = tokens.nextToken();
					String tmpExpiry = tokens.nextToken();
					Date expiry = null;
					if("null".equals(tmpExpiry) == false) {
						expiry = new Date(tmpExpiry);
					}
					Boolean isSecure = new Boolean(tokens.nextToken());
					Cookie k = new Cookie(name, value, domain, path, expiry, isSecure);
					driver.manage().addCookie(k);
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
