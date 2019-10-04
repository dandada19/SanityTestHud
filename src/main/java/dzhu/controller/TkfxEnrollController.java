package dzhu.controller;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.GlobalSettings;
import dzhu.settings.SettingsUtil;
import dzhu.settings.TkfxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TkfxEnrollController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnTkfxEnroll=null;
	@FXML
	private Button btnTkfxLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnTkfxEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() throws Exception{
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(TkfxSettings.ENROLL_PAGE_LINK);
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
	public void btnTkfxEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());

		boolean ret = ControllerUtils.doEnroll(TkfxSettings.ENROLL_PAGE_LINK, 
				TkfxSettings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);
		
		ControllerUtils.showStages(getParentStage());
		if(ret) {
			btnTkfxEnroll.getStyleClass().remove("danger");
			btnTkfxEnroll.getStyleClass().add("success");
		}else {
			btnTkfxEnroll.getStyleClass().remove("success");
			btnTkfxEnroll.getStyleClass().add("danger");
		}
	}
	
	@FXML
	public void btnTkfxLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			driver = getDriver();
			driver.get(TkfxSettings.ENROLL_PAGE_LINK);
		}catch (Exception ex) {
			ControllerUtils.showStages(getParentStage());
			btnTkfxLaunchIe.getStyleClass().remove("success");
			btnTkfxLaunchIe.getStyleClass().add("danger");
			return;
		}
		ControllerUtils.showStages(getParentStage());
		btnTkfxLaunchIe.getStyleClass().remove("danger");
		btnTkfxLaunchIe.getStyleClass().add("success");
	}
}
