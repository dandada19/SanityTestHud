package dzhu.controller;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.SettingsUtil;
import dzhu.settings.TkfxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TkfxWebAdminLoginController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnTkfxLaunchWebAdmin=null;
	@FXML
	private Button btnTkfxLogonWebAdmin=null;
	@FXML
	private Button btnTkfxResetPin=null;
	@FXML
	private Label lbTkfxWebadminResult=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnTkfxLaunchWebAdmin.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnTkfxLaunchWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
		driver.get(TkfxSettings.WEBADMIN_LINK);
		ControllerUtils.showStages(getParentStage());
		btnTkfxLaunchWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnTkfxLogonWebAdminClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
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
		driver.get(TkfxSettings.WEBADMIN_LINK);
		
		logonWebAdmin(TkfxSettings.WEBADMIN_USERNAME, TkfxSettings.WEBADMIN_PASSWORD);
		ControllerUtils.showStages(getParentStage());
		btnTkfxLogonWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnTkfxResetPinClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		lbTkfxWebadminResult.setText("");
		
		boolean ret = resetPin("qtpicuser" , TkfxSettings.ENROLLMENT_USERNAME, "test1234");
		if (ret==false) {
			lbTkfxWebadminResult.setText("You need to login before reset pin");
			btnTkfxResetPin.getStyleClass().remove("success");
			btnTkfxResetPin.getStyleClass().add("danger");
		}else {
			btnTkfxResetPin.getStyleClass().remove("danger");
			btnTkfxResetPin.getStyleClass().add("success");
		}
		ControllerUtils.showStages(getParentStage());
	}
	
	private void logonWebAdmin(String user, String pwd) {
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

			boolean isUserExist = false;
			for (WebElement li : countriesList) {
				WebElement a;
				try {
					a = li.findElement(By.tagName("a"));
				}catch(Exception e) {
					continue;
				}
				
				if (a.getAttribute("innerHTML").contains(user)) {
					li.click();
					isUserExist = true;
				}
			}
			if(!isUserExist) {
				System.out.println("user " + user + " doesn't exist in the dropdown list.");
				return false;
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
	
	private WebDriver getDriver() {
		if(driver==null) {			
			return initDriver();
		}else {
			try {
				driver.get(TkfxSettings.WEBADMIN_LINK);
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
}
