package dzhu.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BretVikingController {
	@FXML
	private Label lb1=null;
	@FXML
	private Label lb2=null;
	@FXML
	private Hyperlink linkBretVikingInstall = null;
	
	@FXML
	public void initialize() {
		//replace [#BretSettings.ENROLLMENT_USERNAME#] with actual username
		lb1.setText(SettingsUtil.replaceTextWithActualUserSettings(lb1.getText()));
		lb2.setText(SettingsUtil.replaceTextWithActualUserSettings(lb2.getText()));
	}
	
	@FXML
	public void linkBretVikingInstallClicked(Event e) throws IOException, URISyntaxException {
		WebView b = new WebView();
		WebEngine engine = b.getEngine();
		System.out.println(linkBretVikingInstall.getText());
		engine.load(linkBretVikingInstall.getText());

		Desktop.getDesktop().browse(new URI(linkBretVikingInstall.getText()));
	}

}
