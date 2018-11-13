package AI;
import Core.Board;

public class Stupid implements AI {
    private int[][] board;
    private int[][] move;

    public Stupid(int[][] board){
        this.board = board;
    }

    public int[] getMoves(Board board)
    {
        return new int[2];
    }
}