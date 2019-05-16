package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Int2EnrollController {
	@FXML
	private Button btnInt2LaunchEnrollPage=null;
	@FXML
	private Button btnInt2LaunchEnrollApp=null;
	
	private WebDriver driver = null;
	
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
		driver = getDriver();
		driver.get(Int2Settings.ENROLL_PAGE_LINK);

		btnInt2LaunchEnrollApp.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LaunchEnrollAppClicked(Event e) throws FindFailed {
		btnInt2LaunchEnrollApp.getStyleClass().remove("success");
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.ENROLL_APP_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Screen s = new Screen();		
		String elementsFolderPath = "src/main/resources/sikuli_elements/";
        Pattern checkboxAgree = new Pattern(elementsFolderPath + "enroll_checkbox_agree.PNG");
        Pattern btnSubmit = new Pattern(elementsFolderPath + "enroll_button_submit.PNG");
        Pattern textUserName = new Pattern(elementsFolderPath + "enroll_text_username.PNG");
        Pattern textPin = new Pattern(elementsFolderPath + "enroll_text_pin.PNG");
        Pattern textEmail = new Pattern(elementsFolderPath + "enroll_text_email.PNG");
        Pattern btnEnroll = new Pattern(elementsFolderPath + "enroll_button_enroll.PNG");

        checkboxAgree.similar((float)0.8);
        btnSubmit.similar((float)0.8);
        textUserName.similar((float)0.8);
        textUserName.targetOffset(100, 0);
        textPin.similar((float)0.8);
        textPin.targetOffset(100, 0);
        textEmail.similar((float)0.8);
        textEmail.targetOffset(100, 0);
        btnEnroll.similar((float)0.8);
        
        s.wait(checkboxAgree, 30);
        s.click(checkboxAgree);
        s.click(btnSubmit);
        s.wait(textUserName, 30);
        s.type(textUserName, "qtpint2_enroll");
        s.type(textPin, "test1234");
        s.type(textEmail, "qa@currenex.com");
        s.click(btnEnroll);
		btnInt2LaunchEnrollApp.getStyleClass().add("success");
	}
}
