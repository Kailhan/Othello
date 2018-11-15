package AI;
import java.util.*;

import Core.*;

public class Minimax{

    EvaluationFunction evaluator;
    Board board;
    Node<Board> bestNode;
    int maxValue;

    public Minimax(EvaluationFunction evaluator, Board board){
        this.evaluator = evaluator;
        this.board = board;
        this.bestNode = new Node<Board>(board);
    }

    public int minimaxAlg(Node<Board> currentNode, int startingPlayer){
        if(currentNode.getChildren() == null){ //komt hier nooit
            int value = evaluator.evaluate(currentNode.getData());
            System.out.println("print evaluation value2: " + evaluator.evaluate(currentNode.getData()));
            getMaxBoard();
            //if(value > maxValue){
                maxValue = value;
                if(currentNode.getData() != null) this.bestNode = currentNode;
           // }
            return value;
        }


        if (currentNode.getData().getCurrentPlayer() == startingPlayer) { //MAXVALUE, AI is player white: -1
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

    //

    public Node<Board> getMaxBoard(){
        while(bestNode.getParent().getDepth() != 1){
            bestNode = bestNode.getParent();
        }
        return bestNode;
    }

    public Node<Board> getBestNode(){return bestNode;}
}

/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
 */