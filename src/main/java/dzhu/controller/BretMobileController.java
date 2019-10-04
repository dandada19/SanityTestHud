package dzhu.controller;

import dzhu.settings.SettingsUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BretMobileController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;

	
	@FXML
	public void initialize() {
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
}
