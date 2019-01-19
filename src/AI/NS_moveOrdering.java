package AI;

import Core.Board;

public class NS_moveOrdering extends  AI {

    private int moveTime;
    private int[] currentBestMove;
    private Node<Board> PV_node;

    public NS_moveOrdering(int moveTime){
        this.moveTime = moveTime;
    }


    public int[] getBestMove(Board startBoard){
        int depth = 1;
        currentBestMove = null;
        int[] previousBestMove = null;
        Board board = new Board(startBoard);
        GameTree gameTree = new GameTree(1, board);
        NegaScout ns = new NegaScout(1, board, gameTree);
        Node<Board> root = gameTree.createTree();
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        while (endTime - startTime < moveTime) {
            ns = new NegaScout(depth, board, ns.getGameTree());
            PV_node = ns.selectPV_node(root, depth);
            ns.PV_orderTreeLayer(PV_node);
            ns.MinimaxIterative(board, gameTree.getRoot());
            int[] move = ns.getBestMoveFromNode(root);
            previousBestMove = currentBestMove;
            currentBestMove = move;
            depth++;
            endTime = System.currentTimeMillis();
        }
        //System.out.println("depth: " + depth);
        if(previousBestMove == null) previousBestMove = currentBestMove;
        return previousBestMove;
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }


}
