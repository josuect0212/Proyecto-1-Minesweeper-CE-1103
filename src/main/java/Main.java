import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application{
    private static final int gridSize = 8;
    private static final int numMines= 10;
    private Button[][] grid;
    private int[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int numFlags;
    private Text statusText;

    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Minesweeper");

        StackPane layout = new StackPane();

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}