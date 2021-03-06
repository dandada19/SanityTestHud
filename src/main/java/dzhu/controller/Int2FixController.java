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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)labelInt2FixFxStatus.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() throws Exception{
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(GlobalSettings.FIX_TEAMCITY_LINK);
			}catch(Exception e){
				//browser may be closed manually.
				driver = initDriver();
			}
			return driver;
		}
	}
	
	private WebDriver initDriver() throws Exception{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}
	
	@FXML
	public void btnInt2FixFXClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
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
		}catch(Exception ex) {
	        ControllerUtils.showStages(getParentStage());
			btnInt2FixFX.getStyleClass().remove("success");
			btnInt2FixFX.getStyleClass().add("danger");
			return;
		}
        ControllerUtils.showStages(getParentStage());
		btnInt2FixFX.getStyleClass().remove("danger");
		btnInt2FixFX.getStyleClass().add("success");
	}
	@FXML
	public void btnInt2FixTreasClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
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
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnInt2FixTreas.getStyleClass().remove("success");
			btnInt2FixTreas.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnInt2FixTreas.getStyleClass().remove("danger");
		btnInt2FixTreas.getStyleClass().add("success");
	}
	@FXML
	public void btnInt2LaunchTeamcityClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
	        driver.get(GlobalSettings.FIX_TEAMCITY_LINK);
	        if(isLoginPageShown()) {
	        	login();
	        }
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
	        btnInt2LaunchTeamcity.getStyleClass().remove("success");
	        btnInt2LaunchTeamcity.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
        btnInt2LaunchTeamcity.getStyleClass().remove("danger");
        btnInt2LaunchTeamcity.getStyleClass().add("success");
	}
	
	private void login() throws Exception{
		WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(GlobalSettings.FIXPORTAL_USERNAME);
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(GlobalSettings.FIXPORTAL_PASSWORD);
        WebElement submitLogin = driver.findElement(By.name("submitLogin"));
        submitLogin.click();
	}
	
	private boolean isLoginPageShown() {
        By byLoginPage = By.name("username");
        if(driver.findElements(byLoginPage).size()!=0) {
        	return true;
        }
        return false;
	}
	
	@SuppressWarnings({ "unused" })
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
