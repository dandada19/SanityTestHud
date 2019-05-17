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

public class Int2X2LoginController {
	@FXML
	private Button btnInt2LaunchX2=null;
	@FXML
	private Button btnInt2LogonX2=null;
	
	private WebDriver driver = null;
	
	@FXML
	public void btnInt2LaunchX2Clicked(Event e) {
		driver = getDriver();
		driver.get(Int2Settings.X2_LINK);
		btnInt2LaunchX2.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonX2Clicked(Event e) {
		driver = getDriver();
		driver.get(Int2Settings.X2_LINK);
		logonX2("qtpint2_enroll", "test1234");
		btnInt2LogonX2.getStyleClass().add("success");
	}
	
	private void logonX2(String user, String pwd) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		By byRun = By.cssSelector("input[placeholder='Username']");
		wait.until(ExpectedConditions.elementToBeClickable(byRun));
		WebElement username = driver.findElement(By.cssSelector("input[placeholder='Username']"));
        username.sendKeys(user);
        WebElement password = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        password.sendKeys(pwd);
        WebElement submitLogin = driver.findElement(By.cssSelector(".qa-submit-button"));
        submitLogin.click();
        
        //pick the cert for qtpenroll_int2
        Screen s = new Screen();
		String elementsFolderPath = "src/main/resources/sikuli_elements/";
        Pattern certOther = new Pattern(elementsFolderPath + "x2_cert_other.PNG");
        Pattern certQtpInt2Enroll = new Pattern(elementsFolderPath + "x2_cert_qtpint2_enroll.PNG");
        certOther.similar((float)0.95);
        certQtpInt2Enroll.similar((float)0.95);
        Pattern btnOk = new Pattern(elementsFolderPath + "x2_button_ok.PNG");        
        try {
			s.wait(certOther, 5);
			s.click(certOther);
			s.wait(certQtpInt2Enroll, 5);
	        s.click(certQtpInt2Enroll);
	        s.click(btnOk);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}
	
	private WebDriver getDriver() {
		if(driver==null) {		
			return initDriver();
		}else {
			try {
				driver.get(Int2Settings.X2_LINK);
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