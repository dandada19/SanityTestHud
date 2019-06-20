package dzhu.controller;

import java.io.IOException;

import dzhu.settings.LofxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LofxMarketDfController {
	@FXML
	private Button btnLofxLogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnLofxLogonMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnLofxLogonMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = LofxSettings.MDFADMIN_USERNAME + ";" +
				   LofxSettings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + LofxSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnLofxLogonMarketDf.getStyleClass().add("success");
	}	
}
