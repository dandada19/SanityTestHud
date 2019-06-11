package dzhu.SanityTestHud;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dzhu.controller.SettingsController;
import dzhu.settings.GlobalSettings;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class MainStage {
	private Stage stage;
	private Stage sideStage;
	private Map<String, Scene> scenePool = new HashMap();
	final int SCENE_POOL_SIZE = 10;
	private static SettingsController settingsController;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void draw() {
		initSideStage();
		
		TreeView tv = new TreeView();
		
		TreeItem<String> treeRoot = new TreeItem<String>("root");
		
		TreeItem<String> settingsMenu = new TreeItem<String>("Settings");
		
		TreeItem int2 = setupInt2();
		TreeItem bret = setupBret();
		
		treeRoot.getChildren().add(settingsMenu);
		treeRoot.getChildren().add(int2);
		treeRoot.getChildren().add(bret);
		
		tv.setRoot(treeRoot);
		tv.setShowRoot(false);
		
		VBox vb = new VBox(tv);
		//VBox.setVgrow(tv, Priority.ALWAYS);
		
		Scene mainScene = new Scene(vb, 230, 600);
		mainScene.getStylesheets().add("application.css");
		mainScene.getStylesheets().add("bootstrap3.css");
		tv.getStyleClass().add("myTree");

		Button btnComposeEmail = new Button("Compose Email");
		btnComposeEmail.getStyleClass().add("primary");
		btnComposeEmail.setMaxWidth(Double.MAX_VALUE);
		
		//not able to login
//		WebDriver driver = new HtmlUnitDriver(false);
//		driver.get(GlobalSettings.DEV_PORTAL_LINK);
//		try {
//			WebElement element = driver.findElement(By.id("stackInfo_int2"));
//			stackVersion = element.getText();
//		}catch(Exception e) {
//			//element not found, ignore this step.
//		}

		btnComposeEmail.setOnAction(e->{
			String [] rets = getTestResult(treeRoot);
			String testedStacks = rets[0];
			if(testedStacks.equals("")) {
				return;
			}
			String body = rets[1];
			String strSubject = testedStacks + " updated";
			String toList = "", ccList="";
			if (testedStacks.toLowerCase().contains("int2")) {
				toList = GlobalSettings.EMAIL_TO_LIST_INT2;
				ccList = GlobalSettings.EMAIL_CC_LIST_INT2;
			}else if (testedStacks.toLowerCase().contains("bret")) {
				toList = GlobalSettings.EMAIL_TO_LIST_BRET;
				ccList = GlobalSettings.EMAIL_CC_LIST_BRET;
			}
			
			try {
		        Runtime.getRuntime().exec(new String[] {
		        		"wscript.exe", "sendEmail.vbs", 
		        		//0-to
		        		toList,
		        		//1-cc
		        		ccList,
		        		//2-subject
		        		strSubject,
		        		//3-body
		        		body
		        });        
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		});
		
		VBox v1 = new VBox(tv);
		VBox.setVgrow(tv, Priority.ALWAYS);
		VBox.setVgrow(v1, Priority.ALWAYS);
		VBox v2 = new VBox(btnComposeEmail);
		v2.getStyleClass().add("mainVBox");
		
		vb.getChildren().addAll(v1, v2);
		
		//vb.setSpacing(5);
		
		//set TreeView selecting actions
		tv.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue)->{
		            TreeItem unSelectedItem = (TreeItem) oldValue;
		            if(unSelectedItem!=null && unSelectedItem.getValue().equals("Settings")) {
		            	//save settings to settings.properties file.
		            	settingsController.savePropertiesToFile();
		            }

		            TreeItem selectedItem = (TreeItem) newValue;
		            if(selectedItem.getParent().equals(treeRoot) && !selectedItem.getValue().equals("Settings")) {
		            	return;
		            }
					String fxmlName = "";
					if(selectedItem.getValue() instanceof CheckBox) {
						CheckBox cb = (CheckBox) selectedItem.getValue();
						TreeItem parentItem = selectedItem.getParent();
						fxmlName = parentItem.getValue() + "_" + cb.getText() + ".fxml";
						sideStage.setTitle(parentItem.getValue() + "-" + cb.getText());
					}else {
						fxmlName = selectedItem.getValue() + ".fxml";
						sideStage.setTitle(selectedItem.getValue().toString());
					}
					sideStage.setScene(getScene(fxmlName));
					
					if(sideStage.isShowing()) {
						if(sideStage.isIconified()){
							sideStage.setIconified(false);
						}
					}else {
						sideStage.setX(stage.getX()+stage.getWidth()-3);
						sideStage.setY(stage.getY());
						sideStage.setHeight(650);
						sideStage.show();
					}
				});
		

		stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("check32.png")));
		stage.resizableProperty().set(false);
		stage.setScene(mainScene);
		stage.resizableProperty().set(false);
		stage.setX(1200);
		stage.setY(100);
		
		stage.setOnCloseRequest(value->{
			if(settingsController!=null) {
				settingsController.savePropertiesToFile();
			}
			sideStage.close();
		});
		
		stage.iconifiedProperty().addListener(
				(ov, oldValue, newValue)->{
					if(sideStage.isShowing()) {
						sideStage.toFront();
					}
				});
		
		stage.show();
	}
	
	public void initSideStage() {
		if(sideStage == null) {
			sideStage = new Stage();
		}
		sideStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("idea.png")));
		sideStage.setAlwaysOnTop(true);
		sideStage.initStyle(StageStyle.DECORATED);
		sideStage.setWidth(400);
	}
	
	private TreeItem setupInt2() {
		TreeItem int2 = new TreeItem(new String("INT2"));
		TreeItem<CheckBox> int2FIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> int2Enroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> int2Classic = new TreeItem<CheckBox>(new CheckBox("Classic"));
		TreeItem<CheckBox> int2Mdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> int2Devmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> int2X2Login = new TreeItem<CheckBox>(new CheckBox("X2Login"));
		TreeItem<CheckBox> int2WALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> int2Jasper = new TreeItem<CheckBox>(new CheckBox("Jasper"));
		int2.getChildren().addAll(int2FIX, int2Enroll, int2Classic, int2Mdf, int2Devmon, int2X2Login, int2WALogin, int2Jasper);
		int2.setExpanded(false);
		return int2;
	}
	
	private TreeItem setupBret() {
		TreeItem bret = new TreeItem(new String("BRET"));

		TreeItem<CheckBox> bretDevmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> bretWALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> bretEnroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> bretQtp = new TreeItem<CheckBox>(new CheckBox("QTP"));
		
		TreeItem<CheckBox> bretFIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> bretMdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> bretX2 = new TreeItem<CheckBox>(new CheckBox("X2"));
		TreeItem<CheckBox> bretMobile = new TreeItem<CheckBox>(new CheckBox("Mobile"));
		TreeItem<CheckBox> bretViking = new TreeItem<CheckBox>(new CheckBox("Viking"));
		bret.getChildren().addAll(bretDevmon, bretWALogin, bretEnroll, bretQtp, bretFIX, bretMdf, bretX2, bretMobile, bretViking);
		bret.setExpanded(false);
		return bret;
	}
	
	private Scene getScene(String name) {
		name = name.toLowerCase();
		if(scenePool.containsKey(name)) {
			return scenePool.get(name);
		}else {
			FXMLLoader loader = new FXMLLoader();
			Parent root = new VBox();
			try {
				InputStream is = MainStage.class.getClassLoader().getResourceAsStream(name);
				root = (Parent) loader.load(is);
				if(name.contains("settings.fxml")) {
					settingsController = loader.getController();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(name+" not found or loading error...");
			}					
			Scene scene = new Scene(root);
			if(scenePool.size()==SCENE_POOL_SIZE) {
				int rand = new Random().nextInt(SCENE_POOL_SIZE);
				int i=0;
				for(Map.Entry<String, Scene> e : scenePool.entrySet()) {
					if(i==rand) {
						scenePool.remove(e.getKey());
					}
					i++;					
				}
			}
			scenePool.put(name, scene);
			return scene;
		}
	}
	
	private String[] getTestResult(TreeItem root) {
		String lineBreak = "<br>    &nbsp&nbsp&nbsp&nbsp&nbsp-";
		String testedStacks = "";
		String result = "";
		String stackResult = "";
		for(TreeItem stack : (ObservableList<TreeItem>)root.getChildren()) {
			if("Settings".equals(stack.getValue())) {
				continue;
			}
			boolean isTested  = false;
			for(TreeItem<CheckBox>test : (ObservableList<TreeItem>)stack.getChildren()) {
				boolean isPassed = test.getValue().isSelected();
				if(isPassed) {
					isTested = true;
					stackResult += lineBreak + test.getValue().getText() + ":    <font color=\"green\">Passed</font>" ;
				}else {
					stackResult += lineBreak + test.getValue().getText() + ":    <font color=\"red\">Failed</font>";
				}
			}
			if(isTested) {
				stackResult = "<strong>"+stack.getValue() + " is updated to <Check Version>"+"</strong><br>" + stackResult;
				result += stackResult + "<br><br><br>";
				testedStacks += stack.getValue() + "/";
			}
			
			stackResult = "";
		}
		if(!testedStacks.equals("")) {
			testedStacks = testedStacks.substring(0,testedStacks.length()-1);
		}
		return new String [] {testedStacks, result};
	}
}
