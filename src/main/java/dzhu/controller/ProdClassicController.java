package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.ProdSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProdClassicController {
	@FXML
	private Button btnProdLogonClassicTaker=null;
	private WebDriver driver=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdLogonClassicTaker.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(ProdSettings.CLASSIC_TAKER_LINK);
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
	public void btnProdLogonClassicTakerClicked(Event e) {
		btnProdLogonClassicTaker.getStyleClass().remove("success");
		ControllerUtils.hideStages(getParentStage());
		
		driver = getDriver();
        driver.get(ProdSettings.CLASSIC_TAKER_LINK);
        
        loginTakerFromWeb(ProdSettings.CLASSICTAKER_USERNAME, ProdSettings.CLASSICTAKER_PASSWORD);
		
		ControllerUtils.showStages(getParentStage());
		btnProdLogonClassicTaker.getStyleClass().add("success");
	}
	
	private void loginTakerFromWeb(String user, String pwd) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		By by = By.name("username");
		wait.until(ExpectedConditions.elementToBeClickable(by));
		
		WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(user);
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(pwd);
        WebElement submitLogin = driver.findElement(By.id("submitButton"));
        submitLogin.click();
	}

}
