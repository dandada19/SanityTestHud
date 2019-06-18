package dzhu.controller;

import java.io.IOException;

import dzhu.settings.PretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PretDevmonController {
	@FXML
	private Button btnPretCompareOrders=null;
	@FXML
	private Button btnPretLaunchDevmon=null;
	@FXML
	private Button btnPretLogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnPretLaunchDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	@FXML
	public void btnPretCompareOrdersClicked(Event e){
		//to do
		btnPretCompareOrders.setText("Not implement yet");
	}
	
	@FXML
	public void btnPretLaunchDevmonClicked(Event e){
		getParentStage().hide();
		try {
			Runtime.getRuntime().exec("javaws " + PretSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		getParentStage().show();
		btnPretLaunchDevmon.getStyleClass().add("success");
	}


	@FXML
	public void btnPretLogonDevmonClicked(Event e){
		getParentStage().hide();
		
		String agentArgs = PretSettings.DEVMONADMIN_USERNAME + ";" +
						   PretSettings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + PretSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		getParentStage().show();
		btnPretLogonDevmon.getStyleClass().add("success");
	}
}
