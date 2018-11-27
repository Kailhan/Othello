package AI.Genetic_Algorithm;

import AI.EvaluationFunction;
import AI.GameTree;
import AI.MiniMaxAlph;
import AI.Node;
import Core.Board;

public class GA_MiniMaxAlph extends MiniMaxAlph {
    EvaluationFunction evaluator;
    private GameTree gameTree;
    private Node<Board> root;

    public GA_MiniMaxAlph(int depth , Board board, EvaluationFunction evalFunc) {
        super(depth, board);
        this.gameTree = new GameTree(depth, board);
        this.evaluator = evalFunc;
        this.root = gameTree.createTree();
    }
}
