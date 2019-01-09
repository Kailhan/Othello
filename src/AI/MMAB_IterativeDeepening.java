package AI;

import Core.Board;
import Core.GameScene;

public class MMAB_IterativeDeepening extends AI {

    private int moveTime;
    private Board startBoard;

        public MMAB_IterativeDeepening(int moveTime, Board startBoard){
            this.moveTime = moveTime;
            this.startBoard = startBoard;
        }

        public int[] getBestMove(Board board){
            long startTime = System.currentTimeMillis();
            long endTime = startTime + moveTime;
            int depth = 1;
            int[] currentBestMove = null;
            //startBoard = new Board(board);
            while (System.currentTimeMillis() < endTime)
            {
                board = new Board(startBoard);
                int[] move = null;
                MiniMaxAlph m = new MiniMaxAlph(depth, board);
                for(int i = 0; i < depth; i++) {
                    move = m.getBestMove(board);
                    board.applyMove(move[0], move[1]);
                }
                currentBestMove = move;
                depth++;
            }
            return currentBestMove;
        }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

}
