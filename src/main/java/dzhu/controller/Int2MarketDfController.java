package dzhu.controller;

import java.io.IOException;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Int2MarketDfController {
	@FXML
	private Button btnInt2LaunchMarketDf=null;
	@FXML
	private Button btnInt2LogonMarketDf=null;
	
	@FXML
	public void btnInt2LaunchMarketDfClicked(Event e) throws FindFailed {
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		btnInt2LaunchMarketDf.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonMarketDfClicked(Event e) throws FindFailed {
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		launchAndLogin("dzhu", "test1234");
		btnInt2LogonMarketDf.getStyleClass().add("success");
	}
	
	private void launchAndLogin(String user, String password) {
		Screen s = new Screen();
		String elementsFolderPath = "src/main/resources/sikuli_elements/";
        Pattern btnRun = new Pattern(elementsFolderPath + "common_button_run.PNG");
        Pattern textUserName = new Pattern(elementsFolderPath + "common_text_username.PNG");
        textUserName.similar((float) 0.9);
        textUserName.targetOffset(150, 0);
        Pattern textPassword = new Pattern(elementsFolderPath + "common_text_password.PNG");
        Pattern btnLogon = new Pattern(elementsFolderPath + "common_button_logon.PNG");
        try {
        	btnRun.similar((float)0.8);
        	s.wait(btnRun, 15);
            s.click(btnRun);
        }catch(FindFailed ff) {
        	//run button may not be present sometimes.
        }
        try {
	        s.wait(textUserName, 15);
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
