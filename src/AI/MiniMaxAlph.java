package AI;

import AI.Tests.GenericTest;
import Core.Board;

public class MiniMaxAlph extends AI {

    //private EvaluationFunction evaluator;
    private GameTree gameTree;
    private Node<Board> root;
    private int depth;

    public MiniMaxAlph(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public MiniMaxAlph(int depth, EvaluationFunction eval) {
        this.depth = depth;
        this.evaluator = eval;
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        AI stupid = new Stupid();
        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1 : gamesToBeSimmed;
        GenericTest.test(this, stupid, gamesToBeSimmed / 2, boardSize);
        winsFirstMove = GenericTest.getPlayer1Wins();
        GenericTest.test(stupid, this, gamesToBeSimmed / 2, boardSize);
        winsSecondMove = GenericTest.getPlayer2Wins();
        //System.out.println(this.getEvaluator().getChromosome()[2]);
        //System.out.println("this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;");
        //System.out.println(gamesToBeSimmed);
        fitness = (winsFirstMove + winsSecondMove) / gamesToBeSimmed;

        return (winsFirstMove + winsSecondMove) / gamesToBeSimmed;
    }

    public int[] getBestMove(Board board) {
        MiniMaxAlph minimax = new MiniMaxAlph(this.depth, board);
        GameTree gameTree = new GameTree(this.depth, board);
        Node<Board> root = gameTree.createTree();

        minimax.miniMaxAB(root, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer());
        Node<Board> child1 = minimax.selectMove(root);

        int[] bestMove = new int[2];
        try {
            bestMove[0] = selectMove(root).getRow();
            bestMove[1] = selectMove(root).getColumn();
        }
        catch(NullPointerException e) {
            System.out.println("no more moves");
        }
        return bestMove;
    }

    public double miniMaxAB(Node<Board> currentNode, double alpha, double beta, int playerValue) {
        if (currentNode.getChildren().size() == 0) {
            double value = playerValue * evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;

        } else if (currentNode.getData().getCurrentPlayer() == playerValue) {
            double value = Integer.MIN_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.max(value, miniMaxAB(currentChild,alpha,beta, playerValue));
                alpha = Math.max(value,alpha);
                if(alpha >= beta){
                  //  System.out.println("pruned");
                    break;
                }
            }
            currentNode.setValue(value);
            return value;

        } else { //MINVALUE, opponent player
            double value = Integer.MAX_VALUE;
            for (Node<Board> currentChild : currentNode.getChildren()) {
                value = Math.min(value, miniMaxAB(currentChild,alpha,beta,playerValue));
                beta = Math.min(value,beta);
                if(alpha >= beta){
                  //  System.out.println("pruned");
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

    public Node<Board> getRoot() {
        return root;
    }

}