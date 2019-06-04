package dzhu.controller;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import dzhu.settings.GlobalSettings;
import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Int2ClassicController {
	@FXML
	private Button btnInt2LaunchClassicTaker=null;
	@FXML
	private Button btnInt2LaunchClassicMaker=null;
	@FXML
	private Button btnInt2LaunchQtpPortal=null;
	private WebDriver driver = null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchClassicTaker.getScene().getWindow();
		}
		return parentStage;
	}
	
	private WebDriver getDriver() {
		if(driver==null) {
			return initDriver();
		}else {
			try {
				driver.get(GlobalSettings.QTP_PORTAL_LINK);
			}catch(Exception e){
				//browser may be closed manually.
				driver = initDriver();
			}
			return driver;
		}
	}
	
	private WebDriver initDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}
	
	@FXML
	public void btnInt2LaunchClassicTakerClicked(Event e) throws FindFailed {
		getParentStage().hide();
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.CLASSIC_TAKER_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//launchAndLoginGui(Int2Settings.CLASSICTAKER_USERNAME, Int2Settings.CLASSICTAKER_PASSWORD);
		getParentStage().show();
		btnInt2LaunchClassicTaker.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LaunchClassicMakerClicked(Event e) {
		getParentStage().hide();
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.CLASSIC_MAKER_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		getParentStage().show();
		btnInt2LaunchClassicMaker.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LaunchQtpPortalClicked(Event e) {
		getParentStage().hide();
		driver = getDriver();
		driver.get(GlobalSettings.QTP_PORTAL_LINK);
		getParentStage().show();
		btnInt2LaunchQtpPortal.getStyleClass().add("success");
	}
	
	@Deprecated
	private void launchAndLoginGuiSikuli(String user, String password) {
		Screen s = new Screen();
		String elementsFolderPath = "src/main/resources/sikuli_elements/";
        Pattern btnRun = new Pattern(elementsFolderPath + "classic_button_run.PNG");
        Pattern textUserName = new Pattern(elementsFolderPath + "classic_text_username.PNG");
        textUserName.similar((float) 0.9);
        textUserName.targetOffset(250, 0);
        Pattern textPassword = new Pattern(elementsFolderPath + "classic_text_password.PNG");
        Pattern btnLogon = new Pattern(elementsFolderPath + "classic_button_logon.PNG");
        try {
        	btnRun.similar((float)0.8);
        	s.wait(btnRun, 20);
            s.click(btnRun);
        }catch(FindFailed ff) {
        	//run button may not be present sometimes.
        }
        try {
	        s.wait(textUserName, 20);
	        s.doubleClick(textUserName);
	        s.type(Key.DELETE);
	        s.doubleClick(textUserName);
	        s.type(Key.DELETE);
	        s.type(textUserName, user);
	        s.type(textPassword, password);
	        s.click(btnLogon);
        }catch(FindFailed ff) {
        	ff.printStackTrace();
        }
	}
}
