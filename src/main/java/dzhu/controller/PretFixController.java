package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import dzhu.settings.PretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PretFixController {
	@FXML
	private Label labelPretFixFxStatus = null;
	@FXML
	private Button btnPretFixFX = null;
	@FXML
	private Button btnPretLaunchTeamcity = null;
	
	private WebDriver driver=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)labelPretFixFxStatus.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() throws Exception {
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
	public void btnPretFixFXClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
	        driver.get(PretSettings.FIX_FX_LINK);
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
			btnPretFixFX.getStyleClass().remove("success");
			btnPretFixFX.getStyleClass().add("danger");
			return;
		}
        ControllerUtils.showStages(getParentStage());
		btnPretFixFX.getStyleClass().remove("danger");
		btnPretFixFX.getStyleClass().add("success");
	}
	
	@FXML
	public void btnPretLaunchTeamcityClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
	        driver.get(GlobalSettings.FIX_TEAMCITY_LINK);
	        if(isLoginPageShown()) {
	        	login();
	        }
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
	        btnPretLaunchTeamcity.getStyleClass().remove("success");
	        btnPretLaunchTeamcity.getStyleClass().add("danger");
	        return;
		}
		ControllerUtils.showStages(getParentStage());
        btnPretLaunchTeamcity.getStyleClass().remove("danger");
        btnPretLaunchTeamcity.getStyleClass().add("success");
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

}
