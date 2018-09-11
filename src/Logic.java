public class Logic {

    public int countBlack(){
       return 0;
    }

    public int countWhite(){
        return 0;
    }

    //change color; who's turn; only coordinate as input
    public void changeColor(int x, int y){

    }

    public boolean checkSquareAllowed(Grid grid, int x, int y){
        int[][] board = grid.getBoardGrid();

        return false;
    }

    public int numberSquaresAllowed(){

    }

    public enum changeTurn(GameEngine gameEngine){
        if(gameEngine.getTurn() == WHITE){
            gameEngine.setTurn(BLACK);
        } else{
            gameEngine.setTurn(WHITE);
        }
    }


}
