public class Logic {

//    private Board grid;
//    private int[][] board;
//    private int turn = 1;
//
//    public Logic(Board grid) {
//        this.grid = grid;
//        this.board = grid.getBoardGrid();
//    }

    //change color; who's turn; only coordinate as input
    /*
    public void changeColor(int x, int y){

        if(board[x][y] == 1) board[x][y] = 2;
        if(board[x][y] == 2) board[x][y] = 1;
    }
    */

    //only returns if it possible to place a disk on the selected square (NOT: show the disks that would switch color)
    public static boolean checkSquareAllowed(int x, int y, Board board, int playerNr) {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();

        if (boardGrid[x][y] == 0) {
            if (x != 0 && x != boardSize - 1 && y != 0 && y != boardSize - 1) { //middle
                if(checkDirectionAllowed(x, y, board, playerNr, "N") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "E") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "S") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SW") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "W") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NW") == true)
                    return true;
            }
            else if (x == 0 && y != 0 && y != boardSize - 1) { //left edge
                if(checkDirectionAllowed(x, y, board, playerNr, "N") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "E") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "S") == true)
                    return true;
            }
            else if (x == boardSize - 1 && y != 0 && y != boardSize - 1) { //right edge
                if(checkDirectionAllowed(x, y, board, playerNr, "N") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "S") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SW") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "W") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NW") == true)
                    return true;
            }
            else if (x != 0 && x != boardSize - 1 && y == 0) { //top edge
                if(checkDirectionAllowed(x, y, board, playerNr, "E") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "S") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SW") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "W") == true ||)
                    return true;
            }
            else if (x != 0 && x != boardSize - 1 && y == boardSize - 1) { //bottom edge
                if(checkDirectionAllowed(x, y, board, playerNr, "N") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "E") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "W") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NW") == true)
                    return true;
            }
            else if (x == 0 && y == 0) { //top left corner
                if(checkDirectionAllowed(x, y, board, playerNr, "E") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "S") == true ||)
                    return true;
            }
            else if (x == 0 && y == boardSize-1) { //bottom left corner
                if(checkDirectionAllowed(x, y, board, playerNr, "N") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NE") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "E") == true)
                    return true;
            }
            else if (x == boardSize-1 && y == 0) { //top right corner
                if(checkDirectionAllowed(x, y, board, playerNr, "S") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "SW") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "W") == true)
                    return true;
            }
            else if (x == boardSize-1 && y == boardSize-1) { //bottom right corner
                if(checkDirectionAllowed(x, y, board, playerNr, "N") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "W") == true ||
                        checkDirectionAllowed(x, y, board, playerNr, "NW") == true)
                    return true;
            }
        }
        return false;
    }

    public static boolean checkDirectionAllowed(int x, int y, Board board, int playerNr, String direction)
    {
        switch(direction)
        {
            case "N" :
            case "NE" :
            case "E" :
            case "SE" :
            case "S" :
            case "SW" :
            case "W" :
            case "NW" :

            default:

        }
        return false;
    }

//        int sizeBoard = grid.getSize();
//        int playerNr = 1;
//        int edgeBoardNeg = -1;
//
//        if(board[x][y] == 0) { //square is empty
//            //if (x == 0 && y == 0) {
//            if (turn % 2 != 0) {
//                playerNr = 1; //player 1 Black
//            } else {
//                playerNr = 2; //player 2 White
//            }
//
//            if (x == 0)
//
//                //check on same color in row/diagonal/colomn
//                for (int i = y - 1; i > edgeBoardNeg; i--) { //vertical - North
//                    if (y == 0) {
//                        break;
//                    }
//                    if (board[x][i] == 0) {
//                        break;
//                    }
//                    if (board[x][i] == playerNr) {
//                        return true;
//                    } //disk is same color as the player
//                }
//            for (int i = y - 1; i > edgeBoardNeg; i--) { //diagonal - NorthEast
//                for (int j = x + 1; j < sizeBoard; j++) {
//                    if (y == 0 || x == sizeBoard - 1) {
//                        break;
//                    }
//                    if (board[i][j] == 0) {
//                        break;
//                    }
//                    if (board[i][j] == playerNr) {
//                        return true;
//                    } //disk is same color as the player
//                }
//            }
//            for (int i = x + 1; i < sizeBoard; i++) { //horizontal - East
//                if (x == sizeBoard - 1) {
//                    break;
//                }
//                if (board[i][y] == 0) {
//                    break;
//                }
//                if (board[i][y] == playerNr) {
//                    return true;
//                } //disk is same color as the player
//            }
//            for (int i = y + 1; i < sizeBoard; i++) { //diagonal - EastSouth
//                for (int j = x + 1; j < sizeBoard; j++) {
//                    if (y == sizeBoard - 1 || x == sizeBoard - 1) {
//                        break;
//                    }
//                    if (board[i][j] == 0) {
//                        break;
//                    }
//                    if (board[i][j] == playerNr) {
//                        return true;
//                    } //disk is same color as the player
//                }
//            }
//            for (int i = y + 1; i < sizeBoard; i++) { //vertical - South
//                if (y == sizeBoard - 1) {
//                    break;
//                }
//                if (board[x][i] == 0) {
//                    break;
//                }
//                if (board[x][i] == playerNr) {
//                    return true;
//                } //disk is same color as the player
//            }
//            for (int i = y + 1; i < sizeBoard; i++) { //diagonal - SouthWest
//                for (int j = x - 1; j > edgeBoardNeg; j--) {
//                    if (y == sizeBoard - 1 || x == 0) {
//                        break;
//                    }
//                    if (board[i][j] == 0) {
//                        break;
//                    }
//                    if (board[i][j] == playerNr) {
//                        return true;
//                    } //disk is same color as the player
//                }
//            }
//            for (int i = x - 1; i > edgeBoardNeg; i--) { //horizontal - East
//                if (x == 0) {
//                    break;
//                }
//                if (board[i][y] == 0) {
//                    break;
//                }
//                if (board[i][y] == playerNr) {
//                    return true;
//                } //disk is same color as the player
//            }
//            for (int i = y - 1; i > edgeBoardNeg; i--) { //diagonal - WestNorth
//                for (int j = x - 1; j > edgeBoardNeg; j--) {
//                    if (x == 0 || y == 0) {
//                        break;
//                    }
//                    if (board[i][j] == 0) {
//                        break;
//                    }
//                    if (board[i][j] == playerNr) {
//                        return true;
//                    } //disk is same color as the player
//                }
//            }
//            return false;
//        }
//        return false;
//    }

    //counts the amount of possible moves
    public int numberSquaresAllowed(Board board)
    {
        int boardSize = board.getSize();
        int nrOfAllowedSquares = 0;
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
                if (checkSquareAllowed(i, j, board, board.getCurrentPlayer()))
                    nrOfAllowedSquares++;
            return nrOfAllowedSquares;
        }
        return 0;
    }




}