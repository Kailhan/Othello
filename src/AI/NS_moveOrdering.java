package AI;

import Core.Board;

public class NS_moveOrdering extends  AI {

    private int moveTime;

    public NS_moveOrdering(int moveTime){
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
            NegaScout ns = new NegaScout(depth, board);
//            Node<Board> PV_node = ns.getBestMoveNode(board);
//            int[] move = ns.getBestMoveFromNode(PV_node);
//            currentBestMove = move;
            depth++;
        }
        //System.out.println("depth: " + depth);
        return currentBestMove;
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }


}
