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

public class BretVikingController {
	@FXML
	private Hyperlink linkBretVikingInstall = null;

	
	@FXML
	public void linkBretVikingInstallClicked(Event e) throws IOException, URISyntaxException {
		WebView b = new WebView();
		WebEngine engine = b.getEngine();
		System.out.println(linkBretVikingInstall.getText());
		engine.load(linkBretVikingInstall.getText());

		Desktop.getDesktop().browse(new URI(linkBretVikingInstall.getText()));
	}

}
