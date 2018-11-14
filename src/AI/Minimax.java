package AI;
import java.util.*;

import Core.*;

public class Minimax{

    EvaluationFunction evaluator;
    Node<Board> bestNode;


    public Minimax(EvaluationFunction evaluator){
        this.evaluator = evaluator;
    }

    public int minimaxAlg(Node<Board> currentNode, int depth){
        if(currentNode.getChildren() == null || depth == 0){
            int value = evaluator.evaluate(currentNode.getData());
            bestNode = currentNode;
            getMaxBoard(depth);
            return value;
        }
        else if (currentNode.getData().getCurrentPlayer() == -1) { //MAXVALUE, AI is player white: -1
            int value = Integer.MIN_VALUE;
            for(Node<Board> currentChild: currentNode.getChildren()){
                value = Math.max(value, minimaxAlg(currentChild, depth-1));

            }
            return value;
        }
        else { //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, minimaxAlg(currentChild, depth - 1));
            }
            return value;

        }
    }

    public Node<Board> getMaxBoard(int depth){
        while(bestNode.getParent().getDepth() != depth - 1){
            bestNode = bestNode.getParent();
        }
        return bestNode;
    }

    public Node<Board> getBestNode(){return bestNode;}
}

/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
 */