package AI.Tests;

import AI.*;
import AI.EvaluationFunctions.EvalFunc_FixedTerr;
import Core.Board;

public class MiniMaxAlphaTest {

    final static int DEPTH = 7;

    public static void main(String[] args) {

        EvalFunc_FixedTerr evaluator = new EvalFunc_FixedTerr();
        Board board = new Board();
        MiniMaxAlph minimax = new MiniMaxAlph(DEPTH,board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        System.out.println(minimax.search(root,Integer.MIN_VALUE, Integer.MAX_VALUE));

        Node<Board> child1 = minimax.selectMove(root);
        child1.getData().displayBoardGrid();

        //System.out.println(minimax.search2(root,Integer.MIN_VALUE, Integer.MAX_VALUE));

    }
}
