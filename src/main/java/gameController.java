import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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

/**
 * This class controls the GUI of the game, and also has some logic of the game
 */
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
    private Label selectedLabel = new Label("[0,0]");
    @FXML
    private Label timeLabel;
    @FXML
    private GridPane gameGPane;

    private int secondsElapsed = 0;
    private static Timeline timeline;
    private int turnCount = 0;
    Stack hintStack = new Stack();
    private SerialPort serialPort;

    /**
     * Starts a time counter for the game.
     */
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

    /**
     * This method shows every tile if the selected tile has a bomb, if not, it shows the number of adjacent mines for the selected tile.
     * @param btn The tile that was clicked
     * @param row The row where the tile is located
     * @param col The column where the tile is located
     */
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
            turnCount++;
            if(turnCount==5){
                hint();
                turnCount=0;
            }
            btn.setText(Integer.toString(mainGame.adjMines(row, col)));
            mainGame.setRevealed(row,col);
            aiPlay();
        }
    }

    /**
     * This method looks for a tile without a mine, that hasn't been revealed, then it adds that tile to the hintStack.
     */
    private void hint(){
        Random randTile = new Random();
        int i,j;
        do{
            i = randTile.nextInt(8);
            j = randTile.nextInt(8);
        } while(!mainGame.hasBomb(i,j)&&mainGame.isRevealed(i,j));
        hintStack.push("The button at the " +"["+Integer.toString(i)+","+Integer.toString(j)+"]"+" position, is safe");
    }

    /**
     * Shows the last added hint to the player if the hintStack isn't empty, if it's empty it only shows an alert that says that there are no hints.
     * @param event the ActionEvent that gets triggered when the hint button is pressed.
     */
    @FXML
    private void showHint(ActionEvent event){
        Alert hintAlert = new Alert(Alert.AlertType.INFORMATION);
        if (hintStack.isEmpty()){
            hintAlert.setTitle("No hints available");
            hintAlert.setHeaderText(null);
            hintAlert.setContentText("There are no hints available!");
            hintAlert.showAndWait();
        }
        else{
            hintAlert.setTitle("Hint");
            hintAlert.setHeaderText(null);
            hintAlert.setContentText(String.valueOf(hintStack.peek()));
            hintStack.pop();
            hintAlert.showAndWait();
        }
    }

    /**
     * If the ai lands on a mine it reveals all tiles, otherwise it reveals the adjacent mines of the tile selected by the ai
     * @param row The row where the tile is located
     * @param col The column where the tile is located
     */
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
            timeline.stop();
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

    /**
     * This method contains the logic for both the easy difficulty and the advanced difficulty
     * If difficulty is off, the AI will select a random playable tile and will play it
     * If the hard mode is true, the AI will consider what's the safest tile known based on the game board state and which ones are unsafe, all this by using Linked Lists to generate all possible safe plays and unsafe plays, after that the AI will always prioritize a tile from the safeList to play, if there are none it will play one from the unsafeList.
     */
    public void aiPlay(){
        Random randomPlay = new Random();
        int i,j;
        if(mainGame.difficulty==false){
            do{
                i = randomPlay.nextInt(8);
                j = randomPlay.nextInt(8);
            } while (mainGame.isRevealed(i,j)&&!mainGame.hasFlag(Integer.toString(i),Integer.toString(j)));
            mainGame.setRevealed(i,j);
            aiShowTile(i,j);
        }
        else{
            LinkedList genList = new LinkedList();
            LinkedList safeList = new LinkedList();
            LinkedList unsafeList = new LinkedList();
            for (int x = 0; x < mainGame.gameTiles.length; x++) {
                for (int y = 0; y < mainGame.gameTiles[x].length; y++) {
                    if(!mainGame.isRevealed(x,y)&&!mainGame.hasFlag(Integer.toString(x),Integer.toString(y))){
                        String pos = Integer.toString(x)+Integer.toString(y);
                        genList.insertFirst(pos);
                    }
                }
            }
            System.out.println("General List at the start");
            genList.displayList();
            while(!genList.isEmpty()&&genList.getSize()<64){
                int randX = randomPlay.nextInt(8);
                int randY = randomPlay.nextInt(8);
                String randPos = Integer.toString(randX)+Integer.toString(randY);
                if(genList.find(randPos) != null){
                    if(isSafe(randX,randY)){
                        System.out.println("Safe List after insertion");
                        safeList.insertFirst(randPos);

                        safeList.displayList();
                    }
                    else{
                        System.out.println("Unsafe List after insertion");
                        unsafeList.insertFirst(randPos);
                        unsafeList.displayList();
                    }
                    System.out.println("General List after deleting");
                    genList.delete(randPos);
                    genList.displayList();
                }
            }
            System.out.println("Final safe list: ");
            safeList.displayList();
            System.out.println("Final unsafe list");
            unsafeList.displayList();
            if(!safeList.isEmpty()){
                String safeNum = (String) safeList.getHead().getData();
                int row = Integer.parseInt(safeNum.substring(0,1));
                int col = Integer.parseInt(safeNum.substring(1,2));
                mainGame.setRevealed(row,col);
                aiShowTile(row, col);
            }
            else if(!unsafeList.isEmpty()){
                String unsafeNum = (String) unsafeList.getHead().getData();
                int row = Integer.parseInt(unsafeNum.substring(0,1));
                int col = Integer.parseInt(unsafeNum.substring(1,2));
                mainGame.setRevealed(row,col);
                aiShowTile(row, col);
            }
        }
    }

    /**
     * This method will look for each adjacent tile of the selected tile and, based on it's number of adjacent mines will consider if the selected tile is safe or not, this can only be considered if the adjacent tiles are revealed.
     * @param row the row where the tile is located
     * @param col the column where the tile is located
     * @return true if the selected tile is safe to play, false if not
     */
    public boolean isSafe(int row, int col){
        System.out.println("Entro: "+Integer.toString(row)+Integer.toString(col));
        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (i == row && j == col) {
                    System.out.println("Continue");
                    continue;
                }
                if((i >= 0) && (i < mainGame.gameTiles.length) && (j >= 0) && (j < mainGame.gameTiles[0].length)){
                    if (mainGame.isRevealed(i, j)) {
                        String btnID = "btn" + j + i;
                        Button btn = (Button) gameGPane.lookup("#" + btnID);
                        if (btn.getText().equals("0")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method detects if the MouseEvent was either a right click, to reveal a tile, or a left click, to set a flag.
     * @param actionEvent Mouse click event
     * @throws IOException
     */
    public void btnClick(MouseEvent actionEvent) throws IOException {
        Button btnClicked = (Button) actionEvent.getSource();
        MouseButton click = actionEvent.getButton();
        String btnName = btnClicked.getId();
        String[] btnID = btnName.split("btn");
        int btnRow = Integer.parseInt(btnID[1].substring(0, 1));
        int btnCol = Integer.parseInt(btnID[1].substring(1));
        selectedLabel.setText("["+btnID[1].substring(0, 1)+","+btnID[1].substring(1)+"]");
        if(click==MouseButton.SECONDARY){
            sendData("F");
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

    /**
     * This method sends a message to the arduino so that it performs a certain action
     * @param data the data that will be sent
     */
    public void sendData(String data){
        byte[] message = data.getBytes();
        serialPort.writeBytes(message,message.length);
    }

    /**
     * This method initializes the Arduino controller, it also adds a data listener to listen for incoming data from the Arduino, this being the pressed buttons on the Arduino, it updates the selected label so that it shows what's the arduino current selection.
     */
    public void ArduinoController(){
        serialPort = SerialPort.getCommPort("COM3");
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
                    case "L":
                        System.out.println("LEFT");
                        updateLabel("LEFT");
                        break;
                    case "R":
                        System.out.println("RIGHT");
                        updateLabel("RIGHT");
                        break;
                    case "U":
                        System.out.println("UP");
                        updateLabel("UP");
                        break;
                    case "D":
                        System.out.println("DOWN");
                        updateLabel("DOWN");
                        break;
                    case "S":
                        System.out.println("SELECT");
                        updateLabel("SELECT");
                        break;
                }
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(serialPort::closePort));
    }

    /**
     * This method receives the specific button that was pressed and updates the label of current selection to the corresponding movement, if the select button is pressed it calls the selection method.
     * @param input received from the arduino as a string
     */
    public void updateLabel(String input){
        String currentText = selectedLabel.getText();
        String currentX = currentText.substring(1,2);
        String currentY = currentText.substring(3,4);
        if (input.equals("LEFT")){
            int x = Integer.parseInt(currentX);
            if(x==0){
                System.out.println("Out of limit");
            }
            else{
                x--;
                String newX = Integer.toString(x);
                System.out.println("Current selection: "+"["+x+","+currentY+"]");
                Platform.runLater(()->{
                    selectedLabel.setText("[" + newX + ',' + currentY + "]");
                });
            }
        }
        if(input.equals("RIGHT")) {
            int x = Integer.parseInt(currentX);
            if (x == 7) {
                System.out.println("Out of limit");
            } else {
                x++;
                String newX = Integer.toString(x);
                System.out.println("Current selection: "+"[" + x + ',' + currentY + "]");
                Platform.runLater(()->{
                selectedLabel.setText("[" + newX + ',' + currentY + "]");
                });
            }
        }
        if(input.equals("UP")){
            int y = Integer.parseInt(currentY);
            if(y==0){
                System.out.println("Out of limit");
            }
            else{
                y--;
                String newY = Integer.toString(y);
                System.out.println("Current selection: "+"["+currentX+","+y+"]");
                Platform.runLater(()->{
                    selectedLabel.setText("[" + currentX + ',' + newY + "]");
                });
            }
        }
        if(input.equals("DOWN")){
            int y = Integer.parseInt(currentY);
            if(y==7){
                System.out.println("Out of limit");
            }
            else{
                y++;
                String newY = Integer.toString(y);
                System.out.println("Current selection: "+"["+currentX+","+y+"]");
                Platform.runLater(()->{
                    selectedLabel.setText("[" + currentX + ',' + newY + "]");
                });
            }
        }
        if(input.equals("SELECT")){
            Platform.runLater(()->{
            arduinoSelection(Integer.parseInt(currentY), Integer.parseInt(currentX));
            });
        }
    }

    /**
     * This method contains the logic for revealing the selected tile by the arduino select button, and if it has a mine it will reveal every tile of the game and end it.
     * @param row
     * @param col
     */
    public void arduinoSelection(int row, int col){
        System.out.println(row);
        System.out.println(col);
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
            sendData("M");
            timeline.stop();
            mainGame.gameOver();
            disableBoard();
        }
        else {
            String btnID = "btn" + col + row;
            System.out.println(btnID);
            Button btn = (Button) gameGPane.lookup("#" + btnID);
            btn.setText(Integer.toString(mainGame.adjMines(row, col)));
            mainGame.setRevealed(row,col);
            aiPlay();
        }
    }

    /**
     * This method disables the board after the game ends.
     */
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
