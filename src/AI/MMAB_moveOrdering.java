package AI;

import Core.Board;

public class MMAB_moveOrdering extends AI{

    private int moveTime;
    private int[] currentBestMove;

    public MMAB_moveOrdering(int moveTime){
        this.moveTime = moveTime;
    }

        public int[] getBestMove (Board startBoard){
            long startTime = System.currentTimeMillis();
            long endTime = startTime + moveTime;
            int depth = 1;
            currentBestMove = null;
            int[] previousBestMove = null;
            Board board;
            while (System.currentTimeMillis() < endTime) {
                board = new Board(startBoard);
                MiniMaxAlph m = new MiniMaxAlph(depth, board);
                Node<Board> PV_node = m.getBestMoveNode(board);
                int[] move = m.getBestMoveFromNode(PV_node);
                previousBestMove = currentBestMove;
                currentBestMove = move;
                depth++;
            }
            //System.out.println("depth: " + depth);
            if(previousBestMove == null) previousBestMove = currentBestMove;
            return previousBestMove;
        }

        public double evaluateFitness ( int gamesToBeSimmed, int boardSize){
            return -1;
        }

    public int[] getCurrentBestMove() {
        return currentBestMove;
    }
}
