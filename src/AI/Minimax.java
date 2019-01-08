package AI;

import Core.*;

public class Minimax extends AI {


    int depth;
    private GameTree gameTree;
    private EvaluationFunction evaluator;
    private Node<Board> root;

    private Board board;

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

    public int[] getBestMove(Board board) {
        this.board = board;
        this.gameTree = new GameTree(depth, board);
        this.evaluator.setBoard(board);
        this.root = gameTree.createTree();
        minimaxAlg2(root, board.getCurrentPlayer());
        //minimaxAlg2(root);
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

    public Minimax(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public double minimaxAlg2(Node<Board> currentNode, int playerValue) {
        if (currentNode.getChildren().size() == 0) {
            double value = playerValue * evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else if (currentNode.getData().getCurrentPlayer() == playerValue) {
            double value = Integer.MIN_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.max(value, minimaxAlg2(currentChild, playerValue));
            }
            currentNode.setValue(value);
            return value;
        } else { //MINVALUE, opponent player
            double value = Integer.MAX_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, minimaxAlg2(currentChild, playerValue));
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

    public Node<Board> getRoot() {
        return root;
    }

}