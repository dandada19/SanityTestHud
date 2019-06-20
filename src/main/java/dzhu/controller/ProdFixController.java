package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import dzhu.settings.ProdSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdFixController {
	@FXML
	private Label labelProdFixFxStatus = null;
	@FXML
	private Label labelProdFixTreasStatus = null;
	@FXML
	private Button btnProdFixFX = null;
	@FXML
	private Button btnProdFixTreas = null;
	@FXML
	private Button btnProdFixCrypto = null;
	@FXML
	private Button btnProdLaunchTeamcity = null;
	
	private WebDriver driver=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)labelProdFixFxStatus.getScene().getWindow();
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
	public void btnProdFixFXClicked(Event e) {
		btnProdFixFX.getStyleClass().remove("success");
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
        driver.get(ProdSettings.FIX_FX_LINK);
        if(isLoginPageShown()) {
        	login();
        }

        clickRun();

        ControllerUtils.showStages(getParentStage());
		btnProdFixFX.getStyleClass().add("success");
	}
	@FXML
	public void btnProdFixTreasClicked(Event e) {
		btnProdFixTreas.getStyleClass().remove("success");
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
		driver.get(ProdSettings.FIX_TREASURY_LINK);        
        if(isLoginPageShown()) {
        	login();
        }

        clickRun();

		ControllerUtils.showStages(getParentStage());
		btnProdFixTreas.getStyleClass().add("success");
	}
	@FXML
	public void btnProdFixCryptoClicked(Event e) {
		btnProdFixCrypto.getStyleClass().remove("success");
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
        driver.get(ProdSettings.FIX_CRYPTO_LINK);
        if(isLoginPageShown()) {
        	login();
        }

        clickRun();

        ControllerUtils.showStages(getParentStage());
		btnProdFixFX.getStyleClass().add("success");
	}
	@FXML
	public void btnProdLaunchTeamcityClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
        driver.get(GlobalSettings.FIX_TEAMCITY_LINK);
        if(isLoginPageShown()) {
        	login();
        }

		ControllerUtils.showStages(getParentStage());
        btnProdLaunchTeamcity.getStyleClass().add("success");
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
