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
        Node<Board> bestNode = new Node<Board>(board);
    }

    public int minimaxAlg(Node<Board> currentNode, int startingPlayer){
        if(currentNode.getChildren() == null){
            int value = evaluator.evaluate(currentNode.getData());
            getMaxBoard();
            //if(value > maxValue){
                maxValue = value;
                if(currentNode.getData() != null) bestNode = currentNode;
           // }
            return value;
        }
        else if (currentNode.getData().getCurrentPlayer() == startingPlayer) { //MAXVALUE, AI is player white: -1
            int value = Integer.MIN_VALUE;
            for(Node<Board> currentChild: currentNode.getChildren()){
                value = Math.max(value, minimaxAlg(currentChild, -1 * startingPlayer));
            }
            return value;
        }
        else { //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, minimaxAlg(currentChild, -1 * startingPlayer));
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