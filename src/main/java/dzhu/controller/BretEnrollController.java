package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.BretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BretEnrollController {
	@FXML
	private Button btnBretEnroll=null;
	@FXML
	private Button btnBretLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
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
		driver = new InternetExplorerDriver(options);
		return driver;
	}
	
	@FXML
	public void btnBretEnrollClicked(Event e) {
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
	
	@FXML
	public void btnBretLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());		
		btnBretLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(BretSettings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnBretLaunchIe.getStyleClass().add("success");
	}
}
