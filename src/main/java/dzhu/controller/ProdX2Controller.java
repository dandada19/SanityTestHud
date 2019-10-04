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
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdX2Controller {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnProdSeleniumX2Test = null;
	@FXML
	private Button btnProdLogonX2 = null;
	
	private WebDriver driver=null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdSeleniumX2Test.getScene().getWindow();
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
	public void btnProdSeleniumX2TestClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
	        driver.get(ProdSettings.SELENIUM_X2_LINK);
	        if(isLoginPageShown()) {
	        	login();
	        }
	
	        WebDriverWait wait = new WebDriverWait(driver, 30);
	        By byRun = By.cssSelector(".btn-group_run > .btn:nth-child(1)");
	        wait.until(ExpectedConditions.elementToBeClickable(byRun));
	        WebElement run = driver.findElement(byRun);
	        run.click();
		}catch (Exception ex) {
	        ControllerUtils.showStages(getParentStage());
			btnProdSeleniumX2Test.getStyleClass().remove("success");
			btnProdSeleniumX2Test.getStyleClass().add("danger");
			return;
		}
        ControllerUtils.showStages(getParentStage());
		btnProdSeleniumX2Test.getStyleClass().remove("danger");
		btnProdSeleniumX2Test.getStyleClass().add("success");
	}
	@FXML
	public void btnProdLogonX2Clicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
        try {
			driver = getDriver();
	        driver.get(ProdSettings.X2_LINK);
	        logonX2(ProdSettings.X2TAKER_USERNAME, ProdSettings.X2TAKER_PASSWORD);
        }catch (Exception ex) {
    		ControllerUtils.showStages(getParentStage());
    		btnProdLogonX2.getStyleClass().remove("success");
            btnProdLogonX2.getStyleClass().add("danger");
            return;
		}    
		ControllerUtils.showStages(getParentStage());
		btnProdLogonX2.getStyleClass().remove("danger");
        btnProdLogonX2.getStyleClass().add("success");
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
	}
	
	
}
