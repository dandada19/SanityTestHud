package dzhu.controller;

import java.io.IOException;

import dzhu.settings.TkfxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TkfxMarketDfController {
	@FXML
	private Button btnTkfxLogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnTkfxLogonMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnTkfxLogonMarketDfClicked(Event e) {
		getParentStage().hide();
		
		String agentArgs = TkfxSettings.MDFADMIN_USERNAME + ";" +
				   TkfxSettings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + TkfxSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		getParentStage().show();
		btnTkfxLogonMarketDf.getStyleClass().add("success");
	}	
}
