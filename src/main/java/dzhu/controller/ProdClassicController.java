package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.ProdSettings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdClassicController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnProdLogonClassicTaker=null;
	private WebDriver driver=null;
	private Stage parentStage = null;

	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdLogonClassicTaker.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() throws Exception{
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
	
	private WebDriver initDriver() throws Exception{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}
	
	@FXML
	public void btnProdLogonClassicTakerClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
	        driver.get(ProdSettings.CLASSIC_TAKER_LINK);
	        loginTakerFromWeb(ProdSettings.CLASSICTAKER_USERNAME, ProdSettings.CLASSICTAKER_PASSWORD);
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnProdLogonClassicTaker.getStyleClass().remove("success");
			btnProdLogonClassicTaker.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnProdLogonClassicTaker.getStyleClass().remove("danger");
		btnProdLogonClassicTaker.getStyleClass().add("success");
	}
	
	private void loginTakerFromWeb(String user, String pwd) throws Exception{
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
