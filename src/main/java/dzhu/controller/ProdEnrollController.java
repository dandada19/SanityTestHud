package dzhu.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import dzhu.settings.GlobalSettings;
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

		boolean ret = ControllerUtils.doEnroll(ProdSettings.ENROLL_PAGE_LINK, 
				ProdSettings.ENROLLMENT_USERNAME, GlobalSettings.COMMON_PIN);

		ControllerUtils.showStages(getParentStage());

		if(ret) {
			btnProdEnroll.getStyleClass().remove("danger");
			btnProdEnroll.getStyleClass().add("success");
		}else {
			btnProdEnroll.getStyleClass().remove("success");
			btnProdEnroll.getStyleClass().add("danger");
		}
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
