package dzhu.controller;

import java.io.IOException;
import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Int2DevmonController {
	@FXML
	private Button btnInt2LaunchDevmon=null;
	@FXML
	private Button btnInt2LogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnInt2LaunchDevmonClicked(Event e){
		getParentStage().hide();
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		getParentStage().show();
		btnInt2LaunchDevmon.getStyleClass().add("success");
	}


	@FXML
	public void btnInt2LogonDevmonClicked(Event e){
		getParentStage().hide();
		
		String agentArgs = Int2Settings.DEVMONADMIN_USERNAME + ";" +
						   Int2Settings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + Int2Settings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		getParentStage().show();
		btnInt2LogonDevmon.getStyleClass().add("success");
	}
}
