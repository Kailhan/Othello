package AI;

import Core.Board;
import Core.Logic;

public class PlayerModel
{
    private final int STRATEGY_AMOUNT = 3; //amount of strategies an evaluation function has
    private final int WEIGHT_POLY_SIZE = EvaluationFunction.WEIGHT_POLY_SIZE; //amount of weights in the model
    private final double LEARNING_RATE; //alpha for gradient descent

    private EvaluationFunction model; //the evaluation function in which the opponent is modeled
    private GameState[] history; //history of boards and opponent moves
    private int moveCount; //amount of moves the opponent has made

    public PlayerModel(Board board, double LEARNING_RATE)
    {
        int size = board.getSize();
        this.LEARNING_RATE = LEARNING_RATE;

        this.model = new EvaluationFunction(board);
        this.model.setWeightPoly(new double[] {1,0,0,1,0,0,0,0,0});

        this.history = new GameState[size*size];
        this.moveCount = 0;

    }

    public PlayerModel(Board board)
    {
        this(board, 0.01);
    }

    public class GameState //nested class of state of the game with regards to opponent move
    {
        private Board[] possibleBoards; //choices the opponent had
        private int chosenBoard; //choice the opponent made
        private double[][] strategyScores; //scores of each strategy for each possible board
        private int filledSquares; //way of keeping track of round progression

        public GameState(Board board, int[] move)
        {
            int size = board.getSize();
            filledSquares = size * size -  board.getNrSquares(Board.EMPTY) + 1;

            possibleBoards = Logic.getPossibleBoards(board);
            strategyScores = new double[possibleBoards.length][WEIGHT_POLY_SIZE];

            Board tempBoard = new Board(board);
            tempBoard.applyMove(move);

            for (int i = 0; i < possibleBoards.length; i++) //comparing all possible boards to see which index the opponent move has
                if (tempBoard.isSameBoard(possibleBoards[i]))
                {
                    chosenBoard = i;
                    break;
                }

            for (int i = 0; i < possibleBoards.length; i++)
            {
                strategyScores[i][0] = EvaluationFunction.getNumberOfCoins(possibleBoards[i]);
                strategyScores[i][1] = EvaluationFunction.getNumberOfMoves(possibleBoards[i]);
                strategyScores[i][2] = model.getTerritory(possibleBoards[i]);
                strategyScores[i][3] = strategyScores[i][0] * filledSquares;
                strategyScores[i][4] = strategyScores[i][1] * filledSquares;
                strategyScores[i][5] = strategyScores[i][2] * filledSquares;
                strategyScores[i][6] = strategyScores[i][3] * filledSquares;
                strategyScores[i][7] = strategyScores[i][4] * filledSquares;
                strategyScores[i][8] = strategyScores[i][5] * filledSquares;
            }
        }
    }

    public void addMove(Board board, int[] move)
    {
        history[moveCount] = new GameState(board, move);
        moveCount++;
    }

    public void addMove(Board board, int r, int c)
    {
        addMove(board, new int[] {r, c});
    }

    public void iterate(int count)
    {
        for (int c = 0; c < count; c++) //iterate a certain amount of times
        {
            double[] weightPoly = model.getWeightPoly();
            double[] newWeightPoly = new double[WEIGHT_POLY_SIZE];

            double gradientSum;
            double gradientAverage;

            for (int i = 0; i < WEIGHT_POLY_SIZE; i++)
            {
                gradientSum = 0;

                for (int j = 0; j < moveCount; j++)
                    gradientSum += history[j].strategyScores[history[j].chosenBoard][i];

                gradientAverage = gradientSum / (moveCount);
                newWeightPoly[i] = weightPoly[i] + LEARNING_RATE * gradientAverage * getAverageChosenEvaluation();
            }

            model.setWeightPoly(newWeightPoly);
            model.normalizeWeightPoly(100);
            System.out.println(getError());
        }
    }

    public void iterate()
    {
        while (getError() > 0)
            iterate(1);
    }

    public double getError()
    {
        int wrongMoves = 0;

        for (int i = 0; i < moveCount; i++) //for each moment the opponent made a move
        {
            Board predictedMove = new Board(history[i].possibleBoards[0]);

            double playerFlip = 1;
            if (history[i].possibleBoards[0].getCurrentPlayer() == Board.WHITE)
                playerFlip = -1;

            double score = playerFlip * evaluate(history[i],0);
            double tempScore;

            for (int j = 1; j < history[i].possibleBoards.length; j++) //for each of the possible moves the opponent could have made
            {
                tempScore = playerFlip * evaluate(history[i],j);
                if(tempScore > score)
                {
                    predictedMove = history[i].possibleBoards[j];
                    score = tempScore;
                }
            }

            if (!history[i].possibleBoards[history[i].chosenBoard].isSameBoard(predictedMove)) //check if the board the model predicted corresponds to the opponents move
                wrongMoves++;
        }

        return (double) (wrongMoves) / moveCount;
    }

    private double evaluate(GameState state, int index)
    {
        return model.calcCoinWeight(state.filledSquares) * state.strategyScores[index][0] +
                model.calcMoveWeight(state.filledSquares) * state.strategyScores[index][1] +
                model.calcTerritoryWeight(state.filledSquares) * state.strategyScores[index][2];
    }

    private double getAverageChosenEvaluation()
    {
        double evaluationSum = 0;

        for (int i = 0; i < moveCount; i++)
        {
            double playerFlip = 1;
            if (history[i].possibleBoards[history[i].chosenBoard].getCurrentPlayer() == Board.WHITE)
                playerFlip = -1;

            evaluationSum += playerFlip * evaluate(history[i], history[i].chosenBoard);
        }

        return evaluationSum / moveCount;
    }

    public EvaluationFunction getEvaluationFunction()
    {
        return model;
    }

    public int[] getMove(Board board)
    {
        return model.getBestMove(board);
    }
}
