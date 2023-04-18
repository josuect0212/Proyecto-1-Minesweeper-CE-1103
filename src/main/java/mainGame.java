import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.Random;

public class mainGame {
    private gameController controller;
    public static int[][] gameTiles = new int[8][8];
    public static String[] flagList = new String[12];
    static int currentFlags = 0;
    public static boolean difficulty;
    public static boolean aiWL = false;
    public static boolean[][] revealedList = new boolean[8][8];

    /**
     * Creates an instance of the gameController class
     * @param controller the instance for the controller
     */
    public mainGame(gameController controller) {
        this.controller = controller;
    }

    /**
     * Sets the initial board of the game, by setting every tile to 0, to indicate that it doesn't contain a mine. Then, it sets every element of the flagList to an empty string, to indicate that the game starts with no flags
     */
    public static void setBoard(){
        for (int i = 0; i < gameTiles.length; i++){
            for (int j = 0; j < gameTiles[i].length; j++){
                gameTiles[i][j] = 0;
            }
        }
        for (int x = 0; x < flagList.length; x++){
            flagList[x] = "";
        }
    }

    /**
     *  Randomly select 12 tiles and changes their value to 1, to indicate that it contains a mine.
     */
    public static void setMines(){
        int numBombs = 12;
        int bombsPlaced = 0;
        Random random = new Random();
        while (bombsPlaced<numBombs){
            int i = random.nextInt(8);
            int j = random.nextInt(8);
            if (gameTiles[i][j] == 0){
                gameTiles[i][j] = 1;
                bombsPlaced++;
            }
        }
    }

    /**
     * Calculates the number of adjacent mines given a specific tile.
     * @param i the row where the tile is located
     * @param j the column where the tile is located
     * @return the number of mines adjacent to that tile
     */
    public static int adjMines(int i, int j){
        int count = 0;
        for (int adjRow=i-1; adjRow<=i+1;adjRow++){
            for(int adjCol=j-1;adjCol<=j+1;adjCol++){
                if((adjRow >= 0) && (adjRow < gameTiles.length) && (adjCol >= 0) && (adjCol < gameTiles[0].length) && !(adjRow == i && adjCol == j)){
                    if(gameTiles[adjRow][adjCol]==1){
                        count ++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Shows if a tile in a specific position contains a bomb or not
     * @param i the row where the tile is located
     * @param j the column where the tile is located
     * @return true if it has a bomb, false if it doesn't
     */
    public static boolean hasBomb(int i, int j){
        if (gameTiles[i][j] == 1){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * If there are flags available it sets a flag on the tile that was clicked
     * @param btnClicked the Button that was clicked
     * @param i the row where the tile is located
     * @param j the column where the tile is located
     */
    public static void flagTile(Button btnClicked, int i, int j){
        String row = Integer.toString(i);
        String col = Integer.toString(j);
        if (currentFlags == 12){
            System.out.println("No flags available");
        }
        else if(!hasFlag(row,col)){
            currentFlags++;
            Image flagImg = new Image("11.png");
            ImageView imageView = new ImageView(flagImg);
            btnClicked.setGraphic(imageView);
            int count = 0;
            while (count<12){
                if (flagList[count].equals("")){
                    flagList[count] = row+col;
                    System.out.println("flag list add: " + Arrays.toString(flagList));
                    break;
                }
                else{
                    count++;
                }
            }
        }
    }
    /* logic behind a deleteFlag method, does not work properly (Not evaluated)
    private static void deleteFlag(){
        if (hasFlag(row, col)){
            btnClicked.setGraphic(null);
            System.out.println("flag list before: " + Arrays.toString(flagList));
            for (int x = 0; x < flagList.length; x++){
                if (flagList[x].equals(row+col)){
                    flagList[x] = "";
                    currentFlags --;
                    System.out.println("flag list after: " + Arrays.toString(flagList));
                    break;
                }
            }
        }
    }
     */

    /**
     * Shows if a specific tile has a flag or not
     * @param i the row where the tile is located
     * @param j the column where the tile is located
     * @return true if it has a flag, false if not
     */
    public static boolean hasFlag(String i, String j){
        int count = 0;
        while (count<12){
            if (flagList[count].contains(i+j)){
                return true;
            }
            else {
                count++;
            }
        }
        return false;
    }

    /**
     * Shows if a specific tile has been revealed
     * @param row the row where the tile is located
     * @param col the column where the tile is located
     * @return true if it has been revealed, false if not
     */
    public static boolean isRevealed(int row,int col){
        if(revealedList[row][col]==true){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Sets a given tile the revealed status
     * @param row the row where the tile is located
     * @param col the column where the tile is located
     */
    public static void setRevealed(int row, int col){
        revealedList[row][col] = true;
    }

    /**
     * Based on the AI win/lose condition shows a Congratulations! alert if the AI loses, and a You lost! alert if the AI wins.
     */
    public static void gameOver(){
        Alert overAlert = new Alert(Alert.AlertType.INFORMATION);
        overAlert.setTitle("Game Over");
        overAlert.setHeaderText(null);
        if (aiWL){
            overAlert.setContentText("Congratulations! You won!");
        }
        else{
            overAlert.setContentText("You lost!");
        }
        overAlert.showAndWait();
    }
}

