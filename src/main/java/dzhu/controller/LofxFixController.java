package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import dzhu.settings.LofxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LofxFixController {
	@FXML
	private Label labelLofxFixFxStatus = null;
	@FXML
	private Button btnLofxFixFX = null;
	@FXML
	private Button btnLofxLaunchTeamcity = null;
	
	private WebDriver driver=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)labelLofxFixFxStatus.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get("https://teamcity.qcnx.eexchange.com");
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
	
	@FXML
	public void btnLofxFixFXClicked(Event e) {
		btnLofxFixFX.getStyleClass().remove("success");
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
        driver.get(LofxSettings.FIX_FX_LINK);
        if(isLoginPageShown()) {
        	login();
        }

        clickRun();

        ControllerUtils.showStages(getParentStage());
		btnLofxFixFX.getStyleClass().add("success");
	}

	@FXML
	public void btnLofxLaunchTeamcityClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
        driver.get(GlobalSettings.FIX_TEAMCITY_LINK);
        if(isLoginPageShown()) {
        	login();
        }

		ControllerUtils.showStages(getParentStage());
        btnLofxLaunchTeamcity.getStyleClass().add("success");
	}
	
	private void login() {
		WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(GlobalSettings.FIXPORTAL_USERNAME);
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(GlobalSettings.FIXPORTAL_PASSWORD);
        WebElement submitLogin = driver.findElement(By.name("submitLogin"));
        submitLogin.click();
	}
	
	private void clickRun() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
        By byRun = By.cssSelector(".btn-group_run > .btn:nth-child(1)");
        wait.until(ExpectedConditions.elementToBeClickable(byRun));
        WebElement run = driver.findElement(byRun);
        run.click();
	}
	
	private boolean isLoginPageShown() {
        By byLoginPage = By.name("username");
        if(driver.findElements(byLoginPage).size()!=0) {
        	return true;
        }
        return false;
	}

}