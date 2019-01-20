package AI;

import AI.Tests.GenericTest;
import Core.Board;
import Core.Logic;

import java.util.Arrays;

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

    public EvaluationFunction(Board board, double[] weights){
        this.cBoard = board;
        setWeightPoly(weights);
        setTerritory();
    }


    public EvaluationFunction(double[] weightPoly) {
        this.weightPoly = weightPoly;
        setTerritory();
    }

    public EvaluationFunction(double[] chromosome, Board board) {
        this.cBoard = board;
        setTerritory();
        setChromosome(chromosome);
    }


    public void setWeightPoly()
    {
        setWeightPoly(new double[] {-6.60,10.42,6.25,-9.33,-14.13,6.15,-38.24,-19.03,42.315});
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
        Stupid evaluator = new Stupid();
        GenericTest.test(this,evaluator, gamesToBeSimmed/2, boardSize);
        winsFirstMove = GenericTest.getPlayer1Wins();
        GenericTest.test(evaluator, this, gamesToBeSimmed/2, boardSize);
        winsSecondMove = GenericTest.getPlayer2Wins();

        this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;
        return this.fitness;
    }

    public double evaluate(Board cBoard)
    {
        this.cBoard = new Board(cBoard);
        int size = cBoard.getSize();
        int filledSquares = size * size - cBoard.getNrSquares(Board.EMPTY);

        double totalScore;
        double numberOfCoins;
        double numberOfMoves;
        double territory;

        numberOfCoins = getNumberOfCoins(this.cBoard);
        numberOfMoves = getNumberOfMoves(this.cBoard);
        territory = getTerritory(this.cBoard);

        totalScore = calcCoinWeight(filledSquares) * numberOfCoins +
                calcMoveWeight(filledSquares) * numberOfMoves +
                calcTerritoryWeight(filledSquares) * territory;


        return totalScore;
    }

    public static double getNumberOfCoins(Board board)
    {
        double numberOfCoins;
        double blackCoins = board.getNrSquares(Board.BLACK);
        double whiteCoins = board.getNrSquares(Board.WHITE);

        numberOfCoins = ((blackCoins - whiteCoins)) / (blackCoins + whiteCoins);

        return numberOfCoins;
    }

    public static double getNumberOfMoves(Board board)
    {
        Board tempBoard = new Board(board);
        double numberOfMoves;
        double blackMoves;
        double whiteMoves;

        if(tempBoard.getCurrentPlayer() == Board.WHITE)
        {
            whiteMoves = Logic.numberSquaresAllowed(tempBoard);
            tempBoard.changePlayer();
            blackMoves = Logic.numberSquaresAllowed(tempBoard);
        }
        else
        {
            blackMoves = Logic.numberSquaresAllowed(tempBoard);
            tempBoard.changePlayer();
            whiteMoves = Logic.numberSquaresAllowed(tempBoard);
        }

        if(blackMoves + whiteMoves != 0)
            numberOfMoves = ((blackMoves - whiteMoves) / (blackMoves + whiteMoves));
        else numberOfMoves = 0;

        return numberOfMoves;
    }

    public double getTerritory(Board board)
    {
        double territory;
        double territoryScoreBlack = getTerritoryScore(board, Board.BLACK);
        double territoryScoreWhite = getTerritoryScore(board, Board.WHITE);

        if (territoryScoreBlack + territoryScoreWhite !=0 )
            territory = (territoryScoreBlack - territoryScoreWhite) / (territoryScoreBlack + territoryScoreWhite);
        else territory = 0;

        return territory;
    }


    public void setWeightPoly(double[] weightPoly)
    {
        this.weightPoly = new double[WEIGHT_POLY_SIZE];
        System.arraycopy(weightPoly, 0, this.weightPoly, 0, weightPoly.length);
    }

    public void normalizeWeightPoly(double newMaxWeight)
    {
        double maxWeight = 0;

        for (double aWeightPoly : weightPoly)
            if (Math.abs(aWeightPoly) > maxWeight)
                maxWeight = Math.abs(aWeightPoly);

        for (int i = 0; i < weightPoly.length; i++)
            weightPoly[i] = weightPoly[i] * newMaxWeight / maxWeight;
    }

    public void normalizeWeightPoly()
    {
        normalizeWeightPoly(1.0);
    }

    public void setTerritory(double[][] cellValues)
    {
        this.cellValues = new double[cBoard.getSize()][cBoard.getSize()];
        for(int i = 0; i < cBoard.getSize(); i++) {
            for(int j = 0; j < cBoard.getSize(); j++) {
                this.cellValues[i][j] = cellValues[i][j];
            }
        }
    }

    public void setTerritory()
    {
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
                    {1000,100,300,250,250,300,100,1000},
                    {100,10,150,150,150,150,10,100},
                    {300,150,300,0,0,300,150,300},
                    {250,150,0,0,0,0,150,250},
                    {250,150,0,0,0,0,150,250},
                    {300,150,300,0,0,300,150,300},
                    {100,10,150,150,150,150,10,100},
                    {1000,100,300,250,250,300,100,1000}});
        }
        else {
            System.out.println("Invalid boardsize");
        }
    }

    public int getTerritoryScore(Board board, int player)
    {
        int score = 0;
        int[][] boardGrid = board.getBoardGrid();

        for (int i = 0; i < boardGrid.length; i++)
            for (int j = 0; j < boardGrid.length; j++)
                if (boardGrid[i][j] == player)
                    score += cellValues[i][j];

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

    public double calcCoinWeight(int filledSquares)
    {
        double[] coinWeightPoly = coinWeightPoly();
        double coinWeight = coinWeightPoly[0] + (coinWeightPoly[1] * filledSquares) + (coinWeightPoly[2] * filledSquares * filledSquares);
        return coinWeight;
    }

    public double calcMoveWeight(int filledSquares)
    {
        double[] moveWeightPoly = moveWeightPoly();
        double moveWeight = moveWeightPoly[0] + (moveWeightPoly[1] * filledSquares) + (moveWeightPoly[2] * filledSquares * filledSquares);
        return moveWeight;
    }

    public double calcTerritoryWeight(int filledSquares)
    {
        double[] territoryWeightPoly = territoryWeightPoly();
        double territoryWeight = territoryWeightPoly[0] + (territoryWeightPoly[1] * filledSquares) + (territoryWeightPoly[2] * filledSquares * filledSquares);
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
    }

    public double[] getChromosome() {
        this.chromosome = new double[weightPoly.length];
        int chromesomePosCounter = 0;
        for(int i = 0; i < weightPoly.length; i++) {
            chromosome[chromesomePosCounter] = weightPoly[i];
            chromesomePosCounter++;
        }

        return chromosome;
    }

    public void printChromosome() {
        getChromosome();
        for(int i = 0; i < chromosome.length; i++){
            System.out.println("Gene " + i + " has value: " + chromosome[i]);
        }
    }

    public void printWeightPoly()
    {
        System.out.println(Arrays.toString(weightPoly));
    }

    public Board getBoard() {
        return cBoard;
    }

    public void setBoard(Board cBoard) {
        this.cBoard = cBoard;
    }
}