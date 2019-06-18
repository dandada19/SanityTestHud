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

public class PretVikingController {
	@FXML
	private Hyperlink linkPretVikingInstall = null;

	
	@FXML
	public void linkPretVikingInstallClicked(Event e) throws IOException, URISyntaxException {
		WebView b = new WebView();
		WebEngine engine = b.getEngine();
		System.out.println(linkPretVikingInstall.getText());
		engine.load(linkPretVikingInstall.getText());

		Desktop.getDesktop().browse(new URI(linkPretVikingInstall.getText()));
	}

}
