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
        double maxScore = Double.MIN_VALUE;
        int[] bestMove = new int[2];
        this.gameTree = new GameTree(depth, board);
        this.evaluator.setBoard(board);
        this.root = gameTree.createTree();

        for(Node<Board> child : root.getChildren()) {
            double score = NegaSAlg(child, this.depth, -10000, 10000, board.getCurrentPlayer());
            //double score = NegaSAlg(child, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer());
            if (score > maxScore) {
                maxScore = score;
                bestMove[0] = child.getRow();
                bestMove[1] = child.getColumn();
            }
        }
        return bestMove;
    }

    public NegaScout(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public double NegaSAlg(Node<Board> currentNode, int depth, double alpha, double beta, int player) {
        if (depth == 0 || currentNode.getChildren().size() == 0) {
            //System.out.println(player * evaluator.evaluate(currentNode.getData()));
            return player * evaluator.evaluate(currentNode.getData());
        } else {
            double value = Integer.MIN_VALUE;
            for (Node<Board> child : currentNode.getChildren()) {
                if(child == currentNode.getChildren().get(0)) {
                    value = -NegaSAlg(child, depth - 1, -beta, -alpha, -player);
                } else {
                    value = -NegaSAlg(child, depth - 1, -alpha - 1, -alpha, -player);
                    if (alpha < value && value < beta) {
                        value = -NegaSAlg(child, depth - 1, -beta, -value, -player);
                    }
                }
                alpha = Math.max(alpha, value);
                if(alpha >= beta){
                    break;
                }
            }
            return alpha;
        }
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
//            System.out.println(player * evaluator.evaluate(currentNode.getData()));
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
