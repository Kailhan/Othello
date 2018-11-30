package AI;

import Core.Board;
import Core.Logic;

import java.util.Random;

public class EvaluationFunction {

    private Board cBoard;
    private int[][] boardGrid;
    private double[][] cellValues;
    private double[] weightPoly;

    private double[] chromosome;

    private final static int BLACK = 1;
    private final static int WHITE = -1;
    private int score;

    public EvaluationFunction(Board cBoard){
        this.cBoard = cBoard;
        this.boardGrid = cBoard.getBoardGrid();
        setWeightPoly();
        setTerritory();
    }

    public void setWeightPoly() {
        this.weightPoly = new double[16];
        this.weightPoly[0] = 100;  //coinWeightPoly0 = 100;
        this.weightPoly[1] = 0; //coinWeightPoly1 = 0;
        this.weightPoly[2] = 0; //coinWeightPoly2 = 0;
        this.weightPoly[3] = 0; //coinWeightPoly3 = 0;
        this.weightPoly[4] = 0; //cornerWeightPoly0 = 0;       //set to 0 to disable for a while
        this.weightPoly[5] = 0; //cornerWeightPoly1 = 0;
        this.weightPoly[6] = 0; //cornerWeightPoly2 = 0;
        this.weightPoly[7] = 0; //cornerWeightPoly3 = 0;
        this.weightPoly[8] = 100; //moveWeightPoly0 = 100;
        this.weightPoly[9] = 0; //moveWeightPoly1 = 0;
        this.weightPoly[10] = 0; //moveWeightPoly2 = 0;
        this.weightPoly[11] = 0; //moveWeightPoly3 = 0;
        this.weightPoly[12] = 100; //territoryWeightPoly0 = 100;
        this.weightPoly[13] = 0; //territoryWeightPoly1 = 0;
        this.weightPoly[14] = 0; //territoryWeightPoly2 = 0;
        this.weightPoly[15] = 0; //territoryWeightPoly3 = 0;
    }

    public void setWeightPoly(double[] weightPoly) {
        this.weightPoly = new double[16];
        for(int i = 0; i < weightPoly.length; i++) {
            this.weightPoly[i] = weightPoly[i];
        }
    }

    public int evaluate(Board cBoard){
        this.cBoard = cBoard;
        int totalScore;
        int blackMoves;
        int whiteMoves;
        double numberOfCorners;
        double numberOfMoves;
        double numberOfCoins;
        double territory;

        numberOfCoins = (double) (this.cBoard.getNrSquares(BLACK) - this.cBoard.getNrSquares(WHITE)) / (this.cBoard.getNrSquares(BLACK) + this.cBoard.getNrSquares(WHITE));

        if(this.cBoard.getCurrentPlayer() == WHITE){
            whiteMoves = Logic.numberSquaresAllowed(this.cBoard);
            this.cBoard.changePlayer();
            blackMoves = Logic.numberSquaresAllowed(this.cBoard);
        }
        else {
            blackMoves = Logic.numberSquaresAllowed(this.cBoard);
            this.cBoard.changePlayer();
            whiteMoves = Logic.numberSquaresAllowed(this.cBoard);
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

//        if (this.cBoard.getTurn() > 10 && this.cBoard.getTurn() < 20) { //even kijken wat de beste strategie is, ook rekening houden met de grote van de borden
//            this.coinWeight = 50;
//            this.cornerWeightPoly0 = 50;
//            this.moveWeightPoly0 = 50;
//            this.territoryWeightPoly0 = 50;
//        }
//
//        if (this.cBoard.getTurn() > 20 && this.cBoard.getTurn() < 32) { //even kijken wat de beste strategie is, ook rekening houden met de grote van de borden
//            this.coinWeight = 50;
//            this.cornerWeightPoly0 = 50;
//            this.moveWeightPoly0 = 50;
//            this.territoryWeightPoly0 = 50;
//        }

        totalScore = (int) (calcCoinWeight(cBoard.getTurn()) * numberOfCoins + calcCornerWeight(cBoard.getTurn()) * numberOfCorners +
                calcMoveWeight(cBoard.getTurn()) * numberOfMoves + calcTerritoryWeight(cBoard.getTurn()) * territory);

        System.out.println("numberOfcoins: " + numberOfCoins);
        System.out.println("numberOfMoves: " + numberOfMoves);
        System.out.println("territoryScoreWhite: " + getTerritoryScore(WHITE));
        System.out.println("territoryScoreBlack: " + getTerritoryScore(BLACK));
        System.out.println("terrScore: " + territory);
        System.out.println("totalscore: " + totalScore);

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

    public void setTerritory(double[][] cellValues) {
        this.cellValues = new double[cBoard.getSize()][cBoard.getSize()];
        for(int i = 0; i < cBoard.getSize(); i++) {
            for(int j = 0; j < cBoard.getSize(); j++) {
                this.cellValues[i][j] = cellValues[i][j];
            }
        }
    }

    public void setTerritory(){
        cellValues = new double[cBoard.getSize()][cBoard.getSize()];
        if(cBoard.getSize() == 4){

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
        }

        if(cBoard.getSize() == 6) {

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
        }

        if(cBoard.getSize() == 8){

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
        }
    }

    public int getTerritoryScore(int player){
        this.score = 0;

        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[i].length; j++) {
                if (boardGrid[i][j] == player) {
                    score += cellValues[i][j];
                }
            }
        }
        return score;
    }

    public double[] coinWeightPoly() {
        double[] coinWeightPoly = new double[4];
        coinWeightPoly[0] = weightPoly[0];
        coinWeightPoly[1] = weightPoly[1];
        coinWeightPoly[2] = weightPoly[2];
        coinWeightPoly[3] = weightPoly[3];
        return coinWeightPoly;
    }

    public double[] cornerWeightPoly() {
        double[] cornerWeightPoly = new double[4];
        cornerWeightPoly[0] = weightPoly[4];
        cornerWeightPoly[1] = weightPoly[5];
        cornerWeightPoly[2] = weightPoly[6];
        cornerWeightPoly[3] = weightPoly[7];
        return cornerWeightPoly;
    }

    public double[] moveWeightPoly() {
        double[] moveWeightPoly = new double[4];
        moveWeightPoly[0] = weightPoly[8];
        moveWeightPoly[1] = weightPoly[9];
        moveWeightPoly[2] = weightPoly[10];
        moveWeightPoly[3] = weightPoly[11];
        return moveWeightPoly;
    }

    public double[] territoryWeightPoly() {
        double[] territoryWeightPoly = new double[4];
        territoryWeightPoly[0] = weightPoly[12];
        territoryWeightPoly[1] = weightPoly[13];
        territoryWeightPoly[2] = weightPoly[14];
        territoryWeightPoly[3] = weightPoly[15];
        return territoryWeightPoly;
    }

    public double calcCoinWeight(int turn) {
        double coinWeight = coinWeightPoly()[0] + (coinWeightPoly()[1] * turn) + (coinWeightPoly()[2] * turn * turn) + (coinWeightPoly()[3] * turn * turn * turn);
        return coinWeight;
    }

    public double calcCornerWeight(int turn) {
        double cornerWeight = cornerWeightPoly()[0] + (cornerWeightPoly()[1] * turn) + (cornerWeightPoly()[2] * turn * turn) + (cornerWeightPoly()[3] * turn * turn * turn);
        return cornerWeight;
    }

    public double calcMoveWeight(int turn) {
        double moveWeight = moveWeightPoly()[0] + (moveWeightPoly()[1] * turn) + (moveWeightPoly()[2] * turn * turn) + (moveWeightPoly()[3] * turn * turn * turn);
        return moveWeight;
    }

    public double calcTerritoryWeight(int turn) {
        double territoryWeight = territoryWeightPoly()[0] + (territoryWeightPoly()[1] * turn) + (territoryWeightPoly()[2] * turn * turn) + (territoryWeightPoly()[3] * turn * turn * turn);
        return territoryWeight;
    }

    public double[][] getCellValues() {
        return cellValues;
    }

    public double[] getWeightPoly() {
        return weightPoly;
    }

    public double[] getChromosome() {
        this.chromosome = new double[weightPoly.length + (cellValues.length * cellValues[0].length)];
        int chromesomePosCounter = 0;
        for(int i = 0; i < weightPoly.length; i++)  {
            chromosome[chromesomePosCounter] = weightPoly[i];
            chromesomePosCounter++;
        }
        for(int i = 0; i < cellValues.length; i++) {
            for (int j = 0; j < cellValues[0].length; j++) {
                chromosome[chromesomePosCounter] = cellValues[i][j];
                chromesomePosCounter++;
            }
        }
        return chromosome;
    }

    public void printChromosome() {
        for(int i = 0; i < chromosome.length; i++){
            System.out.println("Gene " + i + " has value: " + chromosome[i]);
        }
    }
}