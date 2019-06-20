package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.ProdSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProdEnrollController {
	@FXML
	private Button btnProdEnroll=null;
	@FXML
	private Button btnProdLaunchIe=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdEnroll.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(ProdSettings.ENROLL_PAGE_LINK);
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
	public void btnProdEnrollClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		btnProdEnroll.getStyleClass().remove("success");
		
		String agentArgs = ProdSettings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + ProdSettings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnProdEnroll.getStyleClass().add("success");
	}
	
	@FXML
	public void btnProdLaunchIeClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());		
		btnProdLaunchIe.getStyleClass().remove("success");

		driver = getDriver();
		driver.get(ProdSettings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnProdLaunchIe.getStyleClass().add("success");
	}
}
