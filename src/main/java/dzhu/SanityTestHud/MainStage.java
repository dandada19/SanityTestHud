package dzhu.SanityTestHud;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dzhu.controller.SettingsController;
import dzhu.settings.GlobalSettings;
import dzhu.settings.TodayTestSettings;
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
		TreeItem prod = setupProd();
		TreeItem lofx = setupLofx();
		TreeItem bret = setupBret();
		TreeItem dret = setupDret();
		TreeItem pret = setupPret();
		TreeItem tkfx = setupTkfx();
		
		treeRoot.getChildren().add(settingsMenu);
		treeRoot.getChildren().add(int2);
		treeRoot.getChildren().add(prod);
		treeRoot.getChildren().add(lofx);
		treeRoot.getChildren().add(bret);
		treeRoot.getChildren().add(dret);
		treeRoot.getChildren().add(pret);
		treeRoot.getChildren().add(tkfx);
		
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
			String strSubject = null;
			String changeTicketNumber = TodayTestSettings.CHANGE_TICKET_NUMBER;
			if(changeTicketNumber == null || changeTicketNumber.length()==0) {
				changeTicketNumber = "CHG-xxxxx";
			}
			String stackVersion = TodayTestSettings.STACK_VERSION_NUMBER;
			if(stackVersion == null || stackVersion.length()==0) {
				stackVersion = "[Version]";
			}
			
			if(TodayTestSettings.IS_STACK_REBOOT) {
				strSubject = "Currenex " + stackVersion + " QA Status | " + testedStacks;
			}else if(TodayTestSettings.IS_MAJOR_RELEASE) {
				strSubject = changeTicketNumber + " | Currenex " + stackVersion + " Upgrade QA Status | " + testedStacks;
			}else if(TodayTestSettings.IS_MINOR_RELEASE) {
				strSubject = changeTicketNumber + " | Currenex " + stackVersion + " Upgrade QA Status | " + testedStacks;
			}else {//patch
				strSubject = changeTicketNumber + " | Currenex Patch QA Status | " + testedStacks;
			}
			String toList = "", ccList="";
			if (testedStacks.toLowerCase().contains("int2")) {
				toList = GlobalSettings.EMAIL_TO_LIST_INT2;
				ccList = GlobalSettings.EMAIL_CC_LIST_INT2;
			}else if (testedStacks.toLowerCase().contains("bret") ||
					testedStacks.toLowerCase().contains("dret") ||
					testedStacks.toLowerCase().contains("tkfx") ||
					testedStacks.toLowerCase().contains("pret") ) {
				toList = GlobalSettings.EMAIL_TO_LIST_RET;
				ccList = GlobalSettings.EMAIL_CC_LIST_RET;
			}else if (testedStacks.toLowerCase().contains("prod") ||
					testedStacks.toLowerCase().contains("lofx") ) {
				toList = GlobalSettings.EMAIL_TO_LIST_PROD;
				ccList = GlobalSettings.EMAIL_CC_LIST_PROD;
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
						//seems it never goes in here.
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
		stage.setAlwaysOnTop(false);
		stage.show();
	}
	
	public void initSideStage() {
		if(sideStage == null) {
			sideStage = new Stage();
		}
		sideStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("idea.png")));
		sideStage.setAlwaysOnTop(false);
		sideStage.initStyle(StageStyle.DECORATED);
		sideStage.setWidth(400);
	}
	
	private TreeItem setupInt2() {
		TreeItem int2 = new TreeItem(new String("INT2"));
		TreeItem<CheckBox> int2FIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> int2Devmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> int2Enroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> int2Classic = new TreeItem<CheckBox>(new CheckBox("Classic"));
		TreeItem<CheckBox> int2Mdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> int2X2Login = new TreeItem<CheckBox>(new CheckBox("X2Login"));
		TreeItem<CheckBox> int2WALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> int2Jasper = new TreeItem<CheckBox>(new CheckBox("Jasper"));
		int2.getChildren().addAll(int2Devmon, int2FIX, int2Enroll, int2Classic, int2Mdf, int2X2Login, int2WALogin, int2Jasper);
		int2.setExpanded(false);
		return int2;
	}
	
	private TreeItem setupProd() {
		TreeItem prod = new TreeItem(new String("PROD"));
		TreeItem<CheckBox> prodDevmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> prodFIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> prodWALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> prodEnroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> prodClassic = new TreeItem<CheckBox>(new CheckBox("Classic"));
		TreeItem<CheckBox> prodMdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> prodX2 = new TreeItem<CheckBox>(new CheckBox("X2"));
		TreeItem<CheckBox> prodJasper = new TreeItem<CheckBox>(new CheckBox("Jasper"));
		TreeItem<CheckBox> prodMobile = new TreeItem<CheckBox>(new CheckBox("Mobile"));
		TreeItem<CheckBox> prodGlobalCredit = new TreeItem<CheckBox>(new CheckBox("GlobalCredit"));
		TreeItem<CheckBox> prodWarmUpOrders = new TreeItem<CheckBox>(new CheckBox("WarmUpOrders"));
		prod.getChildren().addAll(prodDevmon, prodFIX, prodWALogin, prodEnroll, prodClassic, prodMdf, prodX2, 
				prodJasper, prodMobile, prodGlobalCredit, prodWarmUpOrders);
		prod.setExpanded(false);
		return prod;
	}
	
	private TreeItem setupLofx() {
		TreeItem lofx = new TreeItem(new String("LOFX"));
		TreeItem<CheckBox> lofxDevmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> lofxFIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> lofxWALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> lofxEnroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> lofxClassic = new TreeItem<CheckBox>(new CheckBox("Classic"));
		TreeItem<CheckBox> lofxMdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> lofxJasper = new TreeItem<CheckBox>(new CheckBox("Jasper"));
		lofx.getChildren().addAll(lofxDevmon, lofxFIX, lofxWALogin, lofxEnroll, lofxClassic, lofxMdf, lofxJasper);
		lofx.setExpanded(false);
		return lofx;
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
	
	private TreeItem setupDret() {
		TreeItem dret = new TreeItem(new String("DRET"));

		TreeItem<CheckBox> dretDevmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> dretWALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> dretEnroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> dretQtp = new TreeItem<CheckBox>(new CheckBox("QTP"));
		
		TreeItem<CheckBox> dretFIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> dretMdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> dretX2 = new TreeItem<CheckBox>(new CheckBox("X2"));
		TreeItem<CheckBox> dretMobile = new TreeItem<CheckBox>(new CheckBox("Mobile"));
		TreeItem<CheckBox> dretViking = new TreeItem<CheckBox>(new CheckBox("Viking"));
		dret.getChildren().addAll(dretDevmon, dretWALogin, dretEnroll, dretQtp, dretFIX, dretMdf, dretX2, dretMobile, dretViking);
		dret.setExpanded(false);
		return dret;
	}
	
	private TreeItem setupPret() {
		TreeItem pret = new TreeItem(new String("PRET"));

		TreeItem<CheckBox> pretDevmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> pretWALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> pretEnroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> pretQtp = new TreeItem<CheckBox>(new CheckBox("QTP"));
		
		TreeItem<CheckBox> pretFIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> pretMdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		TreeItem<CheckBox> pretMobile = new TreeItem<CheckBox>(new CheckBox("Mobile"));
		TreeItem<CheckBox> pretViking = new TreeItem<CheckBox>(new CheckBox("Viking"));
		pret.getChildren().addAll(pretDevmon, pretWALogin, pretEnroll, pretQtp, pretFIX, pretMdf, pretMobile, pretViking);
		pret.setExpanded(false);
		return pret;
	}
	
	private TreeItem setupTkfx() {
		TreeItem tkfx = new TreeItem(new String("TKFX"));

		TreeItem<CheckBox> tkfxDevmon = new TreeItem<CheckBox>(new CheckBox("DEVMON"));
		TreeItem<CheckBox> tkfxWALogin = new TreeItem<CheckBox>(new CheckBox("WebAdmin"));
		TreeItem<CheckBox> tkfxEnroll = new TreeItem<CheckBox>(new CheckBox("Enroll"));
		TreeItem<CheckBox> tkfxQtp = new TreeItem<CheckBox>(new CheckBox("QTP"));
		
		TreeItem<CheckBox> tkfxFIX = new TreeItem<CheckBox>(new CheckBox("FIX"));
		TreeItem<CheckBox> tkfxMdf = new TreeItem<CheckBox>(new CheckBox("MarketDF"));
		tkfx.getChildren().addAll(tkfxDevmon, tkfxWALogin, tkfxEnroll, tkfxQtp, tkfxFIX, tkfxMdf);
		tkfx.setExpanded(false);
		return tkfx;
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
						break;
					}
					i++;					
				}
			}
			scenePool.put(name, scene);
			return scene;
		}
	}
	
	private String[] getTestResult(TreeItem root) {
		String lineBreak = "<br>&nbsp&nbsp&nbsp&nbsp&nbsp-";
		String lineBreak2 = "<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
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

				if("DRET".equals(stack.getValue()) ) {
					if(test.getValue().getText().contains("QTP")) {
						stackResult = stackResult 
												+ lineBreak2 + "<font color=\"gray\">" + "Retail IC" + "</font>"
								 				+ lineBreak2 + "<font color=\"gray\">" + "LondonAsiaRegion IC Login" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "Retail Margin" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "Retail WS" + "</font>"
												;
					}
				}else if("BRET".equals(stack.getValue())
						|| "PRET".equals(stack.getValue()) ) {
					if(test.getValue().getText().contains("QTP")) {
						stackResult = stackResult 
												+ lineBreak2 + "<font color=\"gray\">" + "Retail IC" + "</font>"
								 				+ lineBreak2 + "<font color=\"gray\">" + "LondonAsiaRegion IC Login" + "</font>"
										 		+ lineBreak2 + "<font color=\"gray\">" + "E2EE Login" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "Retail Margin" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "Retail WS" + "</font>"
												;
					}
				}else if("TKFX".equals(stack.getValue()) ) {
					if(test.getValue().getText().contains("QTP")) {
						stackResult = stackResult 
												+ lineBreak2 + "<font color=\"gray\">" + "Retail IC" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "Retail Margin" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "Retail WS" + "</font>"
												;
					}
				}else if("INT2".equals(stack.getValue()) 
						|| "PROD".equals(stack.getValue()) ) {
					if(test.getValue().getText().contains("FIX")) {
						stackResult = stackResult 
												+ lineBreak2 + "<font color=\"gray\">" + "FIX FX Sanity" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "FIX Treasury Sanity" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "FIX Crypto Sanity" + "</font>"
												+ lineBreak2 + "<font color=\"gray\">" + "FIX MTF Sanity" + "</font>"
												;
					}
				}else {}
			}
			
			if(isTested) {
				stackResult = "<strong>"+stack.getValue() + " is updated to " +
						(TodayTestSettings.STACK_VERSION_NUMBER==null ? "" : TodayTestSettings.STACK_VERSION_NUMBER) +
						//EmailUtils.getStackVersion(stack.getValue().toString()) + 
						"</strong><br>" + stackResult;
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
