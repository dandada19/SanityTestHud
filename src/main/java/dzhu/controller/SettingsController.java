package dzhu.controller;

import java.util.HashMap;
import java.util.Map;

import dzhu.settings.GlobalSettings;
import dzhu.settings.Int2Settings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class SettingsController {
	private static Map<String,String> mapControl2Property = null;

	@FXML
	private TabPane rootTabPane = null;
	@FXML
	private TextField tfGlobalFixPortalUsername = null;
	@FXML
	private TextField tfGlobalFixPortalPassword = null;
	@FXML
	private TextField tfGlobalDevPortalUsername = null;
	@FXML
	private TextField tfGlobalDevPortalPassword = null;	

	@FXML
	private TextField tfInt2EnrollmentUsername = null;
	@FXML
	private TextField tfInt2EnrollmentPassword = null;
	@FXML
	private TextField tfInt2ClassicTakerUsername = null;
	@FXML
	private TextField tfInt2ClassicTakerPassword = null;
	@FXML
	private TextField tfInt2ClassicMakerUsername = null;
	@FXML
	private TextField tfInt2ClassicMakerPassword = null;
	@FXML
	private TextField tfInt2X2TakerUsername = null;
	@FXML
	private TextField tfInt2X2TakerPassword = null;
	@FXML
	private TextField tfInt2WebAdminUsername = null;
	@FXML
	private TextField tfInt2WebAdminPassword = null;
	@FXML
	private TextField tfInt2DevmonAdminUsername = null;
	@FXML
	private TextField tfInt2DevmonAdminPassword = null;
	@FXML
	private TextField tfInt2MdfAdminUsername = null;
	@FXML
	private TextField tfInt2MdfAdminPassword = null;
	
	public static void initMapControl2Property() {
	}
	
	@FXML
	public void toggleButtonClick(Event e) {
		ToggleButton tb = (ToggleButton)e.getSource();
		if(tb.isSelected()) {
			System.out.println("tb clicked");
			System.out.println("tb.isSelected()="+tb.isSelected());
			setSiblingEditable(tb, true);
		}else {
			setSiblingEditable(tb, false);
		}
	}
	
	private void setSiblingEditable(Node source, boolean editable) {
		HBox parent = (HBox)source.getParent();
		for(Node n : parent.getChildren()) {
			if (n instanceof TextField) {
				TextField t = (TextField)n;
				t.setEditable(editable);
			}else if(n instanceof PasswordField) {
				PasswordField p = (PasswordField)n;
				p.setEditable(editable);
			}
		}
	}
	
	@FXML
	public void initialize() {
		System.out.println("this is Settings Controller init");
		tfGlobalFixPortalUsername.setText(GlobalSettings.FIXPORTAL_USERNAME);
		tfGlobalFixPortalPassword.setText(GlobalSettings.FIXPORTAL_PASSWORD);
		tfGlobalDevPortalUsername.setText(GlobalSettings.DEVPORTAL_USERNAME);
		tfGlobalDevPortalPassword.setText(GlobalSettings.DEVPORTAL_PASSWORD);		
		
		tfInt2EnrollmentUsername.setText(Int2Settings.ENROLLMENT_USERNAME);		
		tfInt2EnrollmentPassword.setText(Int2Settings.ENROLLMENT_PASSWORD);		
		tfInt2ClassicTakerUsername.setText(Int2Settings.CLASSICTAKER_USERNAME);		
		tfInt2ClassicTakerPassword.setText(Int2Settings.CLASSICTAKER_PASSWORD);		
		tfInt2ClassicMakerUsername.setText(Int2Settings.CLASSICMAKER_USERNAME);		
		tfInt2ClassicMakerPassword.setText(Int2Settings.CLASSICMAKER_PASSWORD);		
		tfInt2X2TakerUsername.setText(Int2Settings.X2TAKER_USERNAME);		
		tfInt2X2TakerPassword.setText(Int2Settings.X2TAKER_PASSWORD);		
		tfInt2WebAdminUsername.setText(Int2Settings.WEBADMIN_USERNAME);		
		tfInt2WebAdminPassword.setText(Int2Settings.WEBADMIN_PASSWORD);		
		tfInt2DevmonAdminUsername.setText(Int2Settings.DEVMONADMIN_USERNAME);		
		tfInt2DevmonAdminPassword.setText(Int2Settings.DEVMONADMIN_PASSWORD);		
		tfInt2MdfAdminUsername.setText(Int2Settings.MDFADMIN_USERNAME);		
		tfInt2MdfAdminPassword.setText(Int2Settings.MDFADMIN_PASSWORD);
		
		//add listener to Textfield and PasswordField
		//i don't know how to lookup by Type selector.
		for(Node n : rootTabPane.lookupAll(".middle")) {
			addFocusOffListener(n);
		}
		for(Node n : rootTabPane.lookupAll(".last")) {
			addFocusOffListener(n);
		}
	}
	
	private void addFocusOffListener(Node n) {
		n.focusedProperty().addListener(
				(obs, oldVal, newVal)->{
					if(newVal==false) {
			            System.out.println("Textfield out focus");
			            System.out.println(n.getId());
					}
				}
			);
	}
}
