public class Logic {

    public int countBlack(Grid grid) {
        int[][] boardGrid = grid.getBoardGrid();
        int nrBlack = 0;
        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[0].length; j++) {
                if (boardGrid[i][j] == 1) nrBlack++;
            }
        }
        return nrBlack;
    }


    public int countWhite(Grid grid){
        int[][] boardGrid = grid.getBoardGrid();
        int nrWhite = 0;
        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[0].length; j++) {
                if (boardGrid[i][j] == 1) nrWhite++;
            }
        }
        return nrWhite;
    }

    //change color; who's turn; only coordinate as input
    public void changeColor(Grid grid, int x, int y){
        int[][] boardGrid = grid.getBoardGrid();

        if(boardGrid[x][y] == 1) boardGrid[x][y] = 2;
        if(boardGrid[x][y] == 2) boardGrid[x][y] = 1;
    }

    //only returns if it possible to place a disk on the selected square (NOT: show the disks that would switch color)
    public boolean checkSquareAllowed(Grid grid, GameEngine gameEngine, int x, int y){
        int[][] board = grid.getBoardGrid();
        int sizeBoard = grid.getSize();
        int turn = gameEngine.getTurn();
        int playerNr = 1;

        if(turn%2 != 0){
            playerNr = 1; //player 1 Black
        }
        else playerNr = 2; //player 2 White

        if(board[x][y] == 0){
            //check corners and sides
            for(int i=x; i>-1; i--){ //vertical North
                if(board[i][y] == 0){
                    break;
                }
                if(board[i][y] == playerNr){
                    return true;
                }
            }
            for(int i=x; i>-1; i--){ //diagonal N-E
                for(int j=y; j<sizeBoard; j++){
                    if(board[i][j] == 0){
                        break;
                    }
                    if(board[i][j] == playerNr){
                        return true;
                    }
                }
            }
            for(int i=y; i>-1; i--){ //horizontal East
                if(board[x][i] == 0){
                    break;
                }
                if(board[x][i] == playerNr){
                    return true;
                }
            }
            for(int i=x; i>sizeBoard; i++){ //diagonal E-Z
                for(int j=y; j<sizeBoard; j++){
                    if(board[i][j] == 0){
                        break;
                    }
                    if(board[i][j] == playerNr){
                        return true;
                    }
                }
            }
            for(int i=x; i>sizeBoard; i++){ //vertical South
                if(board[i][y] == 0){
                    break;
                }
                if(board[i][y] == playerNr){
                    return true;
                }
            }
            for(int i=x; i>sizeBoard; i++){ //diagonal S-W
                for(int j=y; j<-1; j--){
                    if(board[i][j] == 0){
                        break;
                    }
                    if(board[i][j] == playerNr){
                        return true;
                    }
                }
            }
            for(int i=y; i>-1; i--){ //horizontal West
                if(board[x][i] == 0){
                    break;
                }
                if(board[x][i] == playerNr){
                    return true;
                }
            }
        }
        else{
            return false;
        }

        return false;
    }

    public void applyMove(){

    }

    public int numberSquaresAllowed(){
        return 0;
    }

    public void changeTurn(GameEngine gameEngine){
        if(gameEngine.getTurn() == 1){
            gameEngine.setTurn(2);
        } else{
            gameEngine.setTurn(1);
        }
    }


}
