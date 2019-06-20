package dzhu.controller;

import java.io.IOException;

import dzhu.settings.LofxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LofxDevmonController {
	@FXML
	private Button btnLofxCompareOrders=null;
	@FXML
	private Button btnLofxLogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnLofxLogonDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	@FXML
	public void btnLofxCompareOrdersClicked(Event e){
		//to do
		btnLofxCompareOrders.setText("Not implement yet");
	}

	@FXML
	public void btnLofxLogonDevmonClicked(Event e){
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = LofxSettings.DEVMONADMIN_USERNAME + ";" +
						   LofxSettings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + LofxSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		ControllerUtils.showStages(getParentStage());
		btnLofxLogonDevmon.getStyleClass().add("success");
	}
}
