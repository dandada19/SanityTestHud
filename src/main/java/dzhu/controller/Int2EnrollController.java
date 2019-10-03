package dzhu.controller;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.sikuli.script.FindFailed;

import dzhu.settings.GlobalSettings;
import dzhu.settings.Int2Settings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Int2EnrollController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Label lb3=null;
	@FXML
	private Button btnInt2LaunchEnrollPage=null;
	@FXML
	private Button btnInt2LaunchEnrollApp=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
		lb3.setText(SettingsUtil.replaceTextWithActualUserSettings(lb3.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchEnrollPage.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(Int2Settings.ENROLL_PAGE_LINK);
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
	public void btnInt2LaunchEnrollPageClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
		driver.get(Int2Settings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchEnrollPage.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LaunchEnrollAppClicked(Event e) throws FindFailed {
		ControllerUtils.hideStages(getParentStage());
		btnInt2LaunchEnrollApp.getStyleClass().remove("success");
		
		ControllerUtils.doEnroll(Int2Settings.ENROLL_PAGE_LINK, 
				Int2Settings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);

		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchEnrollApp.getStyleClass().add("success");
	}
}
