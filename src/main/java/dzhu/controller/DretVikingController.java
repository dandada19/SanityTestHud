package dzhu.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class DretVikingController {
	@FXML
	private Hyperlink linkDretVikingInstall = null;

	
	@FXML
	public void linkDretVikingInstallClicked(Event e) throws IOException, URISyntaxException {
		WebView b = new WebView();
		WebEngine engine = b.getEngine();
		System.out.println(linkDretVikingInstall.getText());
		engine.load(linkDretVikingInstall.getText());

		Desktop.getDesktop().browse(new URI(linkDretVikingInstall.getText()));
	}

}
