package dzhu.controller;

import java.io.IOException;

import dzhu.settings.TkfxSettings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TkfxDevmonController {
	@FXML
	private Button btnTkfxCompareOrders=null;
	@FXML
	private Button btnTkfxLaunchDevmon=null;
	@FXML
	private Button btnTkfxLogonDevmon=null;
	
	private Stage parentStage = null;
	
	private Stage getParentStage() {
		if(parentStage == null) {
			parentStage = (Stage)btnTkfxLaunchDevmon.getScene().getWindow();
		}
		return parentStage;
	}
	@FXML
	public void btnTkfxCompareOrdersClicked(Event e){
		//to do
		btnTkfxCompareOrders.setText("Not implement yet");
	}
	
	@FXML
	public void btnTkfxLaunchDevmonClicked(Event e){
		getParentStage().hide();
		try {
			Runtime.getRuntime().exec("javaws " + TkfxSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		getParentStage().show();
		btnTkfxLaunchDevmon.getStyleClass().add("success");
	}


	@FXML
	public void btnTkfxLogonDevmonClicked(Event e){
		getParentStage().hide();
		
		String agentArgs = TkfxSettings.DEVMONADMIN_USERNAME + ";" +
						   TkfxSettings.DEVMONADMIN_PASSWORD;
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c set JAVA_TOOL_OPTIONS=-javaagent:ClassicAgent.jar" +
					"=" + agentArgs +
					"&& javaws " + TkfxSettings.DEVMON_LINK);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		getParentStage().show();
		btnTkfxLogonDevmon.getStyleClass().add("success");
	}
}
