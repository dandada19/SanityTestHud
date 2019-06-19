package dzhu.controller;

import java.io.IOException;

import dzhu.settings.BretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BretDevmonController {
	@FXML
	private Button btnBretCompareOrders=null;
	@FXML
	private Button btnBretLaunchDevmon=null;
	@FXML
	private Button btnBretLogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnBretLaunchDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	@FXML
	public void btnBretCompareOrdersClicked(Event e){
		//to do
		btnBretCompareOrders.setText("Not implement yet");
	}
	
	@FXML
	public void btnBretLaunchDevmonClicked(Event e){
		ControllerUtils.hideStages(getParentStage());
		try {
			Runtime.getRuntime().exec("javaws " + BretSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ControllerUtils.showStages(getParentStage());
		btnBretLaunchDevmon.getStyleClass().add("success");
	}


	@FXML
	public void btnBretLogonDevmonClicked(Event e){
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = BretSettings.DEVMONADMIN_USERNAME + ";" +
						   BretSettings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + BretSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		ControllerUtils.showStages(getParentStage());
		btnBretLogonDevmon.getStyleClass().add("success");
	}
}
