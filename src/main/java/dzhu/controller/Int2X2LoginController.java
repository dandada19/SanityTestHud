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
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Int2X2LoginController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnInt2LaunchX2=null;
	@FXML
	private Button btnInt2LogonX2=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;

	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchX2.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnInt2LaunchX2Clicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(Int2Settings.X2_LINK);
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnInt2LaunchX2.getStyleClass().remove("success");
			btnInt2LaunchX2.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchX2.getStyleClass().remove("danger");
		btnInt2LaunchX2.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonX2Clicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(Int2Settings.X2_LINK);
			logonX2(Int2Settings.X2TAKER_USERNAME, Int2Settings.X2TAKER_PASSWORD);
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnInt2LogonX2.getStyleClass().remove("success");
			btnInt2LogonX2.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnInt2LogonX2.getStyleClass().remove("danger");
		btnInt2LogonX2.getStyleClass().add("success");
	}
	
	private void logonX2(String user, String pwd) throws Exception{
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
	
	private WebDriver getDriver() throws Exception{
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
	
	private WebDriver initDriver() throws Exception{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}
}
