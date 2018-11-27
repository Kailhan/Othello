package AI;
import Core.Board;
import Core.Logic;

import java.util.Random;

public class Stupid implements AI {
    private int[][] board;
    private int[][] move;
    private Random rand = new Random();

    public Stupid(){}

    public int[] getBestMove(Board board) {
        int[][] moves = Logic.getPossibleMoves(board);
        return moves[rand.nextInt(moves.length)];
    }

}