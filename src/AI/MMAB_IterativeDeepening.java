package AI;

import Core.Board;
import Core.GameScene;

public class MMAB_IterativeDeepening extends AI {

    private int moveTime;
    //private Board startBoard;

        public MMAB_IterativeDeepening(int moveTime){
            this.moveTime = moveTime;
            //this.startBoard = startBoard;
        }

        public int[] getBestMove(Board startBoard){
            long startTime = System.currentTimeMillis();
            long endTime = startTime + moveTime;
            int depth = 1;
            int[] currentBestMove = null;
            Board board;
            //startBoard = new Board(board);
            while (System.currentTimeMillis() < endTime)
            {
                board = new Board(startBoard);
                MiniMaxAlph m = new MiniMaxAlph(depth, board);
                int[] move = m.getBestMove(board);
                currentBestMove = move;
                depth++;
            }
            return currentBestMove;
        }


    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

}
