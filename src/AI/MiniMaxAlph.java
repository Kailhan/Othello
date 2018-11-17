package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.HashSet;

public class MiniMaxAlph {

    EvaluationFunction evaluator;
    private GameTree gameTree;
    private Node<Board> root;


    public MiniMaxAlph(int depth , Board board) {
        this.gameTree = new GameTree(depth, board);
        this.evaluator = new EvaluationFunction();
        this.root = gameTree.createTree();
    }

    public int search(Node<Board> currentNode, int alpha, int beta) {
        if (currentNode.getChildren().size() == 0) {
            int value = evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else if (currentNode.getData().getCurrentPlayer() == -1) {
            int value = Integer.MIN_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.max(value, search(currentChild, alpha, beta));
                alpha = Math.max(value, alpha);
                if (alpha >= beta) {
                    System.out.println("pruned");
                    break;
                }
            }
            currentNode.setValue(value);
            return value;
        } else { //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, search(currentChild, alpha, beta));
                beta = Math.min(value, beta);
                if (alpha >= beta) {
                    System.out.println("pruned");
                    break;
                }
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
//          never gets pruned... gives other values then the search so possibly wrong
//    public int search2(Node<Board> currentNode, int alpha, int beta) {
//        if (currentNode.getChildren().size() == 0) {
//            System.out.println("finished");
//            this.bestNode = currentNode;
//            System.out.println("Score :" + evaluator.evaluate(currentNode.getData()));
//            return evaluator.evaluate(currentNode.getData());
//        }
//
//        if (currentNode.getData().getCurrentPlayer() == -1) { //maximizing white
//            System.out.println("max");
//            int value = alpha;
//            for (Node<Board> child : currentNode.getChildren()) {
//                value = Math.max(value, search(child, alpha, beta));
//                alpha = Math.max(alpha, value);
//                if (alpha >= beta){
//                    break;}
//                    currentNode.setValue(value);
//                return value;
//            }
//
//        } else {    //minimizing black
//            System.out.println("min");
//            int value = Integer.MAX_VALUE;
//            for (Node<Board> child : currentNode.getChildren()) {
//                value = Math.min(value, search(child, alpha, beta));
//                beta = Math.min(beta, value);
//                if (alpha >= beta) {
//                    break;
//                }
//                currentNode.setValue(value);
//                return value;
//            }
//        }
//        return 0;
//    }
