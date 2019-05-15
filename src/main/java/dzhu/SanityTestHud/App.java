package dzhu.SanityTestHud;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application
{
    public static void main( String[] args )
    {
		launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
//		//Label label = new Label("hello world");
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(new File("src/main/resources/main.fxml").toURI().toURL());
//		final Parent root = (Parent) loader.load();
//		
//		Scene scene = new Scene(root, 300, 300);
//		primaryStage.setScene(scene);
//		primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("idea.png")));
//		primaryStage.show();
		MainStage mainStage = new MainStage();
		mainStage.setStage(primaryStage);
		mainStage.draw();
	}
}
