package AI;

import Core.Board;
import Core.GameScene;

public class MMAB_IterativeDeepening extends AI {

    private int moveTime;
    private long totalRunTime;

        public MMAB_IterativeDeepening(int moveTime){
            this.moveTime = moveTime;
        }

         public int[] getBestMove(Board startBoard){
            long startTime = System.currentTimeMillis();
            long endTime = startTime + moveTime;
            int depth = 1;
            int[] currentBestMove = null;
            int[] previousBestMove = null;
            Board board;
            while (System.currentTimeMillis() < endTime)
            {
                board = new Board(startBoard);
                MiniMaxAlph m = new MiniMaxAlph(depth, board);
                //Node<Board> PV_node = m.getBestMoveNode(board);
                //int[] move = m.getBestMoveFromNode(PV_node);
                int[] move = m.getBestMove(board);
                previousBestMove = currentBestMove;
                currentBestMove = move;
                depth++;
                long runtime = (System.currentTimeMillis() - startTime);
                totalRunTime = totalRunTime + runtime;
                //System.out.println("depth: " + depth + " runtime " + runtime);
            }
            //System.out.println("depth: " + depth);
             //System.out.println("runTime mmab: " + totalRunTime);
             if(previousBestMove == null) previousBestMove = currentBestMove;
            return previousBestMove;
        }


    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }


}
