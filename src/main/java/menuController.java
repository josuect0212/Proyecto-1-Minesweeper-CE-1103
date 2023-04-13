import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class menuController {
    @FXML
    private Button easyBtn;
    @FXML
    private Button hardBtn;
    public void easyStart(javafx.event.ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
        Parent root = loader.load();

        gameController gameController = loader.getController();
        gameController.startTime();
        mainGame.setBoard();
        mainGame.setMines();

        Scene scene = new Scene(root);
        Stage stage = (Stage) easyBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
