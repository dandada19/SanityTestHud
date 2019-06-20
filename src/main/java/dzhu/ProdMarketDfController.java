package dzhu.controller;

import java.io.IOException;

import dzhu.settings.ProdSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProdMarketDfController {
	@FXML
	private Button btnProdLaunchMarketDf=null;
	@FXML
	private Button btnProdLogonMarketDf=null;
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdLaunchMarketDf.getScene().getWindow();
		}
		return parentStage;
	}
	
	@FXML
	public void btnProdLaunchMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		try {
			Runtime.getRuntime().exec("javaws " + ProdSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ControllerUtils.showStages(getParentStage());
		btnProdLaunchMarketDf.getStyleClass().add("success");
	}
	
	@FXML
	public void btnProdLogonMarketDfClicked(Event e) {
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = ProdSettings.MDFADMIN_USERNAME + ";" +
				   ProdSettings.MDFADMIN_PASSWORD;

		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + ProdSettings.MDF_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ControllerUtils.showStages(getParentStage());
		btnProdLogonMarketDf.getStyleClass().add("success");
	}	
}
