package AI;

import Core.Board;
import Core.Logic;

public class EvaluationFunction {

    private Board board;
    private int[][] boardGrid;

    private final static int WEIGHT1 = 100;
    private final static int WEIGHT2 = 100;
    private final static int WEIGHT3 = 100;
    private final static int WEIGHT4 = 100;
    private final static int BLACK = 1;
    private final static int WHITE = -1;


    public EvaluationFunction(){
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
    }


    public int evaluate(){
        int totalscore = 0;
        int numberOfCorners;
        int numberOfMoves;
        int blackMoves = 0;
        int whiteMoves = 0;
        int territory;

        int numberOfCoins =  ((board.getNrSquares(BLACK) - board.getNrSquares(WHITE)) / (board.getNrSquares(BLACK) + board.getNrSquares(WHITE)));

        if(board.getCurrentPlayer() == WHITE){
            whiteMoves = Logic.numberSquaresAllowed(this.board);
            board.changePlayer();
            blackMoves = Logic.numberSquaresAllowed(this.board);
        }
        else{
            blackMoves = Logic.numberSquaresAllowed(this.board);
            board.changePlayer();
            whiteMoves = Logic.numberSquaresAllowed(this.board);
        }

        if(blackMoves + whiteMoves != 0){
            numberOfMoves =  ((blackMoves - whiteMoves) / (blackMoves + whiteMoves));
        }
        else numberOfMoves = 0;

        if(getBlackCorners() + getWhiteCorners() != 0) {
            numberOfCorners = ((getBlackCorners() - getWhiteCorners()) / (getBlackCorners() + getWhiteCorners()));
        }
        else numberOfCorners = 0;

        if (getTerritScoreBlack() + getTerritScoreWhite() !=0){
            territory = (getTerritScoreBlack()-getTerritScoreWhite()) / ((getTerritScoreBlack() + getTerritScoreWhite()));
        }
        else territory = 0;


        return totalscore = WEIGHT1 * numberOfCoins + WEIGHT2 * numberOfCorners + WEIGHT3 * numberOfMoves + WEIGHT4 * territory;
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

    public int getTerritScoreWhite(){

        return 0;
    }

    public int getTerritScoreBlack(){

        return 0;
    }

}