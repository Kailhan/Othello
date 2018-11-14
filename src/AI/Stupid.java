package AI;
import Core.Board;
import Core.Logic;

public class Stupid implements AI {
    private int[][] board;
    private int[][] move;

    public Stupid(int[][] board)
    {
        this.board = board;
    }

    public static int[] getBestMove(Board board)
    {
        int[][] moves = Logic.getPossibleMoves(board);
        int rand = (int)(Math.random() * moves.length);
        return moves[rand];
    }
}