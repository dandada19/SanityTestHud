package dzhu.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.GlobalSettings;
import dzhu.settings.LofxSettings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LofxEnrollController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnLofxEnroll=null;
	@FXML
	private Button btnLofxLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnLofxEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() throws Exception{
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(LofxSettings.ENROLL_PAGE_LINK);
			}catch(Exception e){
				//browser may be closed manually.
				driver = initDriver();
			}
			return driver;
		}
	}
	
	private WebDriver initDriver() throws Exception{
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.ignoreZoomSettings();
		driver = new InternetExplorerDriver(options);
		return driver;
	}
	
	@FXML
	public void btnLofxEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());

		boolean ret = ControllerUtils.doEnroll(LofxSettings.ENROLL_PAGE_LINK, 
				LofxSettings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);

		ControllerUtils.showStages(getParentStage());
		if(ret) {
			btnLofxEnroll.getStyleClass().remove("danger");
			btnLofxEnroll.getStyleClass().add("success");
		}else {
			btnLofxEnroll.getStyleClass().remove("success");
			btnLofxEnroll.getStyleClass().add("danger");
		}
	}
	
	@FXML
	public void btnLofxLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(LofxSettings.ENROLL_PAGE_LINK);
		}catch(Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnLofxLaunchIe.getStyleClass().remove("success");
			btnLofxLaunchIe.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnLofxLaunchIe.getStyleClass().remove("danger");
		btnLofxLaunchIe.getStyleClass().add("success");
	}
}
