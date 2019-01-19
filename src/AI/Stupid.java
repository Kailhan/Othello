package AI;
import Core.Board;
import Core.Logic;

import java.util.Random;

public class Stupid extends AI {
    private Random rand = new Random();

    public Stupid(){}

    public double evaluateFitness(int gamesToBeSimmed, int boardSize){
        return -1;
    }

    /**
     * Return uniformly random legal move based on board
     * @param board current board
     * @return legal move
     */
    public int[] getBestMove(Board board) {
        int[][] moves = Logic.getPossibleMoves(board);
        try {
            return moves[rand.nextInt(moves.length)];
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument" + e.getMessage());
        }
        return null;
    }
}