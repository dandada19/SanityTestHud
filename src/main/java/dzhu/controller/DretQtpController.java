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

public class DretQtpController {
	@FXML
	private Button btnDretQtpRetailIc=null;
	@FXML
	private Button btnDretQtpLondonAsiaRegionIcLogin=null;
	@FXML
	private Button btnDretQtpE2EELogin=null;
	@FXML
	private Button btnDretQtpRetailMargin=null;
	@FXML
	private Button btnDretQtpRetailWs=null;
	@FXML
	private Button btnDretQtpRetailAdminTool=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnDretQtpRetailIc.getScene().getWindow();
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
	public void btnDretQtpRetailIcClicked(Event e) {
		runQtpTest(btnDretQtpRetailIc, "FX_RetailIC", "DRET", "SMOKE", "qtpGr_RetailIC");
	}
	@FXML
	public void btnDretQtpLondonAsiaRegionIcLoginClicked(Event e) {
		runQtpTest(btnDretQtpLondonAsiaRegionIcLogin, "FX_RetailIC", "DRET", "SMOKE", "qtpGr_LondonAsiaRegionICLogin");
	}
	@FXML
	public void btnDretQtpE2EELoginClicked(Event e) {
		runQtpTest(btnDretQtpE2EELogin, "FX_RetailIC", "DRET", "SMOKE", "qtpGr_E2EELogin");
	}
	@FXML
	public void btnDretQtpRetailMarginClicked(Event e) {
		runQtpTest(btnDretQtpRetailMargin, "FX_RetailMargin", "DRET", "SMOKE", "qtpGr_RetailMargin");
	}
	@FXML
	public void btnDretQtpRetailWsClicked(Event e) {
		runQtpTest(btnDretQtpRetailWs, "FX_RetailWS", "DRET", "SMOKE", "qtpGr_RetailWS");
	}
	@FXML
	public void btnDretQtpRetailAdminToolClicked(Event e) {
		runQtpTest(btnDretQtpRetailAdminTool, "FX_RetailAdminTool", "DRET", "SMOKE", "qtpGr_Enroll");
	}

	private void runQtpTest(Button btnClicked, String product, String stack, String suite, String test) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(GlobalSettings.QTP_PORTAL_LINK);
	
			WebDriverWait wait = new WebDriverWait(driver, 10);
			QtpUtils.runQtpTest(wait, product, stack, suite, test);
		}catch(Exception ex) {
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
