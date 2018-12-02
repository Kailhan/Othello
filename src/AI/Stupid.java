package AI;
import Core.Board;
import Core.Logic;

import java.util.Random;

public class Stupid extends AI {
    private int[][] board;
    private int[][] move;
    private Random rand = new Random();

    public Stupid(){}

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