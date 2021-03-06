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

public class Int2WebAdminLoginController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnInt2LaunchWebAdmin=null;
	@FXML
	private Button btnInt2LogonWebAdmin=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;

	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}

	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchWebAdmin.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnInt2LaunchWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(Int2Settings.WEBADMIN_LINK);
		}catch (Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnInt2LaunchWebAdmin.getStyleClass().remove("success");
			btnInt2LaunchWebAdmin.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchWebAdmin.getStyleClass().remove("danger");
		btnInt2LaunchWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
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
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnInt2LogonWebAdmin.getStyleClass().remove("success");
			btnInt2LogonWebAdmin.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnInt2LogonWebAdmin.getStyleClass().remove("danger");
		btnInt2LogonWebAdmin.getStyleClass().add("success");
	}
	
	private void logonWebAdmin(String user, String pwd) throws Exception{
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
	
	private WebDriver getDriver() throws Exception{
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
	
	private WebDriver initDriver() throws Exception{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}
}
