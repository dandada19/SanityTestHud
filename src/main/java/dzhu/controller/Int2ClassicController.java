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

public class Int2ClassicController {
	@FXML
	private Button btnInt2LaunchClassicTaker=null;
	@FXML
	private Button btnInt2LaunchClassicMaker=null;
	
	@FXML
	public void btnInt2LaunchClassicTakerClicked(Event e) throws FindFailed {
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.CLASSIC_TAKER_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		launchAndLoginGui("qtpint2_enroll", "test1234");
		btnInt2LaunchClassicTaker.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LaunchClassicMakerClicked(Event e) {
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.CLASSIC_MAKER_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		launchAndLoginGui("qalbank3u1", "test1234");
		btnInt2LaunchClassicMaker.getStyleClass().add("success");
	}
	
	private void launchAndLoginGui(String user, String password) {
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
	        s.type(textUserName, user);
	        s.type(textPassword, password);
	        s.click(btnLogon);
        }catch(FindFailed ff) {
        	ff.printStackTrace();
        }
	}
}