package AI;

import Core.Board;

public class NegaMax {
    int depth;
    private GameTree gameTree;
    private EvaluationFunction evaluator;
    private Node<Board> root;
    private Board board;

//  public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
// return -1;
// }

    public NegaMax(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public int[] getBestMove(Board board, int player){
        int maxScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        this.gameTree = new GameTree(depth, board);
        this.evaluator.setBoard(board);
        this.root = gameTree.createTree();

        for(Node<Board> child : root.getChildren()) {
            int score = NegaAlgo(child, this.depth, player);
            if (score > maxScore) {
                maxScore = score;
                bestMove[0] = child.getRow();
                bestMove[1] = child.getColumn();
            }
        }

        return bestMove;
    }

    public int NegaAlgo(Node<Board> currentNode, int depth, int player) {
        if (depth == 0 || currentNode.getChildren().size() == 0) {
            return (int) (player * evaluator.evaluate(currentNode.getData()));
        } else {
            int value = Integer.MIN_VALUE;
            for (Node<Board> child : currentNode.getChildren()) {
                value = Math.max(value, -NegaAlgo(child, depth - 1, -player));
            }
            return value;
        }
    }
}
