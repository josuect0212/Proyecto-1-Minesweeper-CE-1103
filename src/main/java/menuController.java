import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class controls the gui of the menu and sets some of the values needed before the game starts
 */
public class menuController {
    @FXML
    private Button easyBtn;
    @FXML
    private Button hardBtn;

    /**
     * This method starts the game with the easy AI, it loads the board, sets the mines, starts the timer, and initializes the arduino
     * @param actionEvent event of pressing the easy button
     * @throws IOException if the file cannot be loaded
     */
    public void easyStart(javafx.event.ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
        Parent root = loader.load();

        gameController gameController = loader.getController();
        gameController.startTime();
        mainGame.setBoard();
        mainGame.setMines();

        Scene scene = new Scene(root);
        Stage stage = (Stage) easyBtn.getScene().getWindow();
        mainGame.difficulty = false;
        stage.setScene(scene);
        gameController.ArduinoController();
        stage.show();
    }

    /**
     * This method starts the game with the hard AI, it loads the board, sets the mines, starts the timer, and initializes the arduino
     * @param actionEvent Hard difficulty button pressed
     * @throws IOException if the file cannot be loaded
     */
    public void hardStart(javafx.event.ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
        Parent root = loader.load();

        gameController gameController = loader.getController();
        gameController.startTime();
        mainGame.setBoard();
        mainGame.setMines();

        Scene scene = new Scene(root);
        Stage stage = (Stage) hardBtn.getScene().getWindow();
        mainGame.difficulty = true;
        stage.setScene(scene);
        gameController.ArduinoController();
        stage.show();
    }
}
