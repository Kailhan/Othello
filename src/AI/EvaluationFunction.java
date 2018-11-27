package AI;

import AI.AI;
import Core.Board;
import Core.Logic;
import Core.Settings;

import java.util.Random;

public class EvaluationFunction {

    private Board board;
    private int[][] boardGrid;
    private Settings settings;
    private double[][] cellValues;

    private double coinWeightPoly0 = 100;
    private double coinWeightPoly1 = 0;
    private double coinWeightPoly2 = 0;
    private double coinWeightPoly3 = 0;
    private double cornerWeightPoly0 = 0;       //set to 0 to disable for a while
    private double cornerWeightPoly1 = 0;
    private double cornerWeightPoly2 = 0;
    private double cornerWeightPoly3 = 0;
    private double moveWeightPoly0 = 100;
    private double moveWeightPoly1 = 0;
    private double moveWeightPoly2 = 0;
    private double moveWeightPoly3 = 0;
    private double territoryWeightPoly0 = 100;
    private double territoryWeightPoly1 = 0;
    private double territoryWeightPoly2 = 0;
    private double territoryWeightPoly3 = 0;

    private final static int BLACK = 1;
    private final static int WHITE = -1;
    private int score;

    public EvaluationFunction(Board cBoard){
        this.board = cBoard;
        this.boardGrid = cBoard.getBoardGrid();
        this.settings = new Settings();
    }

    public EvaluationFunction(){
        this(new Board());
    }

    public int evaluate(){
        int totalScore;
        int blackMoves;
        int whiteMoves;
        double numberOfCorners;
        double numberOfMoves;
        double numberOfCoins;
        double territory;

        numberOfCoins = (double) (this.board.getNrSquares(BLACK) - this.board.getNrSquares(WHITE)) / (this.board.getNrSquares(BLACK) + this.board.getNrSquares(WHITE));

        if(this.board.getCurrentPlayer() == WHITE){
            whiteMoves = Logic.numberSquaresAllowed(this.board);
            this.board.changePlayer();
            blackMoves = Logic.numberSquaresAllowed(this.board);
        }
        else {
            blackMoves = Logic.numberSquaresAllowed(this.board);
            this.board.changePlayer();
            whiteMoves = Logic.numberSquaresAllowed(this.board);
        }

        if(blackMoves + whiteMoves != 0){
            numberOfMoves =  (double) ((blackMoves - whiteMoves) / (blackMoves + whiteMoves));
        } else numberOfMoves = 0;

        if(getCorners(BLACK) + getCorners(WHITE) != 0) {
            numberOfCorners = (double) ((getCorners(BLACK) - getCorners(WHITE)) / (getCorners(BLACK) + getCorners(WHITE)));
        } else numberOfCorners = 0;

        if (getTerritoryScore(BLACK) + getTerritoryScore(WHITE) !=0 ){
            territory = (double) (getTerritoryScore(BLACK) - getTerritoryScore(WHITE))/ (getTerritoryScore(BLACK) + getTerritoryScore(WHITE));
        } else territory = 0;

//        if (this.board.getTurn() > 10 && this.board.getTurn() < 20) { //even kijken wat de beste strategie is, ook rekening houden met de grote van de borden
//            this.coinWeight = 50;
//            this.cornerWeightPoly0 = 50;
//            this.moveWeightPoly0 = 50;
//            this.territoryWeightPoly0 = 50;
//        }
//
//        if (this.board.getTurn() > 20 && this.board.getTurn() < 32) { //even kijken wat de beste strategie is, ook rekening houden met de grote van de borden
//            this.coinWeight = 50;
//            this.cornerWeightPoly0 = 50;
//            this.moveWeightPoly0 = 50;
//            this.territoryWeightPoly0 = 50;
//        }

        totalScore = (int) (calcCoinWeight(board.getTurn()) * numberOfCoins + calcCornerWeight(board.getTurn()) * numberOfCorners +
                calcMoveWeight(board.getTurn()) * numberOfMoves + calcTerritoryWeight(board.getTurn()) * territory);

        System.out.println("numberOfcoins: " + numberOfCoins);
        System.out.println("numberOfMoves: " + numberOfMoves);
        System.out.println("territoryScoreWhite: " + getTerritoryScore(WHITE));
        System.out.println("territoryScoreBlack: " + getTerritoryScore(BLACK));
        System.out.println("terrScore: " + territory);
        System.out.println("totalscore: " + totalScore);

        return totalScore;
    }

    public int evaluate(Board cBoard) {
        int totalScore = 0;
        double numberOfCorners;
        double numberOfMoves;
        double numberOfCoins;
        int blackMoves = 0;
        int whiteMoves = 0;
        double territory;

        numberOfCoins = (double) (cBoard.getNrSquares(BLACK) - cBoard.getNrSquares(WHITE)) / (cBoard.getNrSquares(BLACK) + cBoard.getNrSquares(WHITE));

        if (cBoard.getCurrentPlayer() == WHITE) {
            whiteMoves = Logic.numberSquaresAllowed(cBoard);
            cBoard.changePlayer();
            blackMoves = Logic.numberSquaresAllowed(cBoard);
        } else {
            blackMoves = Logic.numberSquaresAllowed(cBoard);
            cBoard.changePlayer();
            whiteMoves = Logic.numberSquaresAllowed(cBoard);
        }

        if (blackMoves + whiteMoves != 0) {
            numberOfMoves = (double) ((blackMoves - whiteMoves) / (blackMoves + whiteMoves));
        } else numberOfMoves = 0;

        if (getCorners(cBoard, BLACK) + getCorners(cBoard, WHITE) != 0) {
            numberOfCorners = (double) ((getCorners(cBoard, BLACK) - getCorners(cBoard, WHITE)) / (getCorners(cBoard, BLACK) + getCorners(cBoard, WHITE)));
        } else numberOfCorners = 0;

        if (getTerritoryScore(BLACK, cBoard) + getTerritoryScore(WHITE, cBoard) != 0) {
            territory = (double) (getTerritoryScore(BLACK, cBoard) - getTerritoryScore(WHITE, cBoard)) / (getTerritoryScore(BLACK, cBoard) + getTerritoryScore(WHITE, cBoard));
        } else territory = 0;


//        if (cBoard.getTurn() > 10 && cBoard.getTurn() < 20) { //even kijken wat de beste strategie is, ook rekening houden met de grote van de borden
//            this.coinWeight = 50;
//            this.cornerWeightPoly0 = 50;
//            this.moveWeightPoly0 = 50;
//            this.territoryWeightPoly0 = 50;
//        }
//
//        if (cBoard.getTurn() > 20 && cBoard.getTurn() < 32) { //even kijken wat de beste strategie is, ook rekening houden met de grote van de borden
//            this.coinWeight = 50;
//            this.cornerWeightPoly0 = 50;
//            this.moveWeightPoly0 = 50;
//            this.territoryWeightPoly0 = 50;
//        }

        totalScore = (int) (coinWeightPoly0 * numberOfCoins + cornerWeightPoly0 * numberOfCorners + moveWeightPoly0 * numberOfMoves + territoryWeightPoly0 * territory);

//        System.out.println("numberOfcoins: " + numberOfCoins);
//        System.out.println("numberOfMoves: " + numberOfMoves);
//        System.out.println("territoryScoreWhite: " + getTerritoryScore(WHITE, cBoard));
//        System.out.println("territoryScoreBlack: " + getTerritoryScore(BLACK, cBoard));
//        System.out.println("terrScore: " + territory);
//        System.out.println("turn: " + cBoard.getTurn());
//        System.out.println("totalscore: " + totalScore);

        return totalScore;
    }

    public int getCorners(int player) {
        int nrCorners = 0;
        for (int i = 0; i < boardGrid.length; i += boardGrid.length-1)
            for (int j = 0; j < boardGrid[i].length; j += boardGrid[i].length-1)
                if (boardGrid[i][j] == player)
                    nrCorners++;

        return nrCorners;
    }

    public int getCorners(Board cBoard, int player) {
        int nrCorners = 0;
        for (int i = 0; i < cBoard.getBoardGrid().length; i += cBoard.getBoardGrid().length-1)
            for (int j = 0; j < cBoard.getBoardGrid()[i].length; j += cBoard.getBoardGrid()[i].length-1)
                if (cBoard.getBoardGrid()[i][j] == player)
                    nrCorners++;

        return nrCorners;
    }

    public double[][] setTerritory(double bound) {
        Random rand = new Random();
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                cellValues[i][j] = rand.nextDouble()*bound;
            }
        }
        return cellValues;
    }

    public double[][] setTerritory(double[][] cellValues) {
        this.cellValues = cellValues;
        return  cellValues;
    }

    public double[][] setTerritory(){
        cellValues = new double[board.getSize()][board.getSize()];
        if(board.getSize() == 4){

            cellValues[0][0]  = 10;
            cellValues[3][3]  = 10;
            cellValues[3][0]  = 10;
            cellValues[0][3]  = 10;

            cellValues[0][1]  = 5;
            cellValues[0][2]  = 5;
            cellValues[1][0]  = 5;
            cellValues[2][0]  = 5;
            cellValues[3][1]  = 5;
            cellValues[3][2]  = 5;
            cellValues[2][3]  = 5;
            cellValues[1][3]  = 5;

            return cellValues;
        }

        if(board.getSize() == 6) {

            cellValues[0][0] = 10;
            cellValues[5][5] = 10;
            cellValues[0][5] = 10;
            cellValues[5][0] = 10;

            cellValues[0][1] = 5;
            cellValues[1][1] = 5;
            cellValues[1][0] = 5;
            cellValues[0][4] = 5;
            cellValues[1][4] = 5;
            cellValues[1][5] = 5;
            cellValues[4][0] = 5;
            cellValues[4][1] = 5;
            cellValues[5][1] = 5;
            cellValues[5][4] = 5;
            cellValues[4][4] = 5;
            cellValues[4][5] = 5;

            cellValues[1][2] = 6;
            cellValues[1][3] = 6;
            cellValues[2][1] = 6;
            cellValues[3][1] = 6;
            cellValues[4][2] = 6;
            cellValues[4][3] = 6;
            cellValues[2][4] = 6;
            cellValues[3][4] = 6;

            cellValues[2][0] = 8;
            cellValues[3][0] = 8;
            cellValues[0][2] = 8;
            cellValues[0][3] = 8;
            cellValues[2][5] = 8;
            cellValues[3][5] = 8;
            cellValues[5][2] = 8;
            cellValues[5][3] = 8;

            return cellValues;
        }

        if(board.getSize() == 8){

            cellValues[0][0] = 10;
            cellValues[7][7] = 10;
            cellValues[0][7] = 10;
            cellValues[7][0] = 10;

            cellValues[0][1] = 5;
            cellValues[1][1] = 5;
            cellValues[1][0] = 5;
            cellValues[0][4] = 5;
            cellValues[1][4] = 5;
            cellValues[1][5] = 5;
            cellValues[6][0] = 5;
            cellValues[6][1] = 5;
            cellValues[7][1] = 5;
            cellValues[7][6] = 5;
            cellValues[6][6] = 5;
            cellValues[6][7] = 5;

            cellValues[2][1] = 6;
            cellValues[3][1] = 6;
            cellValues[4][1] = 6;
            cellValues[5][1] = 6;
            cellValues[1][2] = 6;
            cellValues[1][3] = 6;
            cellValues[1][4] = 6;
            cellValues[1][5] = 6;
            cellValues[2][6] = 6;
            cellValues[3][6] = 6;
            cellValues[4][6] = 6;
            cellValues[5][6] = 6;
            cellValues[6][2] = 6;
            cellValues[6][3] = 6;
            cellValues[6][4] = 6;
            cellValues[6][5] = 6;

            cellValues[2][2] = 7;
            cellValues[2][3] = 7;
            cellValues[2][4] = 7;
            cellValues[2][5] = 7;
            cellValues[5][2] = 7;
            cellValues[5][3] = 7;
            cellValues[5][4] = 7;
            cellValues[5][5] = 7;
            cellValues[3][2] = 7;
            cellValues[4][2] = 7;
            cellValues[3][5] = 7;
            cellValues[4][5] = 7;

            cellValues[2][0] = 8;
            cellValues[3][0] = 8;
            cellValues[4][0] = 8;
            cellValues[5][0] = 8;
            cellValues[0][2] = 8;
            cellValues[0][3] = 8;
            cellValues[0][4] = 8;
            cellValues[0][5] = 8;
            cellValues[2][7] = 8;
            cellValues[3][7] = 8;
            cellValues[4][7] = 8;
            cellValues[5][7] = 8;
            cellValues[7][2] = 8;
            cellValues[7][3] = 8;
            cellValues[7][4] = 8;
            cellValues[7][5] = 8;
            return cellValues;
        }

        return null;
    }

    public int getTerritoryScore(int player){
        this.score = 0;

        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[i].length; j++) {
                if (boardGrid[i][j] == player) {
                    score += setTerritory()[i][j];
                }
            }
        }
        return score;
    }

    public int getTerritoryScore(int player, Board cBoard){
        this.score = 0;

        for (int i = 0; i < cBoard.getBoardGrid().length; i++) {
            for (int j = 0; j < cBoard.getBoardGrid()[i].length; j++) {
                if (cBoard.getBoardGrid()[i][j] == player) {
                    score += setTerritory()[i][j];
                }
            }
        }
        return score;
    }

    public double calcCoinWeight(int turn) {
        double coinWeight = coinWeightPoly0 + (coinWeightPoly1 * turn) + (coinWeightPoly2 * turn * turn) + (coinWeightPoly3 * turn * turn * turn);
        return coinWeight;
    }

    public double calcCornerWeight(int turn) {
        double cornerWeight = cornerWeightPoly0 + (cornerWeightPoly1 * turn) + (cornerWeightPoly2 * turn * turn) + (cornerWeightPoly3 * turn * turn * turn);
        return cornerWeight;
    }

    public double calcMoveWeight(int turn) {
        double moveWeight = moveWeightPoly0 + (moveWeightPoly1 * turn) + (moveWeightPoly2 * turn * turn) + (moveWeightPoly3 * turn * turn * turn);
        return moveWeight;
    }

    public double calcTerritoryWeight(int turn) {
        double territoryWeight = territoryWeightPoly0 + (territoryWeightPoly1 * turn) + (territoryWeightPoly2 * turn * turn) + (territoryWeightPoly3 * turn * turn * turn);
        return territoryWeight;
    }

}