package dzhu.controller;


import dzhu.settings.SettingsUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DretMobileController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;

	
	@FXML
	public void initialize() {
		//replace [#BretSettings.ENROLLMENT_USERNAME#] with actual username
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}

}
