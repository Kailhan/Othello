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
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.settings = new Settings();
    }



    //check what to do with the corners, they are also implemented for the territory function
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

        if (getTerritScoreBlack() + getTerritScoreWhite() !=0 ){
            territory = (getTerritScoreBlack()-getTerritScoreWhite()) / ((getTerritScoreBlack() + getTerritScoreWhite()));
        }
        else territory = 0;

        /*
        //debug
        System.out.println("totalscore: " + totalscore);
        System.out.println("numberOfcoins: " + numberOfCoins);
        System.out.println("numberOfMoves: " + numberOfMoves);
        System.out.println("terrScore: " + territory);
        */

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



    //creates arraylists with the coordinates and the terValue
    public ArrayList createArraylists(int x, int j){

        ArrayList<Point> eight = new ArrayList<>();
        ArrayList<Point> seven  = new ArrayList<>();
        ArrayList<Point> six  = new ArrayList<>();
        ArrayList<Point> five  = new ArrayList<>();
        int[][] small = new int[4][4];
        int[][] medium = new int[6][6];
        int[][] large = new int[8][8];

        if(j == 1){

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
        }

        if(j == 2) {

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
        }

        if(j == 3){

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


            six.add(createPoint(2,1));
            six.add(createPoint(3,1));
            six.add(createPoint(4,1));
            six.add(createPoint(5,1));
            six.add(createPoint(1,2));
            six.add(createPoint(1,3));
            six.add(createPoint(1,4));
            six.add(createPoint(1,5));
            six.add(createPoint(2,6));
            six.add(createPoint(3,6));
            six.add(createPoint(4,6));
            six.add(createPoint(5,6));
            six.add(createPoint(6,2));
            six.add(createPoint(6,3));
            six.add(createPoint(6,4));
            six.add(createPoint(6,5));

            seven.add(createPoint(2,2));
            seven.add(createPoint(2,3));
            seven.add(createPoint(2,4));
            seven.add(createPoint(2,5));
            seven.add(createPoint(5,2));
            seven.add(createPoint(5,3));
            seven.add(createPoint(5,4));
            seven.add(createPoint(5,5));
            seven.add(createPoint(3,2));
            seven.add(createPoint(4,2));
            seven.add(createPoint(3,5));
            seven.add(createPoint(4,5));

            eight.add(createPoint(2,0));
            eight.add(createPoint(3,0));
            eight.add(createPoint(4,0));
            eight.add(createPoint(5,0));
            eight.add(createPoint(0,2));
            eight.add(createPoint(0,3));
            eight.add(createPoint(0,4));
            eight.add(createPoint(0,5));
            eight.add(createPoint(2,7));
            eight.add(createPoint(3,7));
            eight.add(createPoint(4,7));
            eight.add(createPoint(5,7));
            eight.add(createPoint(7,2));
            eight.add(createPoint(7,3));
            eight.add(createPoint(7,4));
            eight.add(createPoint(7,5));
        }



        switch(x){
            case 5: return five;
            case 6: return six;
            case 7: return seven;
            case 8: return eight;
        }

        return null;
    }

    //gets the terScore for white
    public int getTerritScoreWhite(){
        this.score = 0;

        ArrayList<Point> actualPoints = new ArrayList<>();
        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[i].length; j++) {
                if (boardGrid[i][j] == WHITE) {
                    actualPoints.add(createPoint(i, j));
                }
            }
        }

        if(settings.getBoardSize() == 4){    //for small map

            int occur5 = Collections.frequency(createArraylists(5, "small"), actualPoints);
            int occur6 = Collections.frequency(createArraylists(6, "small"), actualPoints);
            int occur7 = Collections.frequency(createArraylists(7, "small"), actualPoints);
            int occur8 = Collections.frequency(createArraylists(8, "small"), actualPoints);

            this.score = 5*occur5 + 6*occur6 + 7*occur7 + 8*occur8 + 10*getWhiteCorners();
            return score;

        }

        if(settings.getBoardSize() == 6) { //for medium map

            int occur5 = Collections.frequency(createArraylists(5, "medium"), actualPoints);
            int occur6 = Collections.frequency(createArraylists(6, "medium"), actualPoints);
            int occur7 = Collections.frequency(createArraylists(7, "medium"), actualPoints);
            int occur8 = Collections.frequency(createArraylists(8, "medium"), actualPoints);

            this.score = 5 * occur5 + 6 * occur6 + 7 * occur7 + 8 * occur8 + 10 * getWhiteCorners();
            return score;
        }

        if(settings.getBoardSize() == 8) { //for large map

            int occur5 = Collections.frequency(createArraylists(5, "large"), actualPoints);
            int occur6 = Collections.frequency(createArraylists(6, "large"), actualPoints);
            int occur7 = Collections.frequency(createArraylists(7, "large"), actualPoints);
            int occur8 = Collections.frequency(createArraylists(8, "large"), actualPoints);

            this.score = 5 * occur5 + 6 * occur6 + 7 * occur7 + 8 * occur8 + 10 * getWhiteCorners();
            return score;
        }

        return 0;
    }


    //gets the terScore for black
    public int getTerritScoreBlack(){
        this.score = 0;

        ArrayList<Point> actualPoints = new ArrayList<>();
        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[i].length; j++) {
                if (boardGrid[i][j] == BLACK) {
                    actualPoints.add(createPoint(i, j));
                }
            }
        }

        if(settings.getBoardSize() == 4){    //for small map

            int occur5 = Collections.frequency(createArraylists(5, "small"), actualPoints);
            int occur6 = Collections.frequency(createArraylists(6, "small"), actualPoints);
            int occur7 = Collections.frequency(createArraylists(7, "small"), actualPoints);
            int occur8 = Collections.frequency(createArraylists(8, "small"), actualPoints);

            this.score = 5*occur5 + 6*occur6 + 7*occur7 + 8*occur8 + 10*getBlackCorners();
            return score;
        }

        if(settings.getBoardSize() == 6) { //for medium map

            int occur5 = Collections.frequency(createArraylists(5, "medium"), actualPoints);
            int occur6 = Collections.frequency(createArraylists(6, "medium"), actualPoints);
            int occur7 = Collections.frequency(createArraylists(7, "medium"), actualPoints);
            int occur8 = Collections.frequency(createArraylists(8, "medium"), actualPoints);

            this.score = 5 * occur5 + 6 * occur6 + 7 * occur7 + 8 * occur8 + 10 * getBlackCorners();
            return score;
        }

        if(settings.getBoardSize() == 8) { //for large map

            int occur5 = Collections.frequency(createArraylists(5, "large"), actualPoints);
            int occur6 = Collections.frequency(createArraylists(6, "large"), actualPoints);
            int occur7 = Collections.frequency(createArraylists(7, "large"), actualPoints);
            int occur8 = Collections.frequency(createArraylists(8, "large"), actualPoints);

            this.score = 5 * occur5 + 6 * occur6 + 7 * occur7 + 8 * occur8 + 10 * getBlackCorners();
            return score;
        }
            return 0;
    }

}