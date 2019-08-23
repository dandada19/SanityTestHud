package dzhu.controller;

import javafx.stage.Stage;

public class ControllerUtils {
	private static Stage mainStage;
	ControllerUtils(Stage s){
		mainStage = s;
	}
	public static void init(Stage s) {
		mainStage = s;
	}
	public static void hideStages(Stage... stages) {
		for(Stage s : stages) {
			s.hide();
		}
		mainStage.hide();
	}
	public static void showStages(Stage... stages) {
		for(Stage s : stages) {
			s.setHeight(650);
			s.show();
		}
		mainStage.show();
	}
}
