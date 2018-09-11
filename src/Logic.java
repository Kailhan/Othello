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

    public boolean checkSquareAllowed(Grid grid, GameEngine gameEngine, int x, int y){
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
