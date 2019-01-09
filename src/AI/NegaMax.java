package AI;

import Core.Board;

public class NegaMax extends AI {
    int depth;
    private GameTree gameTree;
    private EvaluationFunction evaluator;
    private Node<Board> root;
    private Board board;

  public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
 return -1;
 }

    public NegaMax(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public int[] getBestMove(Board board){
        double maxScore = Double.MIN_VALUE;
        this.gameTree = new GameTree(depth, board);
        this.evaluator.setBoard(board);
        this.root = gameTree.createTree();

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

    public double NegaAlgo(Node<Board> currentNode, int depth, int player) {
        if (currentNode.getChildren().size() == 0) {
            double value = player * evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else {
            double value = Integer.MIN_VALUE;
            for (Node<Board> child : currentNode.getChildren()) {
                value = Math.max(value, -NegaAlgo(child, depth - 1, -player));
            }
            currentNode.setValue(value);
            return value;
        }
    }

    public Node<Board> selectMove(Node<Board> root) {
        for (Node<Board> currentChild : root.getChildren()) {
            if (currentChild.getValue() == root.getValue()) return currentChild;
        }
        return null;
    }

}
