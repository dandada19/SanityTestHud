package dzhu.controller;

import java.io.IOException;

import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Int2MarketDfController {
	@FXML
	private Button btnInt2LaunchMarketDf=null;
	@FXML
	private Button btnInt2LogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnInt2LaunchMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnInt2LaunchMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			Runtime.getRuntime().exec("javaws " + Int2Settings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ControllerUtils.showStages(getParentStage());
		btnInt2LaunchMarketDf.getStyleClass().add("success");
	}
	
	@FXML
	public void btnInt2LogonMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = Int2Settings.MDFADMIN_USERNAME + ";" +
				   Int2Settings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + Int2Settings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnInt2LogonMarketDf.getStyleClass().add("success");
	}	
}
