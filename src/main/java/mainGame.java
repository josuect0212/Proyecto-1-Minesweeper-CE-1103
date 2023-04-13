import java.util.Random;

public class mainGame {
    private gameController controller;
    public static int[][] gameTiles = new int[8][8];

    public mainGame(gameController controller) {
        this.controller = controller;
    }
    public static void setBoard(){
        for (int i = 0; i < gameTiles.length; i++){
            for (int j = 0; j < gameTiles[i].length; j++){
                gameTiles[i][j] = 0;
            }
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
    public static void gameOver(){
    }
}
