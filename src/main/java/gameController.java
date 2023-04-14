import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class gameController {
    @FXML
    private Button btn00;
    @FXML
    private Button btn01;
    @FXML
    private Button btn02;
    @FXML
    private Button btn03;
    @FXML
    private Button btn04;
    @FXML
    private Button btn05;
    @FXML
    private Button btn06;
    @FXML
    private Button btn07;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn13;
    @FXML
    private Button btn14;
    @FXML
    private Button btn15;
    @FXML
    private Button btn16;
    @FXML
    private Button btn17;
    @FXML
    private Button btn20;
    @FXML
    private Button btn21;
    @FXML
    private Button btn22;
    @FXML
    private Button btn23;
    @FXML
    private Button btn24;
    @FXML
    private Button btn25;
    @FXML
    private Button btn26;
    @FXML
    private Button btn27;
    @FXML
    private Button btn30;
    @FXML
    private Button btn31;
    @FXML
    private Button btn32;
    @FXML
    private Button btn33;
    @FXML
    private Button btn34;
    @FXML
    private Button btn35;
    @FXML
    private Button btn36;
    @FXML
    private Button btn37;
    @FXML
    private Button btn40;
    @FXML
    private Button btn41;
    @FXML
    private Button btn42;
    @FXML
    private Button btn43;
    @FXML
    private Button btn44;
    @FXML
    private Button btn45;
    @FXML
    private Button btn46;
    @FXML
    private Button btn47;
    @FXML
    private Button btn50;
    @FXML
    private Button btn51;
    @FXML
    private Button btn52;
    @FXML
    private Button btn53;
    @FXML
    private Button btn54;
    @FXML
    private Button btn55;
    @FXML
    private Button btn56;
    @FXML
    private Button btn57;
    @FXML
    private Button btn60;
    @FXML
    private Button btn61;
    @FXML
    private Button btn62;
    @FXML
    private Button btn63;
    @FXML
    private Button btn64;
    @FXML
    private Button btn65;
    @FXML
    private Button btn66;
    @FXML
    private Button btn67;
    @FXML
    private Button btn70;
    @FXML
    private Button btn71;
    @FXML
    private Button btn72;
    @FXML
    private Button btn73;
    @FXML
    private Button btn74;
    @FXML
    private Button btn75;
    @FXML
    private Button btn76;
    @FXML
    private Button btn77;
    @FXML
    private Button hintBtn;
    @FXML
    public static Label minesLabel;
    @FXML
    private Label selectedLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private static GridPane gameGPane;

    private int secondsElapsed = 0;
    private static Timeline timeline;

    public void startTime(){
        secondsElapsed = 0;
        timeLabel.setText("00:00");
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    int minutes = secondsElapsed/60;
                    int seconds = secondsElapsed%60;
                    timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
                    secondsElapsed++;
                }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void showTile(Button btn, int row, int col) {
        if (mainGame.hasBomb(row, col)) {
            for (int i = 0; i < mainGame.gameTiles.length; i++) {
                for (int j = 0; j < mainGame.gameTiles[i].length; j++) {
                    String btnID = "btn" + j + i;
                    System.out.println("btnID before mine: "+btnID);
                    btn = (Button) gameGPane.lookup("#" + btnID);
                    if (mainGame.hasBomb(i, j)) {
                        Image mineImg = new Image("9.png");
                        ImageView imageView = new ImageView(mineImg);
                        btn.setGraphic(imageView);
                    } else {
                        btn.setText(Integer.toString(mainGame.adjMines(i, j)));
                        mainGame.aiPlay();
                    }
                }
            }
            timeline.stop();
            mainGame.gameOver();
        }
        else {
            btn.setText(Integer.toString(mainGame.adjMines(row, col)));
            mainGame.aiPlay();
        }
    }
    public static void aiShowTile(int row, int col){
        if (mainGame.hasBomb(row, col)) {
            for (int i = 0; i < mainGame.gameTiles.length; i++) {
                for (int j = 0; j < mainGame.gameTiles[i].length; j++) {
                    String btnID = "btn" + j + i;
                    Button btn = (Button) gameGPane.lookup("#" + btnID);
                    if (mainGame.hasBomb(i, j)) {
                        Image mineImg = new Image("9.png");
                        ImageView imageView = new ImageView(mineImg);
                        btn.setGraphic(imageView);
                    } else {
                        btn.setText(Integer.toString(mainGame.adjMines(i, j)));
                    }
                }
            }
        }
        else {
            String btnID = "btn" + col + row;
            Button btn = (Button) gameGPane.lookup("#" + btnID);
            btn.setText(Integer.toString(mainGame.adjMines(row, col)));
        }
    }
    public void btnClick(MouseEvent actionEvent) throws IOException {
        Button btnClicked = (Button) actionEvent.getSource();
        MouseButton click = actionEvent.getButton();
        String btnName = btnClicked.getId();
        String[] btnID = btnName.split("btn");
        int btnRow = Integer.parseInt(btnID[1].substring(0, 1));
        int btnCol = Integer.parseInt(btnID[1].substring(1));
        selectedLabel.setText("["+btnID[1].substring(1)+","+btnID[1].substring(0, 1)+"]");
        if(click==MouseButton.SECONDARY){
            mainGame.flagTile(btnClicked, btnCol, btnRow);
        }
        else{
            if(!mainGame.hasFlag(btnID[1].substring(1),btnID[1].substring(0, 1))){
                showTile(btnClicked, btnCol, btnRow);
            }
            else{
                System.out.println("The selected Tile has a flag");
            }
        }
    }
}
