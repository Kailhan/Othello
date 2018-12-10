package AI.Genetic_Algorithm;

import AI.EvaluationFunction;
import AI.GameTree;
import AI.MiniMaxAlph;
import AI.Node;
import AI.AI;
import AI.Stupid;
import Core.Board;

public class GA_MiniMaxAlph extends MiniMaxAlph {
    private GameTree gameTree;
    private Node<Board> root;
    private AI stupid;
    private Board board;

    public GA_MiniMaxAlph(int depth , Board board, EvaluationFunction evalFunc) {
        super(depth, board);
        this.board = board;
        this.gameTree = new GameTree(depth, board);
        this.evaluator = evalFunc;
        this.root = gameTree.createTree();
        this.stupid = new Stupid();
    }
}
