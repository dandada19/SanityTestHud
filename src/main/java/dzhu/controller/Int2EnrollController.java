package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

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
				driver.get("https://integration2-dl.currenex.com/enroll-int2/enroll.html");
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
		driver.get("https://integration2-dl.currenex.com/enroll-int2/enroll.html");
	}
	
	@FXML
	public void btnInt2LaunchEnrollAppClicked(Event e) throws FindFailed {
		try {
			Runtime.getRuntime().exec("javaws https://integration2-dl.currenex.com/webstart/enroll.jnlp");
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
        s.wait(checkboxAgree, 60);
        s.click(checkboxAgree);
        s.click(btnSubmit);
        s.wait(textUserName, 50);
        s.type(textUserName, "qtpint2_enroll");
        s.type(textPin, "test1234");
        s.type(textEmail, "qa@currenex.com");
        s.click(btnEnroll);        
	}
}
