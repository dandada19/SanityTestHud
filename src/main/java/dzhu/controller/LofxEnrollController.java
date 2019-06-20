package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.LofxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LofxEnrollController {
	@FXML
	private Button btnLofxEnroll=null;
	@FXML
	private Button btnLofxLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnLofxEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
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
	
	private WebDriver initDriver() {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.ignoreZoomSettings();
		driver = new InternetExplorerDriver(options);
		return driver;
	}
	
	@FXML
	public void btnLofxEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		btnLofxEnroll.getStyleClass().remove("success");
		
		String agentArgs = LofxSettings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + LofxSettings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnLofxEnroll.getStyleClass().add("success");
	}
	
	@FXML
	public void btnLofxLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());		
		btnLofxLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(LofxSettings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnLofxLaunchIe.getStyleClass().add("success");
	}
}
