package dzhu.controller;

import java.io.IOException;

import dzhu.settings.PretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PretMarketDfController {
	@FXML
	private Button btnPretLogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnPretLogonMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnPretLogonMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = PretSettings.MDFADMIN_USERNAME + ";" +
				   PretSettings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + PretSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnPretLogonMarketDf.getStyleClass().add("success");
	}	
}
