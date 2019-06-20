package dzhu.controller;

import java.io.IOException;

import dzhu.settings.ProdSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProdDevmonController {
	@FXML
	private Button btnProdCompareOrders=null;
	@FXML
	private Button btnProdLaunchDevmon=null;
	@FXML
	private Button btnProdLogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnProdLaunchDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	@FXML
	public void btnProdCompareOrdersClicked(Event e){
		//to do
		btnProdCompareOrders.setText("Not implement yet");
	}
	
	@FXML
	public void btnProdLaunchDevmonClicked(Event e){
		ControllerUtils.hideStages(getParentStage());
		try {
			Runtime.getRuntime().exec("javaws " + ProdSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ControllerUtils.showStages(getParentStage());
		btnProdLaunchDevmon.getStyleClass().add("success");
	}


	@FXML
	public void btnProdLogonDevmonClicked(Event e){
		ControllerUtils.hideStages(getParentStage());
		
		String agentArgs = ProdSettings.DEVMONADMIN_USERNAME + ";" +
						   ProdSettings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + ProdSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		ControllerUtils.showStages(getParentStage());
		btnProdLogonDevmon.getStyleClass().add("success");
	}
}
