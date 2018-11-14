package AI;

import Core.Board;
import Core.Logic;
import Core.Settings;


import java.awt.*;

import java.util.ArrayList;
import java.util.Collections;

public class EvaluationFunction {

    private Board board;
    private int[][] boardGrid;
    private Settings settings;

    private final static int WEIGHT1 = 100;
    private final static int WEIGHT2 = 0;       //set to 0 to disable for a while
    private final static int WEIGHT3 = 100;
    private final static int WEIGHT4 = 100;
    private final static int BLACK = 1;
    private final static int WHITE = -1;
    private int score;

    public EvaluationFunction(){
<<<<<<< HEAD
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.settings = new Settings();
    }



    //check what to do with the corners, they are also implemented for the territory function
    public int evaluate(){
=======
        //this.board = new Board();
        //this.boardGrid = board.getBoardGrid();
    }


    public int evaluate(Board board){
>>>>>>> master
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

        if (getTerritScore(BLACK) + getTerritScore(WHITE) !=0 ){
            territory = (getTerritScore(BLACK) - getTerritScore(WHITE))/ (getTerritScore(BLACK) + getTerritScore(WHITE));
        }
        else territory = 0;


        System.out.println("totalscore: " + totalscore);
        System.out.println("numberOfcoins: " + numberOfCoins);
        System.out.println("numberOfMoves: " + numberOfMoves);
        System.out.println("terrScore: " + territory);


        return totalscore = WEIGHT1 * numberOfCoins + WEIGHT2 * numberOfCorners + WEIGHT3 * numberOfMoves + WEIGHT4 * territory;

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


    public int[][] setTerritory(int j){


        int[][] small = new int[4][4];
        int[][] medium = new int[6][6];
        int[][] large = new int[8][8];

        if(j == 4){

            small[0][0]  = 10;
            small[3][3]  = 10;
            small[3][0]  = 10;
            small[0][3]  = 10;

            small[0][1]  = 5;
            small[0][2]  = 5;
            small[1][0]  = 5;
            small[2][0]  = 5;
            small[3][1]  = 5;
            small[3][2]  = 5;
            small[2][3]  = 5;
            small[1][3]  = 5;

            return small;
        }

        if(j == 6) {

            medium[0][0] = 10;
            medium[5][5] = 10;
            medium[0][5] = 10;
            medium[5][0] = 10;

            medium[0][1] = 5;
            medium[1][1] = 5;
            medium[1][0] = 5;
            medium[0][4] = 5;
            medium[1][4] = 5;
            medium[1][5] = 5;
            medium[4][0] = 5;
            medium[4][1] = 5;
            medium[5][1] = 5;
            medium[5][4] = 5;
            medium[4][4] = 5;
            medium[4][5] = 5;

            medium[1][2] = 6;
            medium[1][3] = 6;
            medium[2][1] = 6;
            medium[3][1] = 6;
            medium[4][2] = 6;
            medium[4][3] = 6;
            medium[2][4] = 6;
            medium[3][4] = 6;

            medium[2][0] = 8;
            medium[3][0] = 8;
            medium[0][2] = 8;
            medium[0][3] = 8;
            medium[2][5] = 8;
            medium[3][5] = 8;
            medium[5][2] = 8;
            medium[5][3] = 8;

            return medium;
        }

        if(j == 8){

            large[0][0] = 10;
            large[7][7] = 10;
            large[0][7] = 10;
            large[7][0] = 10;

            large[0][1] = 5;
            large[1][1] = 5;
            large[1][0] = 5;
            large[0][4] = 5;
            large[1][4] = 5;
            large[1][5] = 5;
            large[6][0] = 5;
            large[6][1] = 5;
            large[7][1] = 5;
            large[7][6] = 5;
            large[6][6] = 5;
            large[6][7] = 5;
            
            large[2][1] = 6;
            large[3][1] = 6;
            large[4][1] = 6;
            large[5][1] = 6;
            large[1][2] = 6;
            large[1][3] = 6;
            large[1][4] = 6;
            large[1][5] = 6;
            large[2][6] = 6;
            large[3][6] = 6;
            large[4][6] = 6;
            large[5][6] = 6;
            large[6][2] = 6;
            large[6][3] = 6;
            large[6][4] = 6;
            large[6][5] = 6;

            large[2][2] = 7;
            large[2][3] = 7;
            large[2][4] = 7;
            large[2][5] = 7;
            large[5][2] = 7;
            large[5][3] = 7;
            large[5][4] = 7;
            large[5][5] = 7;
            large[3][2] = 7;
            large[4][2] = 7;
            large[3][5] = 7;
            large[4][5] = 7;

            large[2][0] = 8;
            large[3][0] = 8;
            large[4][0] = 8;
            large[5][0] = 8;
            large[0][2] = 8;
            large[0][3] = 8;
            large[0][4] = 8;
            large[0][5] = 8;
            large[2][7] = 8;
            large[3][7] = 8;
            large[4][7] = 8;
            large[5][7] = 8;
            large[7][2] = 8;
            large[7][3] = 8;
            large[7][4] = 8;
            large[7][5] = 8;

            return large;
        }


        return null;
    }

    //gets the terScore for white
    public int getTerritScore(int player){
        this.score = 0;

        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[i].length; j++) {
                if (boardGrid[i][j] == player) {
                    score += setTerritory(settings.getBoardSize())[i][j];
                }
            }
        }
        return score;
    }



}