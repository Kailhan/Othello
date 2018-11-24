package AI.EvaluationFunctions;

import AI.AI;
import Core.Board;
import Core.Logic;
import Core.Settings;

public abstract class EvaluationFunction {

    protected Board board;
    protected int[][] boardGrid;
    protected Settings settings;

    protected double coinWeightPoly0 = 100;
    protected double coinWeightPoly1 = 0;
    protected double coinWeightPoly2 = 0;
    protected double coinWeightPoly3 = 0;
    protected double cornerWeightPoly0 = 0;       //set to 0 to disable for a while
    protected double cornerWeightPoly1 = 0;
    protected double cornerWeightPoly2 = 0;
    protected double cornerWeightPoly3 = 0;
    protected double moveWeightPoly0 = 100;
    protected double moveWeightPoly1 = 0;
    protected double moveWeightPoly2 = 0;
    protected double moveWeightPoly3 = 0;
    protected double territoryWeightPoly0 = 100;
    protected double territoryWeightPoly1 = 0;
    protected double territoryWeightPoly2 = 0;
    protected double territoryWeightPoly3 = 0;

    protected final static int BLACK = 1;
    protected final static int WHITE = -1;
    protected int score;

    public EvaluationFunction(Board cBoard){
        this.board = cBoard;
        this.boardGrid = cBoard.getBoardGrid();
        this.settings = new Settings();
    }

    public EvaluationFunction(){
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.settings = new Settings();
    }

    public int evaluate(){
        int totalScore;
        int blackMoves;
        int whiteMoves;
        double numberOfCorners;
        double numberOfMoves;
        double numberOfCoins;
        double territory;

        numberOfCoins =  (double) (this.board.getNrSquares(BLACK) - this.board.getNrSquares(WHITE)) / (this.board.getNrSquares(BLACK) + this.board.getNrSquares(WHITE));

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

    public abstract int[][] setTerritory(int j);

    public int getTerritoryScore(int player){
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

    public int getTerritoryScore(int player, Board cBoard){
        this.score = 0;

        for (int i = 0; i < cBoard.getBoardGrid().length; i++) {
            for (int j = 0; j < cBoard.getBoardGrid()[i].length; j++) {
                if (cBoard.getBoardGrid()[i][j] == player) {
                    score += setTerritory(cBoard.getSize())[i][j];
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