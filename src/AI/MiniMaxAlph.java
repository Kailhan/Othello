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


    public Node<Board> search(Node<Board> currentNode,int player/* int depth, double myBest, double theirBest*/){
        currentPlayer = player;

        if(currentNode.getChildren() == null){
            System.out.println("finished");
           // this.finalScore = evaluator.evaluate(currentNode.getData());
        }

        else {
            if (currentPlayer == 1) { //for max
                if(this.move != null){
                    this.move.clear();
                }

                for (Node<Board> child : currentNode.getChildren()) {
                    this.move.add(child);
                    this.indexes = new int[currentNode.getChildren().size()];
                    for (int i = 0; i < currentNode.getChildren().size(); i++) {
                        this.indexes[i] = evaluator.evaluate(child.getData());//store all the move values for the children
                    }
                }

                this.maximum = indexes[0];

                for (int i = 0; i < indexes.length; i++) {
                    if (this.indexes[i] > this.maximum)
                        this.maximum = this.indexes[i];
                        this.bestNode = this.move.get(i);
                }

                currentPlayer = -1 * currentPlayer;
                System.out.println("Maximal");

                search(bestNode, currentPlayer); //check if the score has to be processed.
            }

            if(currentPlayer == -1){ //for min
                if(this.move != null){
                    this.move.clear();
                }

                for (Node<Board> child : currentNode.getChildren()) {
                    this.move.add(child);
                    this.indexes = new int[currentNode.getChildren().size()];
                    for (int i = 0; i < currentNode.getChildren().size(); i++) {
                        this.indexes[i] = evaluator.evaluate(child.getData());//store all the move values for the children
                    }
                }

                this.minimum = indexes[0];

                for (int i = 0; i < indexes.length; i++) {
                    if (this.indexes[i] < this.minimum)
                        this.minimum = this.indexes[i];
                        this.bestNode = this.move.get(i);
                }

                currentPlayer = -1 * currentPlayer;
                System.out.println("Minimal");
                this.bestNode.getData().displayBoardGrid();
                search(bestNode, currentPlayer); //check if the score has to be processed.
            }
        }
        return null;
    }

    public Node<Board> getBestNode(){return bestNode;}

}
