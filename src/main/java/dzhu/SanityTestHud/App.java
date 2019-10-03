package dzhu.SanityTestHud;

import dzhu.controller.ControllerUtils;
import dzhu.settings.SettingsUtil;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application
{
    public static void main( String[] args )
    {
		launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		SettingsUtil.initSettings();
		ControllerUtils.init(primaryStage);
		
		MainStage mainStage = new MainStage();
		mainStage.setStage(primaryStage);
		mainStage.draw();
		EmailUtils.initializeDriver();
	}
}
