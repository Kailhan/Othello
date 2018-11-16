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


    public int search(Node<Board> currentNode,int player) {
        currentPlayer = player;

        if (currentNode.getChildren().size() == 0) {
            System.out.println("finished");
            int value = evaluator.evaluate(currentNode.getData());
            System.out.println("print evaluation value " + evaluator.evaluate(currentNode.getData()));
            getMaxBoard();
            //if(value > maxValue){
            maxValue = value;
            if(currentNode.getData() != null) this.bestNode = currentNode;
            // }
            return value;

        } else {
            if (currentPlayer == 1) { //for max

                for (Node<Board> child : currentNode.getChildren()) {
                    this.move.add(child);
                }
                System.out.println("childrenSize" + this.move.size());

                this.indexes = new int[currentNode.getChildren().size()];
                for (int i = 0; i < currentNode.getChildren().size(); i++) {
                    this.indexes[i] = evaluator.evaluate(move.get(i).getData());
                }


                this.maximum = Integer.MIN_VALUE;
                for (int i = 0; i < indexes.length; i++) {
                    if (this.indexes[i] > this.maximum)
                        this.maximum = this.indexes[i];
                    this.bestNode = this.move.get(i);
                    // System.out.println("FINAL max score" + this.move.get(i).getData()); //raar
                }

                currentPlayer = -1 * currentPlayer;
                System.out.println("Maximal");
                System.out.println("best score max: " + evaluator.evaluate(bestNode.getData()));
                bestNode.getData().displayBoardGrid();
                this.move.clear();
                this.indexes = null;
                search(bestNode, currentPlayer); //check if the score has to be processed.
            }

            if (currentPlayer == -1) { //for min
                for (Node<Board> child : currentNode.getChildren()) {
                    this.move.add(child);
                }

                System.out.println("childrenSize" + this.move.size());

                this.indexes = new int[currentNode.getChildren().size()];
                for (int i = 0; i < currentNode.getChildren().size(); i++) {
                    this.indexes[i] = evaluator.evaluate(this.move.get(i).getData());//store all the move values for the children
                }
            }
            System.out.println("indexSIZE" + indexes.length);

            this.minimum = Integer.MAX_VALUE;
            for (int i = 0; i < indexes.length; i++) {
                if (this.indexes[i] < this.minimum)
                    this.minimum = this.indexes[i];
                this.bestNode = this.move.get(i);
            }

            currentPlayer = -1 * currentPlayer;
            System.out.println("Minimal");
            this.bestNode.getData().displayBoardGrid();
            this.move.clear();
            this.indexes = null;
            System.out.println("best score min: " + evaluator.evaluate(bestNode.getData()));
            search(bestNode, currentPlayer); //check if the score has to be processed.



        }
        return 0;
    }

    public Node<Board> getMaxBoard(){
        System.out.println("depth" + this.bestNode.getParent().getDepth());
        while(this.bestNode.getParent() != null && this.bestNode.getParent().getDepth() > 2){
            this.bestNode = bestNode.getParent();
        }
        return bestNode;
    }

    public Node<Board> getBestNode(){return bestNode;}

}
