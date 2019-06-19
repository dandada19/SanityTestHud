package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.sikuli.script.FindFailed;
import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Int2EnrollController {
	@FXML
	private Button btnInt2LaunchEnrollPage=null;
	@FXML
	private Button btnInt2LaunchEnrollApp=null;
	
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchEnrollPage.getScene().getWindow();
		}
		return parentStage;
	}
	
	public WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(Int2Settings.ENROLL_PAGE_LINK);
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
	public void btnInt2LaunchEnrollPageClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		driver = getDriver();
		driver.get(Int2Settings.ENROLL_PAGE_LINK);
		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchEnrollPage.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LaunchEnrollAppClicked(Event e) throws FindFailed {
		ControllerUtils.hideStages(getParentStage());
		btnInt2LaunchEnrollApp.getStyleClass().remove("success");
		
		String agentArgs = Int2Settings.ENROLLMENT_USERNAME + ";" +
						   "test1234" + ";" + 
						   "qa@currenex.com";
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:EnrollAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + Int2Settings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
//		Screen s = new Screen();		
//		String elementsFolderPath = "resources/sikuli_elements/";//"src/main/resources/sikuli_elements/";
//
//        Pattern checkboxAgree = new Pattern(elementsFolderPath + "enroll_checkbox_agree.PNG");
//        Pattern btnSubmit = new Pattern(elementsFolderPath + "enroll_button_submit.PNG");
//        Pattern textUserName = new Pattern(elementsFolderPath + "enroll_text_username.PNG");
//        Pattern textPin = new Pattern(elementsFolderPath + "enroll_text_pin.PNG");
//        Pattern textEmail = new Pattern(elementsFolderPath + "enroll_text_email.PNG");
//        Pattern btnEnroll = new Pattern(elementsFolderPath + "enroll_button_enroll.PNG");
//
//        checkboxAgree.similar((float)0.8);
//        btnSubmit.similar((float)0.8);
//        textUserName.similar((float)0.8);
//        textUserName.targetOffset(100, 0);
//        textPin.similar((float)0.8);
//        textPin.targetOffset(100, 0);
//        textEmail.similar((float)0.8);
//        textEmail.targetOffset(100, 0);
//        btnEnroll.similar((float)0.8);
//        
//        s.wait(checkboxAgree, 30);
//        s.click(checkboxAgree);
//        s.click(btnSubmit);
//        s.wait(textUserName, 30);
//        s.type(textUserName, Int2Settings.ENROLLMENT_USERNAME);
//        s.type(textPin, Int2Settings.ENROLLMENT_PASSWORD);
//        s.type(textEmail, Int2Settings.EMAIL_ENROLL);
//        s.click(btnEnroll);

		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchEnrollApp.getStyleClass().add("success");
	}
}
