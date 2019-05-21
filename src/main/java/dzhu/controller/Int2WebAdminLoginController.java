package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Int2WebAdminLoginController {
	@FXML
	private Button btnInt2LaunchWebAdmin=null;
	@FXML
	private Button btnInt2LogonWebAdmin=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchWebAdmin.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnInt2LaunchWebAdminClicked(Event e) {
		getParentStage().hide();
		driver = getDriver();
		driver.get(Int2Settings.WEBADMIN_LINK);
		getParentStage().show();
		btnInt2LaunchWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonWebAdminClicked(Event e) {
		getParentStage().hide();
		driver = getDriver();
		Runnable run = () -> {
			Screen s = new Screen();		
			String elementsFolderPath = "src/main/resources/sikuli_elements/";
	        Pattern btnOk = new Pattern(elementsFolderPath + "webadmin_button_ok.PNG");      
	        try {
				s.wait(btnOk, 20);
		        s.click(btnOk);
			} catch (FindFailed e1) {
				e1.printStackTrace();
			}
		};
		Thread thread = new Thread(run);
		thread.start();
		driver.get(Int2Settings.WEBADMIN_LINK);
		
		logonWebAdmin(Int2Settings.WEBADMIN_USERNAME, Int2Settings.WEBADMIN_PASSWORD);
		getParentStage().show();
		btnInt2LogonWebAdmin.getStyleClass().add("success");
	}
	
	private void logonWebAdmin(String user, String pwd) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		By byRun = By.cssSelector("input[placeholder='Username']");
		wait.until(ExpectedConditions.elementToBeClickable(byRun));
		WebElement username = driver.findElement(By.cssSelector("input[placeholder='Username']"));
        username.sendKeys(user);
        WebElement password = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        password.sendKeys(pwd);
        WebElement submitLogin = driver.findElement(By.cssSelector(".button-login"));
        submitLogin.click();
	}
	
	private WebDriver getDriver() {
		if(driver==null) {			
			return initDriver();
		}else {
			try {
				driver.get(Int2Settings.WEBADMIN_LINK);
			}catch(Exception e){
				//browser may be closed manually.
				driver = initDriver();
			}
			return driver;
		}
	}
	
	private WebDriver initDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}
}
