public class Logic {

    public int countBlack(Board grid) {
        int[][] board = grid.getBoardGrid();
        int nrBlack = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) nrBlack++;
            }
        }
        return nrBlack;
    }


    public int countWhite(Board grid){
        int[][] board = grid.getBoardGrid();
        int nrWhite = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) nrWhite++;
            }
        }
        return nrWhite;
    }

    //change color; who's turn; only coordinate as input
    public void changeColor(Board grid, int x, int y){
        int[][] board = grid.getBoardGrid();

        if(board[x][y] == 1) board[x][y] = 2;
        if(board[x][y] == 2) board[x][y] = 1;
    }

    public boolean checkSquareAllowed(Board grid, GameEngine gameEngine, int x, int y){
        int[][] board = grid.getBoardGrid();
        int sizeBoard = grid.getSize();

        if(board[x][y] == 0){
            if(x==0 && y==0){

            }
            if(x==0){

            }
            if(y==0){

            }
            if(x==sizeBoard && y==sizeBoard){

            }
            if(x==sizeBoard){

            }
            if(y==sizeBoard){

            }
            else{

            }
        } else{
            return false;
        }
        return false;
    }

    public int numberSquaresAllowed(Board grid, GameEngine gameEngine){
        int[][] board = grid.getBoardGrid();

        int nrOfAllowedSquares = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (checkSquareAllowed(grid, gameEngine, i,j)) nrOfAllowedSquares++;
            }
            return nrOfAllowedSquares;
        }
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