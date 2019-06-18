package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.TkfxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TkfxEnrollController {
	@FXML
	private Button btnTkfxEnroll=null;
	@FXML
	private Button btnTkfxLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnTkfxEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
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
	
	private WebDriver initDriver() {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.ignoreZoomSettings();
		driver = new InternetExplorerDriver(options);
		return driver;
	}
	
	@FXML
	public void btnTkfxEnrollClicked(Event e) {
		getParentStage().hide();
		btnTkfxEnroll.getStyleClass().remove("success");
		
		String agentArgs = TkfxSettings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + TkfxSettings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		getParentStage().show();
		btnTkfxEnroll.getStyleClass().add("success");
	}
	
	@FXML
	public void btnTkfxLaunchIeClicked(Event e) {
		getParentStage().hide();		
		btnTkfxLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(TkfxSettings.ENROLL_PAGE_LINK);
		getParentStage().show();
		btnTkfxLaunchIe.getStyleClass().add("success");
	}
}
