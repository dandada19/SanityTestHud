package dzhu.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Int2FixController {
	@FXML
	private Label labelInt2FixFxStatus = null;
	@FXML
	private Label labelInt2FixTreasStatus = null;
	@FXML
	private Button btnInt2FixFX = null;
	@FXML
	private Button btnInt2FixTreas = null;
	@FXML
	private Button btnInt2LaunchTeamcity = null;
	
	@FXML
	public void btnInt2FixFXClicked(Event e) {
		labelInt2FixFxStatus.setText("INT2 Sanity FX is running...");
	}
	@FXML
	public void btnInt2FixTreasClicked(Event e) {
		labelInt2FixTreasStatus.setText("INT2 Sanity Treasury is running...");
	}
	@FXML
	public void btnInt2LaunchTeamcityClicked(Event e) {
		
	}
}
