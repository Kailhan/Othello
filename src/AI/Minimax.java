package AI;

import Core.*;

public class Minimax extends AI {


    int depth;
    private GameTree gameTree;
    private EvaluationFunction evaluator;
    private Node<Board> root;

    private Board board;
    //Node<Board> bestNode;
    //int maxValue;
    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

    public int[] getBestMove(Board board) {
        this.board = board;
        this.gameTree = new GameTree(depth, board);
        this.evaluator.setBoard(board);
        this.root = gameTree.createTree();
       // minimaxAlg2(root, board.getCurrentPlayer());
        minimaxAlg2(root);
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

    public int minimaxAlg2(Node<Board> currentNode, int playerValue) {
        if (currentNode.getChildren().size() == 0) {
            int value = (int) evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else if (currentNode.getData().getCurrentPlayer() == playerValue) {
            int value = Integer.MIN_VALUE;
            playerValue = -1*playerValue;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.max(value, minimaxAlg2(currentChild, playerValue));
            }
            currentNode.setValue(value);
            return value;
        } else { //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            playerValue = -1*playerValue;
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

    /*
    public int minimaxAlg(Node<Board> currentNode, int startingPlayer){
        if(currentNode.getChildren().size() == 0){ //komt hier nooit
            int value = evaluator.evaluate(currentNode.getData());
            System.out.println("print evaluation value2: " + evaluator.evaluate(currentNode.getData()));
            //getMaxBoard();
            //if(value > maxValue){
            //    maxValue = value;
            //    if(currentNode.getData() != null) this.bestNode = currentNode;
           // }
            return value;
        }

        else if (currentNode.getData().getCurrentPlayer() == startingPlayer) { //MAXVALUE, AI is player white: -1
            int value = Integer.MIN_VALUE;
            for(Node<Board> currentChild: currentNode.getChildren()){
                value = Math.max(value, minimaxAlg(currentChild, -1 * startingPlayer));
                System.out.println("print evaluation value1: " + evaluator.evaluate(currentNode.getData()));
            }
            return value;
        }

        else { //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            //System.out.println("current player: " + currentNode.getData().getCurrentPlayer());
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, minimaxAlg(currentChild, -1 * startingPlayer));
                System.out.println("print evaluation value3: " + evaluator.evaluate(currentNode.getData()));
            }
            return value;
        }
    }



    public Node<Board> getMaxBoard(){
        while(bestNode.getParent().getDepth() != 1){
            bestNode = bestNode.getParent();
        }
        return bestNode;
    }



    public Node<Board> getBestNode(){return bestNode;}
    */


