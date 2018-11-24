package AI;

import AI.EvaluationFunctions.EvalFunc_FixedTerr;
import Core.*;

public class Minimax {


    int depth;
    private GameTree gameTree;
    private EvalFunc_FixedTerr evaluator;
    private Node<Board> root;

    //Board board;
    //Node<Board> bestNode;
    //int maxValue;

    public Minimax(int depth, Board board) {
        this.depth = depth;
        this.gameTree = new GameTree(depth, board);
        this.evaluator = new EvalFunc_FixedTerr();
        this.root = gameTree.createTree();
        //this.board = board;
        //this.bestNode = new Node<Board>(board);
    }

    public int minimaxAlg2(Node<Board> currentNode) {
        if (currentNode.getChildren().size() == 0) {
            int value = evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else if (currentNode.getData().getCurrentPlayer() == -1) {
            int value = Integer.MIN_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.max(value, minimaxAlg2(currentChild));
            }
            currentNode.setValue(value);
            return value;
        } else { //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, minimaxAlg2(currentChild));
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


