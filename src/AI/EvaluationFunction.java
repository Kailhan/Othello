package AI;

import Core.Board;
import Core.Logic;

public class EvaluationFunction {


    private Logic logic;
    private Board board;
    private int[][] boardGrid;

    private final static int WEIGHT1 = 100;
    private final static int WEIGHT2 = 100;
    private final static int WEIGHT3 = 100;
    private final static int WEIGHT4 = 100;
    private final static int BLACK = 1;
    private final static int WHITE = -1;


    public EvaluationFunction(){
      this.logic = new Logic();
      this.board = new Board();
      this.boardGrid = board.getBoardGrid();
}


    public int evaluate(){
        int totalscore = 0;
        int numberOfCorners;
        int numberOfMoves;

        int numberOfCoins =  ((board.getNrBlackSquares() - board.getNrWhiteSquares()) / (board.getNrBlackSquares() + board.getNrWhiteSquares()));

        if(Logic.numberSquaresAllowed(this.board, BLACK) + Logic.numberSquaresAllowed(this.board, WHITE) != 0){
            numberOfMoves =  ((Logic.numberSquaresAllowed(this.board, BLACK) - Logic.numberSquaresAllowed(this.board, WHITE)) / (Logic.numberSquaresAllowed(this.board, BLACK) + Logic.numberSquaresAllowed(this.board, WHITE)));
        }
        else numberOfMoves = 0;

        if(getBlackCorners() + getWhiteCorners() != 0) {
            numberOfCorners = ((getBlackCorners() - getWhiteCorners()) / (getBlackCorners() + getWhiteCorners()));
        }
        else numberOfCorners = 0;

        return totalscore = WEIGHT1 * numberOfCoins + WEIGHT2 * numberOfCorners + WEIGHT3 * numberOfMoves /*+ WEIGHT4 * Teritoryscore*/;
        //to be checked if its the correct way to add scores. .
    }

    //number of blackSquares in corners
    public int getBlackCorners() {
        int nrBlackCorners = 0;
        for (int i = 0; i < boardGrid.length; i += boardGrid.length-1)
            for (int j = 0; j < boardGrid[i].length; j += boardGrid.length-1)
                if (boardGrid[i][j] == BLACK)
                    nrBlackCorners++;

        return nrBlackCorners;
    }

    //number of whiteSquares in corners
    public int getWhiteCorners() {
        int nrWhiteCorners = 0;
        for (int i = 0; i < boardGrid.length; i += boardGrid.length-1)
            for (int j = 0; j < boardGrid[i].length; j += boardGrid[i].length-1)
                if (boardGrid[i][j] == WHITE)
                    nrWhiteCorners++;

        return nrWhiteCorners;
    }

}
