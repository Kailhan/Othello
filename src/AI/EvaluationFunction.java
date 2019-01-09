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

    public static final int WEIGHT_POLY_SIZE = 9;

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

    public EvaluationFunction(double[] chromosome, Board board) {
        this.cBoard = board;
        setChromosome(chromosome);
    }

    public EvaluationFunction(double[] chromosome) {
        this(chromosome, new Board((int)Math.sqrt(chromosome.length - WEIGHT_POLY_SIZE)));
    }

    public void setWeightPoly()
    {
        setWeightPoly(new double[] {0,1,0,100,-1,0,75,2.5,-0.05});
    }

    public void setWeightPoly(double[] weightPoly) {
        this.weightPoly = new double[WEIGHT_POLY_SIZE];
        System.arraycopy(weightPoly, 0, this.weightPoly, 0, weightPoly.length);
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

        for (int r = 0; r < board.getSize(); r++)
        {
            for (int c = 0; c < board.getSize(); c++)
            {
                if (Logic.checkSquareAllowed(r, c, board)) moveCounter++;
            }
        }

        Board[] possibleBoards = new Board[moveCounter];
        for (int r = 0; r < board.getSize(); r++)
        {
            for (int c = 0; c < board.getSize(); c++)
            {
                if (Logic.checkSquareAllowed(r, c, board))
                {
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
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1: gamesToBeSimmed;

        GenericTest.test(this, stupid, gamesToBeSimmed/2, boardSize);
        winsFirstMove = GenericTest.getPlayer1Wins();
        GenericTest.test(stupid, this, gamesToBeSimmed/2, boardSize);
        winsSecondMove = GenericTest.getPlayer2Wins();

        this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;
        return this.fitness;
    }

    public double evaluate(Board cBoard){
        this.cBoard = new Board(cBoard);
        int currentPlayer = cBoard.getCurrentPlayer();
        double totalScore;
        int blackMoves;
        int whiteMoves;
        double numberOfMoves;
        double numberOfCoins;
        double territory;

        numberOfCoins = ((double) (this.cBoard.getNrSquares(Board.BLACK) - this.cBoard.getNrSquares(Board.WHITE))) / ((double) (this.cBoard.getNrSquares(Board.BLACK) + this.cBoard.getNrSquares(Board.WHITE)));

        if(this.cBoard.getCurrentPlayer() == Board.WHITE){
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

        if (getTerritoryScore(Board.BLACK) + getTerritoryScore(Board.WHITE) !=0 ){
            territory = (double) (getTerritoryScore(Board.BLACK) - getTerritoryScore(Board.WHITE))/ (getTerritoryScore(Board.BLACK) + getTerritoryScore(Board.WHITE));
        } else territory = 0;

        totalScore = (calcCoinWeight(cBoard.getNrSquares(Board.EMPTY)) * numberOfCoins +
                calcMoveWeight(cBoard.getNrSquares(Board.EMPTY)) * numberOfMoves + calcTerritoryWeight(cBoard.getNrSquares(Board.EMPTY)) * territory);

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

        if(cBoard.getSize() == 4)
        {
            setTerritory(new double[][] {
                    {10,5,5,10},
                    {5,0,0,5},
                    {5,0,0,5},
                    {10,5,5,10}});
        }
        else if(cBoard.getSize() == 6)
        {
            setTerritory(new double[][] {
                    {10,5,8,8,5,10},
                    {5,5,6,6,5,5},
                    {8,6,0,0,6,8},
                    {8,6,0,0,6,8},
                    {5,5,6,6,5,5},
                    {10,5,8,8,5,10}});
        }

        else if(cBoard.getSize() == 8)
        {
            setTerritory(new double[][] {
                    {400,100,300,250,250,300,100,400},
                    {100,10,150,150,150,150,10,100},
                    {300,150,300,0,0,300,150,300},
                    {250,150,0,0,0,0,150,250},
                    {250,150,0,0,0,0,150,250},
                    {300,150,300,0,0,300,150,300},
                    {100,10,150,150,150,150,10,100},
                    {400,100,300,250,250,300,100,400}});
        }
        else {
            System.out.println("Invalid boardsize");
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
        double[] coinWeightPoly = new double[3];
        coinWeightPoly[0] = weightPoly[0];
        coinWeightPoly[1] = weightPoly[1];
        coinWeightPoly[2] = weightPoly[2];
        return coinWeightPoly;
    }

    public double[] moveWeightPoly() {
        double[] moveWeightPoly = new double[3];
        moveWeightPoly[0] = weightPoly[3];
        moveWeightPoly[1] = weightPoly[4];
        moveWeightPoly[2] = weightPoly[5];
        return moveWeightPoly;
    }

    public double[] territoryWeightPoly() {
        double[] territoryWeightPoly = new double[3];
        territoryWeightPoly[0] = weightPoly[6];
        territoryWeightPoly[1] = weightPoly[7];
        territoryWeightPoly[1] = weightPoly[8];
        return territoryWeightPoly;
    }

    public double calcCoinWeight(int emptySquares)
    {
        int pracTurn = cBoard.getSize() * cBoard.getSize() - emptySquares;
        double[] coinWeightPoly = coinWeightPoly();
        double coinWeight = coinWeightPoly[0] + (coinWeightPoly[1] * pracTurn) + (coinWeightPoly[2] * pracTurn * pracTurn);
        return coinWeight;
    }

    public double calcMoveWeight(int emptySquares)
    {
        int pracTurn = cBoard.getSize() * cBoard.getSize() - emptySquares;
        double[] moveWeightPoly = moveWeightPoly();
        double moveWeight = moveWeightPoly[0] + (moveWeightPoly[1] * pracTurn) + (moveWeightPoly[2] * pracTurn * pracTurn);
        return moveWeight;
    }

    public double calcTerritoryWeight(int emptySquares)
    {
        int pracTurn = cBoard.getSize() * cBoard.getSize() - emptySquares;
        double[] territoryWeightPoly = territoryWeightPoly();
        double territoryWeight = territoryWeightPoly[0] + (territoryWeightPoly[1] * pracTurn) + (territoryWeightPoly[2] * pracTurn * pracTurn);
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
        weightPoly = new double[WEIGHT_POLY_SIZE];
        for(int i = 0; i < weightPoly.length; i++)  {
            weightPoly[i] = chromosome[chromesomePosCounter];
            chromesomePosCounter++;
        }
        cellValues = new double[cBoard.getSize()][cBoard.getSize()];
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