package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.HashSet;

public class MiniMaxAlph {

    EvaluationFunction evaluator;
    Board board;
    Node<Board> bestNode;
    int maxValue;

    public MiniMaxAlph(EvaluationFunction evaluator, Board board){
        this.evaluator = evaluator;
        this.board = board;
        this.bestNode = new Node<Board>(board);
    }

    public Node<Board> search(Board board, int depth, double myBest, double theirBest){
        ArrayList<Node<Board>> Moves;
        set<Node<Board> = new HashSet<Board>();



        return null;
    }



}
