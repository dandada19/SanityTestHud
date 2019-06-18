package dzhu.controller;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.PretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PretWebAdminLoginController {
	@FXML
	private Button btnPretLaunchWebAdmin=null;
	@FXML
	private Button btnPretLogonWebAdmin=null;
	@FXML
	private Button btnPretResetPin=null;
	@FXML
	private Label lbPretWebadminResult=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnPretLaunchWebAdmin.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnPretLaunchWebAdminClicked(Event e) {
		getParentStage().hide();
		driver = getDriver();
		driver.get(PretSettings.WEBADMIN_LINK);
		getParentStage().show();
		btnPretLaunchWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnPretLogonWebAdminClicked(Event e) {
		getParentStage().hide();
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
		driver.get(PretSettings.WEBADMIN_LINK);
		
		logonWebAdmin(PretSettings.WEBADMIN_USERNAME, PretSettings.WEBADMIN_PASSWORD);
		getParentStage().show();
		btnPretLogonWebAdmin.getStyleClass().add("success");
	}
	
	@FXML
	public void btnPretResetPinClicked(Event e) {
		getParentStage().hide();
		lbPretWebadminResult.setText("");
		
		boolean ret = resetPin("qtpicuser" , PretSettings.ENROLLMENT_USERNAME, "test1234");
		if (ret==false) {
			lbPretWebadminResult.setText("You need to login before reset pin");
			btnPretResetPin.getStyleClass().remove("success");
			btnPretResetPin.getStyleClass().add("danger");
		}else {
			btnPretResetPin.getStyleClass().remove("danger");
			btnPretResetPin.getStyleClass().add("success");
		}
		getParentStage().show();
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
				driver.get(PretSettings.WEBADMIN_LINK);
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
