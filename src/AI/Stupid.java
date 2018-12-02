package AI;
import AI.Tests.GenericTest;
import Core.Board;
import Core.Logic;

import java.util.Random;

public class Stupid extends AI {
    private int[][] board;
    private int[][] move;
    private Random rand = new Random();

    public Stupid(){}

    public double evaluateFitness(int gamesToBeSimmed, int boardSize){
        return -1;
    }

    public int[] getBestMove(Board board) {
        int[][] moves = Logic.getPossibleMoves(board);
        return moves[rand.nextInt(moves.length)];
    }

}