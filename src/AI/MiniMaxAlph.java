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


    public void search(Node<Board> currentNode,int player) {
        currentPlayer = player;

        if (currentNode.getChildren().size() == 0) {
            int value = evaluator.evaluate(currentNode.getData());
            getMaxBoard();
            maxValue = value;
            if (currentNode.getData() != null) this.bestNode = currentNode;


        } else {
            if (currentPlayer == 1) { //for max

                for (Node<Board> child : currentNode.getChildren()) {
                    this.move.add(child);
                }

                this.indexes = new int[currentNode.getChildren().size()];
                for (int i = 0; i < currentNode.getChildren().size(); i++) {
                    this.indexes[i] = evaluator.evaluate(move.get(i).getData());
                }

                this.maximum = Integer.MIN_VALUE;

                for (int i = 0; i < indexes.length; i++) {
                    if (indexes[i] > maximum) {
                        this.maximum = this.indexes[i];
                        this.bestNode = this.move.get(i);
                    }
                }


                currentPlayer = -1 * currentPlayer;
                this.move.clear();
                search(bestNode, currentPlayer); //check if the score has to be processed.
            }

            if (currentPlayer == -1) { //for min

                for (Node<Board> child : currentNode.getChildren()) {
                    this.move.add(child);
                }

                this.indexes = new int[currentNode.getChildren().size()];
                for (int i = 0; i < currentNode.getChildren().size(); i++) {
                    this.indexes[i] = evaluator.evaluate(this.move.get(i).getData());//store all the move values for the children
                }
            }

            this.minimum = Integer.MAX_VALUE;

            //Error hier maar geen idee waarom...
            for (int i = 0; i < indexes.length; i++) {
                // //System.out.println("all values min: " + indexes[i]);
                if (indexes[i] < minimum) {
                    this.minimum = this.indexes[i];
                    this.bestNode = this.move.get(i);
                }
            }


            currentPlayer = -1 * currentPlayer;
            //this.move.clear();
            //this.indexes = null;
            search(bestNode, currentPlayer); //check if the score has to be processed.


        }
    }

    public Node<Board> getMaxBoard(){
        while(this.bestNode.getParent() != null && this.bestNode.getParent().getDepth() > 1){
            this.bestNode = bestNode.getParent();
        }
        return bestNode;
    }

    public Node<Board> getBestNode(){return bestNode;}

}
