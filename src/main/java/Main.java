import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        gameController controller = new gameController();
        controller.ArduinoController();
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/mainMenu.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}