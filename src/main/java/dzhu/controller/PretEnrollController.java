package dzhu.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.GlobalSettings;
import dzhu.settings.PretSettings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PretEnrollController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnPretEnroll=null;
	@FXML
	private Button btnPretLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnPretEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(PretSettings.ENROLL_PAGE_LINK);
			}catch(Exception e){
				//browser may be closed manually.
				driver = initDriver();
			}
			return driver;
		}
	}
	
	private WebDriver initDriver() {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.ignoreZoomSettings();
		driver = new InternetExplorerDriver(options);
		return driver;
	}
	
	@FXML
	public void btnPretEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		btnPretEnroll.getStyleClass().remove("success");

		boolean ret = ControllerUtils.doEnroll(PretSettings.ENROLL_PAGE_LINK, 
				PretSettings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);

		ControllerUtils.showStages(getParentStage());
		if(ret) {
			btnPretEnroll.getStyleClass().remove("danger");
			btnPretEnroll.getStyleClass().add("success");
		}else {
			btnPretEnroll.getStyleClass().remove("success");
			btnPretEnroll.getStyleClass().add("danger");
		}
	}
	
	@FXML
	public void btnPretLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());		
		btnPretLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(PretSettings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnPretLaunchIe.getStyleClass().add("success");
	}
}
