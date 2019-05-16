package dzhu.SanityTestHud;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
		
		treeRoot.getChildren().add(settingsMenu);
		treeRoot.getChildren().add(int2);
		
		tv.setRoot(treeRoot);
		tv.setShowRoot(false);
		
		VBox vb = new VBox(tv);
		VBox.setVgrow(tv, Priority.ALWAYS);
		
		Scene mainScene = new Scene(vb, 230, 600);
		mainScene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
		tv.getStyleClass().add("myTree");
		
		//set TreeView selecting actions
		tv.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue)->{
		            TreeItem selectedItem = (TreeItem) newValue;
					CheckBox cb = (CheckBox) selectedItem.getValue();
					System.out.println("selected item is: " + cb.getText());
					TreeItem parentItem = selectedItem.getParent();
					System.out.println("selected item's father is: " + parentItem.getValue());
					String fxmlName = parentItem.getValue() + "_" + cb.getText() + ".fxml";
					System.out.println("fxmlName is: " + fxmlName);
					sideStage.setTitle(parentItem.getValue() + "-" + cb.getText());
					
				
					sideStage.setScene(getScene(fxmlName));
					
					if(sideStage.isShowing()) {
						sideStage.show();
					}else {
						sideStage.setX(stage.getX()+stage.getWidth()-3);
						sideStage.setY(stage.getY());
						sideStage.setHeight(stage.getHeight());
						sideStage.show();
					}
				});
		

		stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("check32.png")));
		stage.resizableProperty().set(false);
		stage.setScene(mainScene);
		stage.resizableProperty().set(false);
		stage.xProperty().addListener((obs, oldVal, newVal) -> {
			sideStage.setX((double)newVal + stage.getWidth()-3);
		});
		stage.yProperty().addListener((obs, oldVal, newVal) -> {
			sideStage.setY((double)newVal);
		});
		
		stage.setOnCloseRequest(value->{
			sideStage.close();
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
		sideStage.setWidth(360);
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
		int2.setExpanded(true);
		return int2;
	}
	
	private Scene getScene(String name) {
		if(scenePool.containsKey(name)) {
			return scenePool.get(name);
		}else {
			FXMLLoader loader = new FXMLLoader();
			Parent root = new VBox();
			try {
				loader.setLocation(new File("src/main/resources/" + name).toURI().toURL());
				root = (Parent) loader.load();
			} catch (IOException e) {
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
}
