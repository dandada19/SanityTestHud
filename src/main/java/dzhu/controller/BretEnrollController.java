package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.BretSettings;
import dzhu.settings.GlobalSettings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BretEnrollController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Button btnBretEnroll=null;
	@FXML
	private Button btnBretLaunchIe=null;
	
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
			parentStage = (Stage)btnBretEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(BretSettings.ENROLL_PAGE_LINK);
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
		options.requireWindowFocus();
		driver = new InternetExplorerDriver(options);
		return driver;
	}
	
	@FXML
	public void btnBretEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		btnBretEnroll.getStyleClass().remove("success");
		
		boolean ret = ControllerUtils.doEnroll(BretSettings.ENROLL_PAGE_LINK, 
				BretSettings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);

		ControllerUtils.showStages(getParentStage());
		if(ret) {
			btnBretEnroll.getStyleClass().remove("danger");
			btnBretEnroll.getStyleClass().add("success");
		}else {
			btnBretEnroll.getStyleClass().remove("danger");
			btnBretEnroll.getStyleClass().add("fail");
		}
	}
	
	@FXML
	public void btnBretLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());		
		btnBretLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(BretSettings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnBretLaunchIe.getStyleClass().add("success");
	}
	
	@Deprecated
	public void btnBretEnrollClicked_old(Event e) {
		ControllerUtils.hideStages(getParentStage());
		btnBretEnroll.getStyleClass().remove("success");
		
		String agentArgs = BretSettings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + BretSettings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnBretEnroll.getStyleClass().add("success");
	}
}
