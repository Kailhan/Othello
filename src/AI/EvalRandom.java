package AI;

import Core.*;

import java.util.Random;

public class EvalRandom extends EvaluationFunction {

    public EvalRandom(Board board, double[] polyWeights){
        super(board,polyWeights);
    }

    @Override
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
        for (int i = 0; i < possibleBoards.length; i++) {
            Random rand = new Random();
            cScore = evaluate(possibleBoards[i]);
            cScore = cScore + (Math.random()*(cScore/5)-cScore/10);
           // cScore = evaluate(possibleBoards[i]);
            //System.out.println(cScore);
            cScore *= cScore;
            if (cScore >= score) {
                score = cScore;
                bestBoardIndex = i;
            }
        }


            int[] move = new int[2];
            move[0] = possibleBoards[bestBoardIndex].getRow(board);
            move[1] = possibleBoards[bestBoardIndex].getColumn(board);
            return move;
        }

    public EvalRandom(double[] weightPoly){
        super(weightPoly);
    }

    public EvalRandom(Board board){
        super(board);
    }


}
