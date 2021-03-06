package dzhu.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dzhu.settings.GlobalSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BretQtpController {
	@FXML
	private Button btnBretQtpRetailIc=null;
	@FXML
	private Button btnBretQtpLondonAsiaRegionIcLogin=null;
	@FXML
	private Button btnBretQtpE2EELogin=null;
	@FXML
	private Button btnBretQtpRetailMargin=null;
	@FXML
	private Button btnBretQtpRetailWs=null;
	@FXML
	private Button btnBretQtpRetailAdminTool=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnBretQtpRetailIc.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() throws Exception{
		if(driver==null) {			
			return initDriver();
		}else {
			try {
				driver.get(GlobalSettings.QTP_PORTAL_LINK);
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
	public void btnBretQtpRetailIcClicked(Event e) {
		runQtpTest(btnBretQtpRetailIc, "FX_RetailIC", "BRET", "SMOKE", "qtpGr_RetailIC");
	}
	@FXML
	public void btnBretQtpLondonAsiaRegionIcLoginClicked(Event e) {
		runQtpTest(btnBretQtpLondonAsiaRegionIcLogin, "FX_RetailIC", "BRET", "SMOKE", "qtpGr_LondonAsiaRegionICLogin");
	}
	@FXML
	public void btnBretQtpE2EELoginClicked(Event e) {
		runQtpTest(btnBretQtpE2EELogin, "FX_RetailIC", "BRET", "SMOKE", "qtpGr_E2EELogin");
	}
	@FXML
	public void btnBretQtpRetailMarginClicked(Event e) {
		runQtpTest(btnBretQtpRetailMargin, "FX_RetailMargin", "BRET", "SMOKE", "qtpGr_RetailMargin");
	}
	@FXML
	public void btnBretQtpRetailWsClicked(Event e) {
		runQtpTest(btnBretQtpRetailWs, "FX_RetailWS", "BRET", "SMOKE", "qtpGr_RetailWS");
	}
	@FXML
	public void btnBretQtpRetailAdminToolClicked(Event e) {
		runQtpTest(btnBretQtpRetailAdminTool, "FX_RetailAdminTool", "BRET", "SMOKE", "qtpGr_Enroll");
	}

	private void runQtpTest(Button btnClicked, String product, String stack, String suite, String test) {
		ControllerUtils.hideStages(getParentStage());
		btnClicked.getStyleClass().remove("success");
		
		try {
			driver = getDriver();
			driver.get(GlobalSettings.QTP_PORTAL_LINK);	
			WebDriverWait wait = new WebDriverWait(driver, 10);	
			QtpUtils.runQtpTest(wait, product, stack, suite, test);
		}catch(Exception ex) {
			ex.printStackTrace();
			ControllerUtils.showStages(getParentStage());
			btnClicked.getStyleClass().remove("success");
			btnClicked.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnClicked.getStyleClass().remove("danger");
		btnClicked.getStyleClass().add("success");
	}
}
