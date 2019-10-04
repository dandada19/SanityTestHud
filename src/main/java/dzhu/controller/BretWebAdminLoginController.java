package dzhu.controller;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.BretSettings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BretWebAdminLoginController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnBretLaunchWebAdmin=null;
	@FXML
	private Button btnBretLogonWebAdmin=null;
	@FXML
	private Button btnBretResetPin=null;
	@FXML
	private Label lbBretWebadminResult=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		//replace [#BretSettings.ENROLLMENT_USERNAME#] with actual username
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnBretLaunchWebAdmin.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnBretLaunchWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(BretSettings.WEBADMIN_LINK);
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnBretLaunchWebAdmin.getStyleClass().add("danger");
			return;			
		}
		ControllerUtils.showStages(getParentStage());
		btnBretLaunchWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnBretLogonWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			/*
			//comment this thread out, so user has to select cert manually.		
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
			*/
			driver.get(BretSettings.WEBADMIN_LINK);		
			logonWebAdmin(BretSettings.WEBADMIN_USERNAME, BretSettings.WEBADMIN_PASSWORD);
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnBretLogonWebAdmin.getStyleClass().remove("success");
			btnBretLogonWebAdmin.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnBretLogonWebAdmin.getStyleClass().remove("danger");
		btnBretLogonWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnBretResetPinClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		lbBretWebadminResult.setText("");
		
		boolean ret = resetPin("qtpicuser" , BretSettings.ENROLLMENT_USERNAME, "test1234");
		if (ret==false) {
			lbBretWebadminResult.setText("You need to login before reset pin");
			btnBretResetPin.getStyleClass().remove("success");
			btnBretResetPin.getStyleClass().add("danger");
		}else {
			btnBretResetPin.getStyleClass().remove("danger");
			btnBretResetPin.getStyleClass().add("success");
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
				driver.get(BretSettings.WEBADMIN_LINK);
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
