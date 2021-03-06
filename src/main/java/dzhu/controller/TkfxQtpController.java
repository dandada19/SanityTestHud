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

public class TkfxQtpController {
	@FXML
	private Button btnTkfxQtpRetailIc=null;
	@FXML
	private Button btnTkfxQtpLondonAsiaRegionIcLogin=null;
	@FXML
	private Button btnTkfxQtpE2EELogin=null;
	@FXML
	private Button btnTkfxQtpRetailMargin=null;
	@FXML
	private Button btnTkfxQtpRetailWs=null;
	@FXML
	private Button btnTkfxQtpRetailAdminTool=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnTkfxQtpRetailIc.getScene().getWindow();
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
	public void btnTkfxQtpRetailIcClicked(Event e) {
		runQtpTest(btnTkfxQtpRetailIc, "FX_RetailIC", "TKFX", "SMOKE", "qtpGr_RetailIC");
	}
	@FXML
	public void btnTkfxQtpLondonAsiaRegionIcLoginClicked(Event e) {
		runQtpTest(btnTkfxQtpLondonAsiaRegionIcLogin, "FX_RetailIC", "TKFX", "SMOKE", "qtpGr_LondonAsiaRegionICLogin");
	}
	@FXML
	public void btnTkfxQtpE2EELoginClicked(Event e) {
		runQtpTest(btnTkfxQtpE2EELogin, "FX_RetailIC", "TKFX", "SMOKE", "qtpGr_E2EELogin");
	}
	@FXML
	public void btnTkfxQtpRetailMarginClicked(Event e) {
		runQtpTest(btnTkfxQtpRetailMargin, "FX_RetailMargin", "TKFX", "SMOKE", "qtpGr_RetailMargin");
	}
	@FXML
	public void btnTkfxQtpRetailWsClicked(Event e) {
		runQtpTest(btnTkfxQtpRetailWs, "FX_RetailWS", "TKFX", "SMOKE", "qtpGr_RetailWS");
	}
	@FXML
	public void btnTkfxQtpRetailAdminToolClicked(Event e) {
		runQtpTest(btnTkfxQtpRetailAdminTool, "FX_RetailAdminTool", "TKFX", "SMOKE", "qtpGr_Enroll");
	}

	private void runQtpTest(Button btnClicked, String product, String stack, String suite, String test) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(GlobalSettings.QTP_PORTAL_LINK);
	
			WebDriverWait wait = new WebDriverWait(driver, 10);
			QtpUtils.runQtpTest(wait, product, stack, suite, test);
		}catch (Exception ex) {
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
