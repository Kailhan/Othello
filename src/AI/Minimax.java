package AI;
import java.util.*;

import Core.*;

public class Minimax{

    private Logic logic;
    private Node parent;
    private int moveIndex;
    private int depth;

    public Minimax(int depth){
        this.logic = new Logic();
        this.parent = new Node(new int[2]);
        this.depth = depth;
    }

    public Board minimaxDecision(Node<Board> root){


    }

    public int minimaxValue(Node<Board> currentNode){
        Board board = currentNode.getData();
        if(currentNode.getChildren() == null){
            int score = evaluationFunction();
        } else if (currentNode.getData().getCurrentPlayer() == -1) { //AI is player white: -1
            List<Node<Board>> children = currentNode.getChildren();
            int score = children.get(0).evaluationFunction();
            if(child > score){
                score = child;
            }
        }
        return score;
    }


    public Board minimaxDecision(Board board, int depth){
        int score = Integer.MIN_VALUE;

        if(depth<=0){
       //     score = evaluationFunction();
        } else{
            board.changePlayer();
            int oppositePlayer = board.getCurrentPlayer();
           // int result = -minimaxDecision(board, depth-1);
            if(result > score){
                score = result;
            }
        }
        return score;
    }

    public int minimaxValue(Node Parent, int depth){
        if(depth<=0){
            return evaluationFunction();
        }
        else if()
        List listChildren = parent.getChildren();
        int maxValue = (int)listChildren.get(0);
        for(int i = 0; i<listChildren.size(); i++){
            if((int)listChildren.get(i)>maxValue) maxValue=(int)listChildren.get(i);
        }
        return maxValue;

    }




    public int valueMax(Node parent){
        List listChildren = parent.getChildren();
        int maxValue = (int)listChildren.get(0);
        for(int i = 0; i<listChildren.size(); i++){
            if((int)listChildren.get(i)>maxValue) maxValue=(int)listChildren.get(i);
        }
        return maxValue;
    }

    public int valueMin(Node parent){
        List listChildren = parent.getChildren();
        int minValue = (int)listChildren.get(0);
        for(int i = 0; i<listChildren.size(); i++){
            if((int)listChildren.get(i)<minValue) minValue=(int)listChildren.get(i);
        }
        return minValue;
    }

}

/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
 */