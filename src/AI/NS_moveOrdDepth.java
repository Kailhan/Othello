package AI;

import Core.Board;

public class NS_moveOrdDepth extends  AI {

    private int depth;
    private int[] currentBestMove;
    private Node<Board> PV_node;

    public NS_moveOrdDepth(int depth){
        this.depth = depth;
    }


    public int[] getBestMove(Board startBoard){
        int d = 1;
        currentBestMove = null;
        int[] previousBestMove = null;
        Board board = new Board(startBoard);
        GameTree gameTree = new GameTree(1, board);
        NegaScout ns = new NegaScout(1, board, gameTree);
        Node<Board> root = gameTree.createTree();
        while (d < depth) {
            ns = new NegaScout(d, board, ns.getGameTree());
            PV_node = ns.selectPV_node(root, d);
            ns.PV_orderTreeLayer(PV_node);
            ns.MinimaxIterative(board, gameTree.getRoot());
            int[] move = ns.getBestMoveFromNode(root);
            currentBestMove = move;
            d++;
        }
        //System.out.println("depth: " + depth);
        return currentBestMove;
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }


}
