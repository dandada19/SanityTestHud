package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.PretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PretEnrollController {
	@FXML
	private Button btnPretEnroll=null;
	@FXML
	private Button btnPretLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
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
		getParentStage().hide();
		btnPretEnroll.getStyleClass().remove("success");
		
		String agentArgs = PretSettings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + PretSettings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		getParentStage().show();
		btnPretEnroll.getStyleClass().add("success");
	}
	
	@FXML
	public void btnPretLaunchIeClicked(Event e) {
		getParentStage().hide();		
		btnPretLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(PretSettings.ENROLL_PAGE_LINK);
		getParentStage().show();
		btnPretLaunchIe.getStyleClass().add("success");
	}
}
