package dzhu.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dzhu.settings.BretSettings;
import dzhu.settings.DretSettings;
import dzhu.settings.GlobalSettings;
import dzhu.settings.Int2Settings;
import dzhu.settings.SettingsUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class SettingsController {
	private static final Map<String, String> mapControl2Property;
    static {
        Map<String, String> map = new HashMap<>();
        map.put("tfGlobalFixPortalUsername", "GlobalSettings.FIXPORTAL_USERNAME");
		map.put("tfGlobalFixPortalPassword", "GlobalSettings.FIXPORTAL_PASSWORD");
		map.put("tfGlobalDevPortalUsername", "GlobalSettings.DEVPORTAL_USERNAME");
		map.put("tfGlobalDevPortalPassword", "GlobalSettings.DEVPORTAL_PASSWORD");
		
		//INT2
		map.put("tfInt2EnrollmentUsername", "Int2Settings.ENROLLMENT_USERNAME");
		map.put("tfInt2EnrollmentPassword", "Int2Settings.ENROLLMENT_PASSWORD");
		map.put("tfInt2ClassicTakerUsername", "Int2Settings.CLASSICTAKER_USERNAME");
		map.put("tfInt2ClassicTakerPassword", "Int2Settings.CLASSICTAKER_PASSWORD");
		map.put("tfInt2ClassicMakerUsername", "Int2Settings.CLASSICMAKER_USERNAME");
		map.put("tfInt2ClassicMakerPassword", "Int2Settings.CLASSICMAKER_PASSWORD");
		map.put("tfInt2X2TakerUsername", "Int2Settings.X2TAKER_USERNAME");
		map.put("tfInt2X2TakerPassword", "Int2Settings.X2TAKER_PASSWORD");
		map.put("tfInt2WebAdminUsername", "Int2Settings.WEBADMIN_USERNAME");
		map.put("tfInt2WebAdminPassword", "Int2Settings.WEBADMIN_PASSWORD");
		map.put("tfInt2DevmonAdminUsername", "Int2Settings.DEVMONADMIN_USERNAME");
		map.put("tfInt2DevmonAdminPassword", "Int2Settings.DEVMONADMIN_PASSWORD");
		map.put("tfInt2MdfAdminUsername", "Int2Settings.MDFADMIN_USERNAME");
		map.put("tfInt2MdfAdminPassword", "Int2Settings.MDFADMIN_PASSWORD");
		
		//BRET
		map.put("tfBretEnrollmentUsername", "BretSettings.ENROLLMENT_USERNAME");
		map.put("tfBretEnrollmentPassword", "BretSettings.ENROLLMENT_PASSWORD");
		map.put("tfBretVikingUsername", "BretSettings.VIKING_USERNAME");
		map.put("tfBretVikingPassword", "BretSettings.VIKING_PASSWORD");
		map.put("tfBretMobileUsername", "BretSettings.MOBILE_USERNAME");
		map.put("tfBretMobilePassword", "BretSettings.MOBILE_PASSWORD");
		map.put("tfBretX2TakerUsername", "BretSettings.X2TAKER_USERNAME");
		map.put("tfBretX2TakerPassword", "BretSettings.X2TAKER_PASSWORD");
		map.put("tfBretWebAdminUsername", "BretSettings.WEBADMIN_USERNAME");
		map.put("tfBretWebAdminPassword", "BretSettings.WEBADMIN_PASSWORD");
		map.put("tfBretDevmonAdminUsername", "BretSettings.DEVMONADMIN_USERNAME");
		map.put("tfBretDevmonAdminPassword", "BretSettings.DEVMONADMIN_PASSWORD");
		map.put("tfBretMdfAdminUsername", "BretSettings.MDFADMIN_USERNAME");
		map.put("tfBretMdfAdminPassword", "BretSettings.MDFADMIN_PASSWORD");
		
		//DRET
		map.put("tfDretEnrollmentUsername", "DretSettings.ENROLLMENT_USERNAME");
		map.put("tfDretEnrollmentPassword", "DretSettings.ENROLLMENT_PASSWORD");
		map.put("tfDretVikingUsername", "DretSettings.VIKING_USERNAME");
		map.put("tfDretVikingPassword", "DretSettings.VIKING_PASSWORD");
		map.put("tfDretMobileUsername", "DretSettings.MOBILE_USERNAME");
		map.put("tfDretMobilePassword", "DretSettings.MOBILE_PASSWORD");
		map.put("tfDretX2TakerUsername", "DretSettings.X2TAKER_USERNAME");
		map.put("tfDretX2TakerPassword", "DretSettings.X2TAKER_PASSWORD");
		map.put("tfDretWebAdminUsername", "DretSettings.WEBADMIN_USERNAME");
		map.put("tfDretWebAdminPassword", "DretSettings.WEBADMIN_PASSWORD");
		map.put("tfDretDevmonAdminUsername", "DretSettings.DEVMONADMIN_USERNAME");
		map.put("tfDretDevmonAdminPassword", "DretSettings.DEVMONADMIN_PASSWORD");
		map.put("tfDretMdfAdminUsername", "DretSettings.MDFADMIN_USERNAME");
		map.put("tfDretMdfAdminPassword", "DretSettings.MDFADMIN_PASSWORD");
		
        mapControl2Property = Collections.unmodifiableMap(map);
    }
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

	
	//INT2
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
	
	//BRET
	@FXML
	private TextField tfBretEnrollmentUsername = null;
	@FXML
	private TextField tfBretEnrollmentPassword = null;
	@FXML
	private TextField tfBretVikingUsername = null;
	@FXML
	private TextField tfBretVikingPassword = null;
	@FXML
	private TextField tfBretMobileUsername = null;
	@FXML
	private TextField tfBretMobilePassword = null;
	@FXML
	private TextField tfBretX2TakerUsername = null;
	@FXML
	private TextField tfBretX2TakerPassword = null;
	@FXML
	private TextField tfBretWebAdminUsername = null;
	@FXML
	private TextField tfBretWebAdminPassword = null;
	@FXML
	private TextField tfBretDevmonAdminUsername = null;
	@FXML
	private TextField tfBretDevmonAdminPassword = null;
	@FXML
	private TextField tfBretMdfAdminUsername = null;
	@FXML
	private TextField tfBretMdfAdminPassword = null;

	//DRET
	@FXML
	private TextField tfDretEnrollmentUsername = null;
	@FXML
	private TextField tfDretEnrollmentPassword = null;
	@FXML
	private TextField tfDretVikingUsername = null;
	@FXML
	private TextField tfDretVikingPassword = null;
	@FXML
	private TextField tfDretMobileUsername = null;
	@FXML
	private TextField tfDretMobilePassword = null;
	@FXML
	private TextField tfDretX2TakerUsername = null;
	@FXML
	private TextField tfDretX2TakerPassword = null;
	@FXML
	private TextField tfDretWebAdminUsername = null;
	@FXML
	private TextField tfDretWebAdminPassword = null;
	@FXML
	private TextField tfDretDevmonAdminUsername = null;
	@FXML
	private TextField tfDretDevmonAdminPassword = null;
	@FXML
	private TextField tfDretMdfAdminUsername = null;
	@FXML
	private TextField tfDretMdfAdminPassword = null;
	
	public static void initMapControl2Property() {
	}
	
	@FXML
	public void toggleButtonClick(Event e) {
		ToggleButton tb = (ToggleButton)e.getSource();
		if(tb.isSelected()) {
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
		tfGlobalFixPortalUsername.setText(GlobalSettings.FIXPORTAL_USERNAME);
		tfGlobalFixPortalPassword.setText(GlobalSettings.FIXPORTAL_PASSWORD);
		tfGlobalDevPortalUsername.setText(GlobalSettings.DEVPORTAL_USERNAME);
		tfGlobalDevPortalPassword.setText(GlobalSettings.DEVPORTAL_PASSWORD);		
		
		//INT2
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
		
		//BRET
		tfBretEnrollmentUsername.setText(BretSettings.ENROLLMENT_USERNAME);		
		tfBretEnrollmentPassword.setText(BretSettings.ENROLLMENT_PASSWORD);		
		tfBretVikingUsername.setText(BretSettings.VIKING_USERNAME);		
		tfBretVikingPassword.setText(BretSettings.VIKING_PASSWORD);		
		tfBretMobileUsername.setText(BretSettings.MOBILE_USERNAME);		
		tfBretMobilePassword.setText(BretSettings.MOBILE_PASSWORD);		
		tfBretX2TakerUsername.setText(BretSettings.X2TAKER_USERNAME);		
		tfBretX2TakerPassword.setText(BretSettings.X2TAKER_PASSWORD);		
		tfBretWebAdminUsername.setText(BretSettings.WEBADMIN_USERNAME);		
		tfBretWebAdminPassword.setText(BretSettings.WEBADMIN_PASSWORD);		
		tfBretDevmonAdminUsername.setText(BretSettings.DEVMONADMIN_USERNAME);		
		tfBretDevmonAdminPassword.setText(BretSettings.DEVMONADMIN_PASSWORD);		
		tfBretMdfAdminUsername.setText(BretSettings.MDFADMIN_USERNAME);		
		tfBretMdfAdminPassword.setText(BretSettings.MDFADMIN_PASSWORD);
		
		//DRET
		tfDretEnrollmentUsername.setText(DretSettings.ENROLLMENT_USERNAME);		
		tfDretEnrollmentPassword.setText(DretSettings.ENROLLMENT_PASSWORD);		
		tfDretVikingUsername.setText(DretSettings.VIKING_USERNAME);		
		tfDretVikingPassword.setText(DretSettings.VIKING_PASSWORD);		
		tfDretMobileUsername.setText(DretSettings.MOBILE_USERNAME);		
		tfDretMobilePassword.setText(DretSettings.MOBILE_PASSWORD);		
		tfDretX2TakerUsername.setText(DretSettings.X2TAKER_USERNAME);		
		tfDretX2TakerPassword.setText(DretSettings.X2TAKER_PASSWORD);		
		tfDretWebAdminUsername.setText(DretSettings.WEBADMIN_USERNAME);		
		tfDretWebAdminPassword.setText(DretSettings.WEBADMIN_PASSWORD);		
		tfDretDevmonAdminUsername.setText(DretSettings.DEVMONADMIN_USERNAME);		
		tfDretDevmonAdminPassword.setText(DretSettings.DEVMONADMIN_PASSWORD);		
		tfDretMdfAdminUsername.setText(DretSettings.MDFADMIN_USERNAME);		
		tfDretMdfAdminPassword.setText(DretSettings.MDFADMIN_PASSWORD);
		
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
			            System.out.println(n.getId());
			            String property = mapControl2Property.get(n.getId());
			            System.out.println(property);
			            if (property==null) {
			            	return;
			            }
			            try {
				            String cls = property.split("\\.")[0];
				            String field = property.split("\\.")[1];
				            Class<?> c = Class.forName("dzhu.settings."+cls);
				            Field f = c.getDeclaredField(field);
				            f.setAccessible(true);
				            TextInputControl input = (TextInputControl)n;
				            f.set(c, input.getText());
			            }catch(Exception e) {
			            	e.printStackTrace();
			            }
			            
					}
				}
			);
	}
	
	public void savePropertiesToFile() {
		List<String> lines = new ArrayList<>();
		Path path = Paths.get("settings.properties");		
		try {
			for(Map.Entry<String, String> entry : mapControl2Property.entrySet()) {
				TextInputControl node = (TextInputControl)rootTabPane.lookup("#"+entry.getKey());
				String value = "";
				if(node.getText()!=null) {
					value = node.getText();
				}
				if (node instanceof PasswordField) {
					value = SettingsUtil.encryptString(value);
				}
				//GlobalSettings.FIXPORTAL_USERNAME to GLOBAL_FIXPORTAL_USERNAME
				String property = entry.getValue().replace("Settings.", "_").toUpperCase();
				lines.add(property+"="+value);
			}
			Collections.sort(lines);
			Files.write(path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
