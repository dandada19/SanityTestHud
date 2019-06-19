package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.DretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DretEnrollController {
	@FXML
	private Button btnDretEnroll=null;
	@FXML
	private Button btnDretLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
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
		
		String agentArgs = DretSettings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + DretSettings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnDretEnroll.getStyleClass().add("success");
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
