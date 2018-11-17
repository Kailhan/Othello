package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.HashSet;

public class MiniMaxAlph {

    EvaluationFunction evaluator;
    Board board;
    Node<Board> bestNode;
    int maxValue;
    int finalScore;
    int[] indexes;
    int currentPlayer;
    int maximum;
    int minimum;
    ArrayList<Node<Board>> move = new ArrayList<Node<Board>>();

    public MiniMaxAlph(EvaluationFunction evaluator, Board board){
        this.evaluator = evaluator;
        this.board = board;
        this.bestNode = new Node<Board>(board);
    }


    public int search(Node<Board> currentNode, int alpha, int beta, int player) {

        if (currentNode.getChildren().size() == 0) {
            System.out.println("finnished");
            return evaluator.evaluate(currentNode.getData());
        }

        if (player == -1) { //maximizing white
            System.out.println("max");
            int value = Integer.MIN_VALUE;
            for (Node<Board> child : currentNode.getChildren()) {
                value = Math.max(value, search(child, alpha, beta, player * -1));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break;
                }
                return value;
            }

        } else { //minimizing black
            System.out.println("min");
            int value = Integer.MAX_VALUE;
            for (Node<Board> child : currentNode.getChildren()) {
                value = Math.min(value, search(child, alpha, beta, player *-1));
                beta = Math.min(beta, value);
                if (alpha >= beta) {
                    break;
                }
                return value;
            }
        }
        return 0;
    }


    public Node<Board> getMaxBoard(){
        while(this.bestNode.getParent() != null && this.bestNode.getParent().getDepth() > 1){
            this.bestNode = bestNode.getParent();
        }
        return bestNode;
    }

    public Node<Board> getBestNode(){return bestNode;}

}
