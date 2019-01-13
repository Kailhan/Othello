package AI;

import Core.*;

public class NegaScout extends AI {
    int depth;
    private GameTree gameTree;
    private EvaluationFunction evaluator;
    private Node<Board> root;
    private Board board;

//  public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
// return -1;
// }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

    public int[] getBestMove(Board board){
        this.board = board;
        this.gameTree = new GameTree(depth, board);
        this.evaluator.setBoard(board);
        this.root = gameTree.createTree();

        NegaSAlg(root, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer());
        int[] bestMove = new int[2];
        try {
            bestMove[0] = selectMove(root).getRow();
            bestMove[1] = selectMove(root).getColumn();
        }
        catch(NullPointerException e) {
            System.out.println("no more moves");
        }
        return bestMove;
    }

    public NegaScout(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public double NegaSAlg(Node<Board> currentNode, double alpha, double beta, int player) {
        if (currentNode.getChildren().size() == 0) {
            //System.out.println(player * evaluator.evaluate(currentNode.getData()));
            double value = player * evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else {
            double value = 0;
            for (Node<Board> child : currentNode.getChildren()) {
                if (child == currentNode.getChildren().get(0)) {
                    value = -1 * NegaSAlg(child, -1 * beta, -1 * alpha, -1 * player);
                    //currentNode.setValue(value);
                } else {
                    value = -1 * NegaSAlg(child, -1 * alpha - 1, -1 * alpha, -1 * player);
                    if (alpha < value && value < beta) {
                        value = -1 * NegaSAlg(child, -1 * beta, -1 * value, -1 * player);
                        //currentNode.setValue(value);
                    }
                    //currentNode.setValue(value);
                }
                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break;
                }
            }
            currentNode.setValue(alpha);
            return alpha;
        }
    }

    public Node<Board> selectMove(Node<Board> root) {
        for (Node<Board> currentChild : root.getChildren()) {
            if (-1 * currentChild.getValue() == root.getValue()) return currentChild;
        }
        return null;
    }

//    public double NegaSAlg(Node<Board> currentNode, int depth, double alpha, double beta, int player) {
//        if (depth == 0 || currentNode.getChildren().size() == 0) {
//            System.out.println(player * evaluator.evaluate(currentNode.getData()));
//            return player * evaluator.evaluate(currentNode.getData());
//        } else {
//            double value = Integer.MIN_VALUE;
//            double b = beta;
//            for (Node<Board> child : currentNode.getChildren()) {
//                value = -NegaSAlg(child, depth - 1, -b, -alpha, -player);
//                if (alpha < value && value < beta && child != currentNode.getChildren().get(0))
//                    value = -NegaSAlg(child, depth - 1, -beta, -alpha, -player);
//                alpha = Math.max(alpha, value);
//                if (beta <= alpha) break;
//                b = alpha + 1;
//            }
//            return alpha;
//        }
//    }

//    public double NegaSAlg(Node<Board> currentNode, int depth, double alpha, double beta, int player) {
//        if (depth == 0 || currentNode.getChildren().size() == 0) {
//            //System.out.println(player * evaluator.evaluate(currentNode.getData()));
//            return player * evaluator.evaluate(currentNode.getData());
//        } else {
//            double value;
//            for (Node<Board> child : currentNode.getChildren()) {
//                if(child != currentNode.getChildren().get(0)) {
//                    value = -NegaSAlg(child, depth - 1, -alpha-1, -alpha, -player);
//                    if (alpha < value && value < beta) {
//                        value = -NegaSAlg(child, depth - 1, -beta, -value, -player);
//                    }
//                } else {
//                    value = -NegaSAlg(child, depth - 1, -beta, -alpha, -player);
//                }
//                alpha = Math.max(alpha, value);
//                if(alpha >= beta){
//                    break;
//                }
//            }
//            return alpha;
//        }
//    }
}
