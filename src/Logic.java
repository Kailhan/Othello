public class Logic {

    private Board grid;
    private int[][] board;
    private int turn = 1;

    public Logic(Board grid) {
        this.grid = grid;
        this.board = grid.getBoardGrid();
    }

    public int countBlack() {
        int nrBlack = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) nrBlack++;
            }
        }
        return nrBlack;
    }


    public int countWhite(){
        int nrWhite = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) nrWhite++;
            }
        }
        return nrWhite;
    }

    //change color; who's turn; only coordinate as input
    /*
    public void changeColor(int x, int y){

        if(board[x][y] == 1) board[x][y] = 2;
        if(board[x][y] == 2) board[x][y] = 1;
    }
    */

    //only returns if it possible to place a disk on the selected square (NOT: show the disks that would switch color)
    public boolean checkSquareAllowed(int x, int y){
        int sizeBoard = grid.getSize();
        int playerNr = 1;
        int edgeBoardNeg = -1;

        if(board[x][y] == 0) { //square is empty
            //if (x == 0 && y == 0) {
            if (turn % 2 != 0) {
                playerNr = 1; //player 1 Black
            } else {
                playerNr = 2; //player 2 White
            }

            if (x == 0)

                //check on same color in row/diagonal/colomn
                for (int i = y - 1; i > edgeBoardNeg; i--) { //vertical - North
                    if (y == 0) {
                        break;
                    }
                    if (board[x][i] == 0) {
                        break;
                    }
                    if (board[x][i] == playerNr) {
                        return true;
                    } //disk is same color as the player
                }
            for (int i = y - 1; i > edgeBoardNeg; i--) { //diagonal - NorthEast
                for (int j = x + 1; j < sizeBoard; j++) {
                    if (y == 0 || x == sizeBoard - 1) {
                        break;
                    }
                    if (board[i][j] == 0) {
                        break;
                    }
                    if (board[i][j] == playerNr) {
                        return true;
                    } //disk is same color as the player
                }
            }
            for (int i = x + 1; i < sizeBoard; i++) { //horizontal - East
                if (x == sizeBoard - 1) {
                    break;
                }
                if (board[i][y] == 0) {
                    break;
                }
                if (board[i][y] == playerNr) {
                    return true;
                } //disk is same color as the player
            }
            for (int i = y + 1; i < sizeBoard; i++) { //diagonal - EastSouth
                for (int j = x + 1; j < sizeBoard; j++) {
                    if (y == sizeBoard - 1 || x == sizeBoard - 1) {
                        break;
                    }
                    if (board[i][j] == 0) {
                        break;
                    }
                    if (board[i][j] == playerNr) {
                        return true;
                    } //disk is same color as the player
                }
            }
            for (int i = y + 1; i < sizeBoard; i++) { //vertical - South
                if (y == sizeBoard - 1) {
                    break;
                }
                if (board[x][i] == 0) {
                    break;
                }
                if (board[x][i] == playerNr) {
                    return true;
                } //disk is same color as the player
            }
            for (int i = y + 1; i < sizeBoard; i++) { //diagonal - SouthWest
                for (int j = x - 1; j > edgeBoardNeg; j--) {
                    if (y == sizeBoard - 1 || x == 0) {
                        break;
                    }
                    if (board[i][j] == 0) {
                        break;
                    }
                    if (board[i][j] == playerNr) {
                        return true;
                    } //disk is same color as the player
                }
            }
            for (int i = x - 1; i > edgeBoardNeg; i--) { //horizontal - East
                if (x == 0) {
                    break;
                }
                if (board[i][y] == 0) {
                    break;
                }
                if (board[i][y] == playerNr) {
                    return true;
                } //disk is same color as the player
            }
            for (int i = y - 1; i > edgeBoardNeg; i--) { //diagonal - WestNorth
                for (int j = x - 1; j > edgeBoardNeg; j--) {
                    if (x == 0 || y == 0) {
                        break;
                    }
                    if (board[i][j] == 0) {
                        break;
                    }
                    if (board[i][j] == playerNr) {
                        return true;
                    } //disk is same color as the player
                }
            }
            return false;
        }
        return false;
    }

    //counts the amount of possible moves
    public int numberSquaresAllowed() {
        int nrOfAllowedSquares = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (checkSquareAllowed(i, j)) nrOfAllowedSquares++;
            }
            return nrOfAllowedSquares;
        }
        return 0;
    }

    //disk flips or placed in an empty square
    public void applyMove(int xCoord, int yCoord) {

        if((board[xCoord][yCoord] == 0 || board[xCoord][yCoord] == 2) && turn == 1) board[xCoord][yCoord] = 1; //if the square is empty or white and it's black's turn, put a black disc in the square
        if((board[xCoord][yCoord] == 0 || board[xCoord][yCoord] == 1) && turn == 2) board[xCoord][yCoord] = 2; //if the square is empty or black and it's white's turn, put a white disc in the square
    }

    public void changeTurn(){
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
    }
}