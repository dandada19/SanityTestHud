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

public class PretQtpController {
	@FXML
	private Button btnPretQtpRetailIc=null;
	@FXML
	private Button btnPretQtpLondonAsiaRegionIcLogin=null;
	@FXML
	private Button btnPretQtpE2EELogin=null;
	@FXML
	private Button btnPretQtpRetailMargin=null;
	@FXML
	private Button btnPretQtpRetailWs=null;
	@FXML
	private Button btnPretQtpRetailAdminTool=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnPretQtpRetailIc.getScene().getWindow();
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
	public void btnPretQtpRetailIcClicked(Event e) {
		runQtpTest(btnPretQtpRetailIc, "FX_RetailIC", "PRET", "SMOKE", "qtpGr_RetailIC");
	}
	@FXML
	public void btnPretQtpLondonAsiaRegionIcLoginClicked(Event e) {
		runQtpTest(btnPretQtpLondonAsiaRegionIcLogin, "FX_RetailIC", "PRET", "SMOKE", "qtpGr_LondonAsiaRegionICLogin");
	}
	@FXML
	public void btnPretQtpE2EELoginClicked(Event e) {
		runQtpTest(btnPretQtpE2EELogin, "FX_RetailIC", "PRET", "SMOKE", "qtpGr_E2EELogin");
	}
	@FXML
	public void btnPretQtpRetailMarginClicked(Event e) {
		runQtpTest(btnPretQtpRetailMargin, "FX_RetailMargin", "PRET", "SMOKE", "qtpGr_RetailMargin");
	}
	@FXML
	public void btnPretQtpRetailWsClicked(Event e) {
		runQtpTest(btnPretQtpRetailWs, "FX_RetailWS", "PRET", "SMOKE", "qtpGr_RetailWS");
	}
	@FXML
	public void btnPretQtpRetailAdminToolClicked(Event e) {
		runQtpTest(btnPretQtpRetailAdminTool, "FX_RetailAdminTool", "PRET", "SMOKE", "qtpGr_Enroll");
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
