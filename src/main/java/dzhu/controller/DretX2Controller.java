package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import dzhu.settings.SettingsUtil;
import dzhu.settings.DretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DretX2Controller {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnDretSeleniumX2Test = null;
	@FXML
	private Button btnDretLaunchX2 = null;
	
	private WebDriver driver=null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		//replace [#BretSettings.ENROLLMENT_USERNAME#] with actual username
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnDretSeleniumX2Test.getScene().getWindow();
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
	public void btnDretSeleniumX2TestClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
	        driver.get(DretSettings.SELENIUM_X2_LINK);
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
			btnDretSeleniumX2Test.getStyleClass().remove("success");
			btnDretSeleniumX2Test.getStyleClass().add("danger");
			return;
		}
        ControllerUtils.showStages(getParentStage());
		btnDretSeleniumX2Test.getStyleClass().remove("danger");
		btnDretSeleniumX2Test.getStyleClass().add("success");
	}
	
	@FXML
	public void btnDretLaunchX2Clicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
        try {
			driver = getDriver();
	        driver.get(DretSettings.X2_LINK);
        }catch(Exception ex) {
    		ControllerUtils.showStages(getParentStage());
            btnDretLaunchX2.getStyleClass().remove("success");
            btnDretLaunchX2.getStyleClass().add("danger");
        	return;
        }
		ControllerUtils.showStages(getParentStage());
        btnDretLaunchX2.getStyleClass().remove("danger");
        btnDretLaunchX2.getStyleClass().add("success");
	}
	
	private void login() {
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

	
}
