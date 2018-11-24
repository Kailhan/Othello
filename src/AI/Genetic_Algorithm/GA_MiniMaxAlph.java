package AI.Genetic_Algorithm;

import AI.EvaluationFunctions.EvalFunc_FixedTerr;
import AI.GameTree;
import AI.MiniMaxAlph;
import AI.Node;
import Core.Board;

public class GA_MiniMaxAlph extends MiniMaxAlph {
    EvalFunc_FixedTerr evaluator;
    private GameTree gameTree;
    private Node<Board> root;

    public GA_MiniMaxAlph(int depth , Board board) {
        super(depth, board);
        this.gameTree = new GameTree(depth, board);
        this.evaluator = new EvalFunc_FixedTerr(board);
        this.root = gameTree.createTree();
    }
}
