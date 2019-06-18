package dzhu.controller;

import java.io.IOException;

import dzhu.settings.DretSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DretMarketDfController {
	@FXML
	private Button btnDretLogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnDretLogonMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnDretLogonMarketDfClicked(Event e) {
		getParentStage().hide();
		
		String agentArgs = DretSettings.MDFADMIN_USERNAME + ";" +
				   DretSettings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + DretSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		getParentStage().show();
		btnDretLogonMarketDf.getStyleClass().add("success");
	}	
}
