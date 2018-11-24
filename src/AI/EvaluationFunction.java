package AI;

import Core.Board;
import Core.Logic;
import Core.Settings;

public class EvaluationFunction {

    private Board board;
    private int[][] boardGrid;
    private Settings settings;

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

    public EvaluationFunction(Board cBoard, int[][] boardGrid){
        this.board = cBoard;
        this.boardGrid = boardGrid;
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