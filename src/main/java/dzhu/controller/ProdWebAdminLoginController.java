package dzhu.controller;

import java.util.List;

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

public class ProdWebAdminLoginController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnProdLogonWebAdmin=null;
	@FXML
	private Button btnProdResetPin=null;
	@FXML
	private Label lbProdWebadminResult=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;

	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdLogonWebAdmin.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnProdLogonWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(ProdSettings.WEBADMIN_LINK);		
			logonWebAdmin(ProdSettings.WEBADMIN_USERNAME, ProdSettings.WEBADMIN_PASSWORD);
		}catch (Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnProdLogonWebAdmin.getStyleClass().remove("success");
			btnProdLogonWebAdmin.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnProdLogonWebAdmin.getStyleClass().remove("danger");
		btnProdLogonWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnProdResetPinClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		lbProdWebadminResult.setText("");
		
		boolean ret = resetPin("qaltaker1" , ProdSettings.ENROLLMENT_USERNAME, "test1234");
		if (ret==false) {
			lbProdWebadminResult.setText("You need to login before reset pin");
			btnProdResetPin.getStyleClass().remove("success");
			btnProdResetPin.getStyleClass().add("danger");
		}else {
			btnProdResetPin.getStyleClass().remove("danger");
			btnProdResetPin.getStyleClass().add("success");
		}
		ControllerUtils.showStages(getParentStage());
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

	private boolean resetPin(String party, String user, String pin) {
		if(driver==null) {
			return false;
		}
		try {
			WebElement menuAccount = driver.findElement(By.xpath(".//a[@href='#/accounts']"));
			menuAccount.click();
			WebDriverWait wait = new WebDriverWait(driver, 5);
			By by = By.cssSelector("input[placeholder='Search...']");
			if (wait.until(ExpectedConditions.elementToBeClickable(by)) == null){
				return false;
			}
			try {
				WebElement inputSearch = driver.findElement(by);
				inputSearch.clear();
				inputSearch.sendKeys(party);
				//((RemoteWebDriver) driver).executeScript("arguments[0].value='"+party+"';", inputSearch);
				Thread.sleep(2000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			WebElement dropdown = driver.findElement(By.xpath(".//ul[@class='dropdown-menu btn-block']"));
			WebElement btnDropdown = driver.findElement(By.xpath(".//button[@ng-disabled='isTakerCreationInProgress()']"));
			btnDropdown.click();
			List<WebElement> countriesList=dropdown.findElements(By.tagName("li"));
			for (WebElement li : countriesList) {
				WebElement a;
				try {
					a = li.findElement(By.tagName("a"));
				}catch(Exception e) {
					continue;
				}
				
				if (a.getAttribute("innerHTML").contains(user)) {
					li.click();
				}
			}
			
			WebElement btnReset = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'reset')]")));
			btnReset.click();
			
			WebElement pinInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("pinInput")));
			pinInput.click();
			pinInput.sendKeys(pin);
	
			WebElement btnSavePin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//form[@name='pinForm']/div/div/button")));
			btnSavePin.click();
			
			WebElement btnApply = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".apply-button")));
			btnApply.click();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private WebDriver getDriver() throws Exception{
		if(driver==null) {			
			return initDriver();
		}else {
			try {
				driver.get(ProdSettings.WEBADMIN_LINK);
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
