import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application{
    /**
     * Starts the game by loading the main menu FXML file
     * @param primaryStage the primary stage of the game
     * @throws Exception if the FXML cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/mainMenu.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * main entry for the JavaFX game
     * @param args arguments passed to the game
     */
    public static void main(String[] args){
        launch(args);
    }
}