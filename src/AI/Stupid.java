package AI;
import Core.Board;
import Core.Logic;

public class Stupid implements AI {
    private int[][] board;
    private int[][] move;

    public Stupid(){}

    public int[] getBestMove(Board board)
    {
        int[][] moves = Logic.getPossibleMoves(board);
        int rand = (int)(Math.random() * moves.length);
        return moves[rand];
    }

}