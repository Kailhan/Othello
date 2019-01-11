package AI;

import Core.Board;
import Core.GameScene;

public class MMAB_IterativeDeepening extends AI {

    private int moveTime;

        public MMAB_IterativeDeepening(int moveTime){
            this.moveTime = moveTime;
        }

        public int[] getBestMove(Board startBoard){
            long startTime = System.currentTimeMillis();
            long endTime = startTime + moveTime;
            int depth = 1;
            int[] currentBestMove = null;
            Board board;
            while (System.currentTimeMillis() < endTime)
            {
                board = new Board(startBoard);
                MiniMaxAlph m = new MiniMaxAlph(depth, board);
                //Node<Board> PV_node = m.getBestMoveNode(board);
                //int[] move = m.getBestMoveFromNode(PV_node);
                int[] move = m.getBestMove(board);
                currentBestMove = move;
                depth++;
            }
            return currentBestMove;
        }

    public int[] getBestMoveWithOrdering(Board startBoard){
        long startTime = System.currentTimeMillis();
        long endTime = startTime + moveTime;
        int depth = 1;
        int[] currentBestMove = null;
        Board board;
        while (System.currentTimeMillis() < endTime)
        {
            board = new Board(startBoard);
            MiniMaxAlph m = new MiniMaxAlph(depth, board);
            Node<Board> PV_node = m.getBestMoveNode(board);
            int[] move = m.getBestMoveFromNode(PV_node);
            currentBestMove = move;
            depth++;
        }
        return currentBestMove;
    }


    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }


}
