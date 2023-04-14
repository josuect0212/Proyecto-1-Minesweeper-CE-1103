import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Random;

public class mainGame {
    private gameController controller;
    public static int[][] gameTiles = new int[8][8];
    public static String[] flagList = new String[12];
    static int currentFlags = 0;
    public static boolean difficulty;
    public static boolean[][] revealedList = new boolean[8][8];
    public mainGame(gameController controller) {
        this.controller = controller;
    }
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
    public static int adjMines(int i, int j){
        int count = 0;
        for (int adjRow=i-1; adjRow<=i+1;adjRow++){
            for(int adjCol=j-1;adjCol<=j+1;adjCol++){
                if((adjRow > 0) && (adjRow < gameTiles.length) && (adjCol >= 0) && (adjCol < gameTiles[0].length) && !(adjRow == i && adjCol == j)){
                    if(gameTiles[adjRow][adjCol]==1){
                        count ++;
                    }
                }
            }
        }
        return count;
    }
    public static boolean hasBomb(int i, int j){
        if (gameTiles[i][j] == 1){
            return true;
        }
        else {
            return false;
        }
    }
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
    public static boolean isRevealed(int row,int col){
        if(revealedList[row][col]==true){
            return true;
        }
        else{
            return false;
        }
    }
    public static void setRevealed(int row, int col){
        revealedList[row][col] = true;
    }
    public static void aiPlay(){
        Random randomPlay = new Random();
        if(difficulty=false){
            int i = randomPlay.nextInt(8);
            int j = randomPlay.nextInt(8);
            System.out.println(i);
            System.out.println(j);
            if (!isRevealed(i,j)){
                gameController.aiShowTile(i,j);
            }
            else{
                aiPlay();
            }
        }
    }
    public static void gameOver(){}}

