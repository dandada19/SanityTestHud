package dzhu.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Int2WebAdminLoginController {
	@FXML
	private Button btnInt2LaunchWebAdmin=null;
	@FXML
	private Button btnInt2LogonWebAdmin=null;
	
	private WebDriver driver = null;
	
	@FXML
	public void btnInt2LaunchWebAdminClicked(Event e) {
		driver = getDriver();
		driver.get(Int2Settings.WEBADMIN_LINK);
		btnInt2LaunchWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonWebAdminClicked(Event e) {
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
		
		logonWebAdmin("qawebadmindz", "test1234");
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
			driver = new ChromeDriver();
			return driver;
		}else {
			try {
				driver.get(Int2Settings.WEBADMIN_LINK);
			}catch(Exception e){
				//browser may be closed manually.
				driver = new ChromeDriver();
			}
			driver.manage().window().maximize();
			return driver;
		}
	}
}
