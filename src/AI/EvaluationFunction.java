package AI;

import AI.Tests.GenericTest;
import Core.Board;
import Core.Logic;

public class EvaluationFunction extends AI{

    private Board cBoard;
    private double[][] cellValues;
    private double[] weightPoly;
    private AI stupid = new Stupid();

    private double[] chromosome;

    private final static int BLACK = 1;
    private final static int WHITE = -1;
    public static final int WEIGHT_POLY_SIZE = 16;

    public EvaluationFunction(Board board){
        this.cBoard = board;
        setWeightPoly();
        setTerritory();
    }

    public EvaluationFunction(double [][] cellValues, double[] weightPoly, Board board) {
        this.cBoard = board;
        this.cellValues = cellValues;
        this.weightPoly = weightPoly;
    }

    public void setWeightPoly() {
        this.weightPoly = new double[WEIGHT_POLY_SIZE];
        this.weightPoly[0] = 100;  //coinWeightPoly0 = 100;
        this.weightPoly[1] = 0; //coinWeightPoly1 = 0;
        this.weightPoly[2] = 0; //coinWeightPoly2 = 0;
        this.weightPoly[3] = 0; //coinWeightPoly3 = 0;
        this.weightPoly[4] = 100; //cornerWeightPoly0 = 0;       //set to 0 to disable for a while
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
        this.weightPoly = new double[WEIGHT_POLY_SIZE];
        for(int i = 0; i < weightPoly.length; i++) {
            this.weightPoly[i] = weightPoly[i];
        }
    }

    /**
     * One level AI that directly and only uses the evaluation function itself (used for GA and maybe future stuff)
     * @param board current board for which we want to find the best move
     * @return a best move
     */

    public int[] getBestMove(Board board) {
        int moveCounter = 0;
        int possibleBoardIndex = 0;
        int bestBoardIndex = -1;
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (Logic.checkSquareAllowed(r, c, board)) moveCounter++;
            }
        }
        Board[] possibleBoards = new Board[moveCounter];
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (Logic.checkSquareAllowed(r, c, board)) {
                    Board tmpBoard = new Board(board);
                    tmpBoard.applyMove(r, c);
                    possibleBoards[possibleBoardIndex] = new Board(tmpBoard);
                    possibleBoardIndex++;
                }
            }
        }
        double score = Integer.MIN_VALUE;
        double cScore;
        for(int i = 0; i < possibleBoards.length; i++) {
            cScore = evaluate(possibleBoards[i]);
            cScore *= cScore;
            if(cScore >= score) {
                score = cScore;
                bestBoardIndex = i;
            }
        }
        int[] move = new int[2];
        move[0] = possibleBoards[bestBoardIndex].getRow(board);
        move[1] = possibleBoards[bestBoardIndex].getColumn(board);
        return move;
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {

        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1:gamesToBeSimmed;

        GenericTest.test(this, stupid, gamesToBeSimmed/2, boardSize);
        winsFirstMove = GenericTest.getPlayer1Wins();
        GenericTest.test(stupid, this, gamesToBeSimmed/2, boardSize);
        winsSecondMove = GenericTest.getPlayer2Wins();

        //System.out.println(this.getEvaluator().getChromosome()[2]);
        //System.out.println("this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;");
        //System.out.println(gamesToBeSimmed);
        this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;
        return this.fitness;
    }


    public double evaluate(Board cBoard){
        this.cBoard = cBoard;
        int currentPlayer = cBoard.getCurrentPlayer();
        double totalScore;
        int blackMoves;
        int whiteMoves;
        double numberOfCorners;
        double numberOfMoves;
        double numberOfCoins;
        double territory;

        //numberOfCoins = (double) (this.cBoard.getNrSquares(BLACK) - this.cBoard.getNrSquares(WHITE)) / (this.cBoard.getNrSquares(BLACK) + this.cBoard.getNrSquares(WHITE));
        if(currentPlayer == WHITE){ numberOfCoins = this.cBoard.getNrSquares(WHITE);
        } else { numberOfCoins = this.cBoard.getNrSquares(BLACK); }

//        if(this.cBoard.getCurrentPlayer() == WHITE){
//            whiteMoves = Logic.numberSquaresAllowed(this.cBoard);
//            this.cBoard.changePlayer();
//            blackMoves = Logic.numberSquaresAllowed(this.cBoard);
//        }
//        else {
//            blackMoves = Logic.numberSquaresAllowed(this.cBoard);
//            this.cBoard.changePlayer();
//            whiteMoves = Logic.numberSquaresAllowed(this.cBoard);
//        }
//
//        if(blackMoves + whiteMoves != 0){
//            numberOfMoves =  (double) ((blackMoves - whiteMoves) / (blackMoves + whiteMoves));
//        } else numberOfMoves = 0;
        numberOfMoves = Logic.numberSquaresAllowed(cBoard);

//        if(getCorners(BLACK) + getCorners(WHITE) != 0) {
//            numberOfCorners = (double) ((getCorners(BLACK) - getCorners(WHITE)) / (getCorners(BLACK) + getCorners(WHITE)));
//        } else numberOfCorners = 0;
        if(currentPlayer == WHITE){ numberOfCorners = cBoard.getCorners(WHITE);
        } else { numberOfCorners = cBoard.getCorners(BLACK); }

//        if (getTerritoryScore(BLACK) + getTerritoryScore(WHITE) !=0 ){
//            territory = (double) (getTerritoryScore(BLACK) - getTerritoryScore(WHITE))/ (getTerritoryScore(BLACK) + getTerritoryScore(WHITE));
//        } else territory = 0;
        if(currentPlayer == WHITE){ territory = getTerritoryScore(WHITE);
        } else { territory = getTerritoryScore(BLACK); }
//
//        System.out.println("Nrcorners white: " + getCorners(WHITE));
//        System.out.println("Nrcorners BLACK: " + getCorners(BLACK));
//        System.out.println("nrCorners total: " + numberOfCorners);
//        System.out.println("nrCorners total: " + cBoard.getCurrentPlayer());

        //totalScore = weightPoly[0] * numberOfCoins +  weightPoly[4] * numberOfCorners + weightPoly[8] * numberOfMoves + weightPoly[12] * territory;

        totalScore = (calcCoinWeight(cBoard.getNrSquares(Board.EMPTY)) * numberOfCoins + calcCornerWeight(cBoard.getNrSquares(Board.EMPTY)) * numberOfCorners +
                calcMoveWeight(cBoard.getNrSquares(Board.EMPTY)) * numberOfMoves + calcTerritoryWeight(cBoard.getNrSquares(Board.EMPTY)) * territory);
        totalScore = (currentPlayer == BLACK) ? totalScore : -totalScore;
        return totalScore;
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
        int score = 0;
        for (int i = 0; i < cBoard.getBoardGrid().length; i++) {
            for (int j = 0; j < cBoard.getBoardGrid()[i].length; j++) {
                if (cBoard.getBoardGrid()[i][j] == player) {
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

    public double calcCoinWeight(int emptySquares) {
        //int pracTurn = (cBoard.getSize() * cBoard.getSize() - emptySquares);
        int pracTurn = emptySquares;
        double coinWeight = coinWeightPoly()[0] + (coinWeightPoly()[1] * pracTurn) + (coinWeightPoly()[2] * pracTurn * pracTurn) + (coinWeightPoly()[3] * pracTurn * pracTurn * pracTurn);
        return coinWeight;
    }

    public double calcCornerWeight(int emptySquares) {
        //int pracTurn = (cBoard.getSize() * cBoard.getSize() - emptySquares);
        int pracTurn = emptySquares;
        double cornerWeight = cornerWeightPoly()[0] + (cornerWeightPoly()[1] * pracTurn) + (cornerWeightPoly()[2] * pracTurn * pracTurn) + (cornerWeightPoly()[3] * pracTurn * pracTurn * pracTurn);
        return cornerWeight;
    }

    public double calcMoveWeight(int emptySquares) {
        //int pracTurn = (cBoard.getSize() * cBoard.getSize() - emptySquares);
        int pracTurn = emptySquares;
        double moveWeight = moveWeightPoly()[0] + (moveWeightPoly()[1] * pracTurn) + (moveWeightPoly()[2] * pracTurn * pracTurn) + (moveWeightPoly()[3] * pracTurn * pracTurn * pracTurn);
        return moveWeight;
    }

    public double calcTerritoryWeight(int emptySquares) {
        //int pracTurn = (cBoard.getSize() * cBoard.getSize() - emptySquares);
        int pracTurn = emptySquares;
        double territoryWeight = territoryWeightPoly()[0] + (territoryWeightPoly()[1] * pracTurn) + (territoryWeightPoly()[2] * pracTurn * pracTurn) + (territoryWeightPoly()[3] * pracTurn * pracTurn * pracTurn);
        return territoryWeight;
    }

    public double[][] getCellValues() {
        return cellValues;
    }

    public double[] getWeightPoly() {
        return weightPoly;
    }

    public void setChromosome(double[] chromosome) {
        int chromesomePosCounter = 0;
        for(int i = 0; i < weightPoly.length; i++)  {
            weightPoly[i] = chromosome[chromesomePosCounter];
            chromesomePosCounter++;
        }
        for(int i = 0; i < cellValues.length; i++) {
            for (int j = 0; j < cellValues[0].length; j++) {
                cellValues[i][j] = chromosome[chromesomePosCounter];
                chromesomePosCounter++;
            }
        }
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
        getChromosome();
        for(int i = 0; i < chromosome.length; i++){
            System.out.println("Gene " + i + " has value: " + chromosome[i]);
        }
    }

    public Board getBoard() {
        return cBoard;
    }

    public void setBoard(Board cBoard) {
        this.cBoard = cBoard;
    }
}