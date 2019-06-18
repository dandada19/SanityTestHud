package dzhu.controller;

import java.io.IOException;

import dzhu.settings.DretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DretDevmonController {
	@FXML
	private Button btnDretCompareOrders=null;
	@FXML
	private Button btnDretLaunchDevmon=null;
	@FXML
	private Button btnDretLogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnDretLaunchDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	@FXML
	public void btnDretCompareOrdersClicked(Event e){
		//to do
		btnDretCompareOrders.setText("Not implement yet");
	}
	
	@FXML
	public void btnDretLaunchDevmonClicked(Event e){
		getParentStage().hide();
		try {
			Runtime.getRuntime().exec("javaws " + DretSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		getParentStage().show();
		btnDretLaunchDevmon.getStyleClass().add("success");
	}


	@FXML
	public void btnDretLogonDevmonClicked(Event e){
		getParentStage().hide();
		
		String agentArgs = DretSettings.DEVMONADMIN_USERNAME + ";" +
						   DretSettings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + DretSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		getParentStage().show();
		btnDretLogonDevmon.getStyleClass().add("success");
	}
}
