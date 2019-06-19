package dzhu.controller;

import java.io.IOException;

import dzhu.settings.BretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BretMarketDfController {
	@FXML
	private Button btnBretLogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnBretLogonMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnBretLogonMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = BretSettings.MDFADMIN_USERNAME + ";" +
				   BretSettings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + BretSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnBretLogonMarketDf.getStyleClass().add("success");
	}	
}
