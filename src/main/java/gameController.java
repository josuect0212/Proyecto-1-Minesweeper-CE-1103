import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
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
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.Random;

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
    private GridPane gameGPane;

    private int secondsElapsed = 0;
    private static Timeline timeline;
    private int turnCount = 0;

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

    public void showTile(Button btn, int row, int col) {
        if (mainGame.hasBomb(row, col)) {
            for (int i = 0; i < mainGame.gameTiles.length; i++) {
                for (int j = 0; j < mainGame.gameTiles[i].length; j++) {
                    String btnID = "btn" + j + i;
                    btn = (Button) gameGPane.lookup("#" + btnID);
                    if (mainGame.hasBomb(i, j)) {
                        Image mineImg = new Image("9.png");
                        ImageView imageView = new ImageView(mineImg);
                        btn.setGraphic(imageView);
                    } else {
                        btn.setText(Integer.toString(mainGame.adjMines(i, j)));
                    }
                }
            }
            timeline.stop();
            mainGame.gameOver();
            disableBoard();
        }
        else {
            btn.setText(Integer.toString(mainGame.adjMines(row, col)));
            mainGame.setRevealed(row,col);
            aiPlay();
        }
    }
    public void aiShowTile(int row, int col){
        if (mainGame.hasBomb(row, col)) {
            for (int i = 0; i < mainGame.gameTiles.length; i++) {
                for (int j = 0; j < mainGame.gameTiles[i].length; j++) {
                    String btnID = "btn" + j + i;
                    Button btn = (Button) gameGPane.lookup("#" + btnID);
                    if (mainGame.hasBomb(i, j)) {
                        Image mineImg = new Image("9.png");
                        ImageView imageView = new ImageView(mineImg);
                        btn.setGraphic(imageView);
                    }
                    else {
                        btn.setText(Integer.toString(mainGame.adjMines(i, j)));
                    }
                }
            }
            mainGame.aiWL = true;
            mainGame.gameOver();
            disableBoard();
        }
        else {
            String btnID = "btn" + col + row;
            Button btn = (Button) gameGPane.lookup("#" + btnID);
            btn.setText(Integer.toString(mainGame.adjMines(row, col)));
        }
    }
    public void aiPlay(){
        Random randomPlay = new Random();
        int i,j;
        if(mainGame.difficulty==false){
            do{
                i = randomPlay.nextInt(8);
                j = randomPlay.nextInt(8);
            } while (mainGame.isRevealed(i,j)&&!mainGame.hasFlag(Integer.toString(i),Integer.toString(j)));
            aiShowTile(i,j);
        }
    }
    public void btnClick(MouseEvent actionEvent) throws IOException {
        Button btnClicked = (Button) actionEvent.getSource();
        MouseButton click = actionEvent.getButton();
        String btnName = btnClicked.getId();
        String[] btnID = btnName.split("btn");
        int btnRow = Integer.parseInt(btnID[1].substring(0, 1));
        int btnCol = Integer.parseInt(btnID[1].substring(1));
        selectedLabel.setText("["+btnID[1].substring(0, 1)+","+btnID[1].substring(1)+"]");
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
    public void ArduinoController(){
        SerialPort serialPort = SerialPort.getCommPort("COM3");
        serialPort.openPort();
        serialPort.setComPortParameters(9600, Byte.SIZE, serialPort.ONE_STOP_BIT, serialPort.NO_PARITY);
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_WRITE_BLOCKING,0,0);
        boolean hasOpened = serialPort.openPort();
        if (!hasOpened){
            throw new IllegalStateException("Failed to open serial port");
        }
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
                byte[] newData = new byte[serialPort.bytesAvailable()];
                int numRead = serialPort.readBytes(newData, newData.length);
                String data = new String(newData);
                switch (data){
                    case "LEFT":
                        selectedLabel.setText("LEFT");
                        break;
                    case "RIGHT":
                        selectedLabel.setText("RIGHT");
                        break;
                    case "UP":
                        selectedLabel.setText("UP");
                        break;
                    case "DOWN":
                        selectedLabel.setText("DOWN");
                        break;
                    case "SELECT":
                        selectedLabel.setText("SELECT");
                        break;
                }
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(serialPort::closePort));
    }
    public void disableBoard(){
        for(int i = 0; i<mainGame.gameTiles.length; i++){
            for(int j = 0; j<mainGame.gameTiles[i].length; j++){
                String btn = "btn"+j+i;
                Button button = (Button) gameGPane.lookup("#"+btn);
                button.setDisable(true);
            }
        }
    }
}
