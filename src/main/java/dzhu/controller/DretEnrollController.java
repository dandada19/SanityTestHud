package dzhu.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.DretSettings;
import dzhu.settings.GlobalSettings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DretEnrollController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnDretEnroll=null;
	@FXML
	private Button btnDretLaunchIe=null;
	
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
			parentStage = (Stage)btnDretEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(DretSettings.ENROLL_PAGE_LINK);
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
	public void btnDretEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		btnDretEnroll.getStyleClass().remove("success");		

		boolean ret = ControllerUtils.doEnroll(DretSettings.ENROLL_PAGE_LINK, 
				DretSettings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);

		ControllerUtils.showStages(getParentStage());
		if(ret) {
			btnDretEnroll.getStyleClass().remove("danger");
			btnDretEnroll.getStyleClass().add("success");
		}else {
			btnDretEnroll.getStyleClass().remove("danger");
			btnDretEnroll.getStyleClass().add("fail");
		}
	}
	
	@FXML
	public void btnDretLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());		
		btnDretLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(DretSettings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnDretLaunchIe.getStyleClass().add("success");
	}
}
