import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameMain extends Application
{

	public void start(Stage primaryStage) throws Exception 
	{
		//this loads the content
		Parent par = FXMLLoader.load(getClass().getResource("Welcome.fxml"));		
		Scene scn = new Scene(par,1100,800);
		primaryStage.setTitle("Welcome");
		primaryStage.setScene(scn);
		primaryStage.show();
	
	}
	
	public static void main(String[] args) 
	{
		
		launch (args);
	}
}
	
