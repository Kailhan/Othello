package AI;
import java.util.*;

import Core.*;

public class Minimax{

    public int minimaxV(Node<Board> currentNode, int depth){
        if(currentNode.getChildren() == null || depth == 0){
            int value = currentNode.getData().getBoardEvaluation();
            return value;
        }
        else if (currentNode.getData().getCurrentPlayer() == -1) { //MAXVALUE, AI is player white: -1
            int value = Integer.MIN_VALUE;
            for(Node<Board> currentChild: currentNode.getChildren()){
                value = Math.max(value, minimax(currentChild, depth-1));
            }
            return value;
//            List<Node<Board>> children = currentNode.getChildren();
//            int value = children.get(0).getData().getBoardEvaluation();
//            for(int i = 1; i < children.size(); i++) {
//                if (children.get(i).getData().getBoardEvaluation() > score) {
//                    value = children.get(i).getData().getBoardEvaluation();
//                }
//            }
        }
        else{ //MINVALUE, opponent player
            int value = Integer.MAX_VALUE;
            for(Node<Board> currentChild: currentNode.getChildren()){
                value = Math.min(value, minimax(currentChild, depth-1));
            }
            return value;

//            List<Node<Board>> children = currentNode.getChildren();
//            int value = children.get(0).getData().getBoardEvaluation();
//            for(int i = 1; i < children.size(); i++) {
//                if (children.get(i).getData().getBoardEvaluation() < score) {
//                    value = children.get(i).getData().getBoardEvaluation();
//                }
//            }
        }
        return 0;
    }


//    public Board minimaxDecision(Board board, int depth){
//        int score = Integer.MIN_VALUE;
//
//        if(depth<=0){
//       //     score = evaluationFunction();
//        } else{
//            board.changePlayer();
//            int oppositePlayer = board.getCurrentPlayer();
//           // int result = -minimaxDecision(board, depth-1);
//            if(result > score){
//                score = result;
//            }
//        }
//        return score;
//    }
//
////    public int minimaxValue(Node Parent, int depth){
////        if(depth<=0){
////            return evaluationFunction();
////        }
////        else if()
////        List listChildren = parent.getChildren();
////        int maxValue = (int)listChildren.get(0);
////        for(int i = 0; i<listChildren.size(); i++){
////            if((int)listChildren.get(i)>maxValue) maxValue=(int)listChildren.get(i);
////        }
////        return maxValue;
////
////    }
//
//
//
//
//    public int valueMax(Node parent){
//        List listChildren = parent.getChildren();
//        int maxValue = (int)listChildren.get(0);
//        for(int i = 0; i<listChildren.size(); i++){
//            if((int)listChildren.get(i)>maxValue) maxValue=(int)listChildren.get(i);
//        }
//        return maxValue;
//    }
//
//    public int valueMin(Node parent){
//        List listChildren = parent.getChildren();
//        int minValue = (int)listChildren.get(0);
//        for(int i = 0; i<listChildren.size(); i++){
//            if((int)listChildren.get(i)<minValue) minValue=(int)listChildren.get(i);
//        }
//        return minValue;
//    }

}

/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
 */