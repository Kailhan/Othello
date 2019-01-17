package AI;

import Core.Board;

public class MMAB_moveOrdering extends AI{

    private int moveTime;
    private int[] currentBestMove;

    public MMAB_moveOrdering(int moveTime){
        this.moveTime = moveTime;
    }

        public int[] getBestMove (Board startBoard){

            int depth = 1;
            currentBestMove = null;
            int[] previousBestMove = null;
            Board board = new Board(startBoard);
            Node<Board> PV_node = null;
            GameTree gameTree = new GameTree(1, board);
            MiniMaxAlph m = new MiniMaxAlph(1, board, gameTree);
            Node<Board> root = gameTree.createTree();
            long startTime = System.currentTimeMillis();
            long endTime = 0;
            while (endTime - startTime < moveTime) {
                //board = new Board(startBoard);
                m = new MiniMaxAlph(depth, board, m.getGameTree());
                if(depth > 1) m.PV_orderTreeLayer(PV_node);
                PV_node = m.selectPV_node(root, depth);
                m.MinimaxIterative(board, gameTree.getRoot());
                int[] move = m.getBestMoveFromNode(root);
                previousBestMove = currentBestMove;
                currentBestMove = move;
                depth++;
                endTime = System.currentTimeMillis();
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
