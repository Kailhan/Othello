package AI.Tests;

import AI.EvaluationFunction;
import AI.GameTree;
import AI.Minimax;
import AI.Node;
import Core.Board;

public class MinimaxTest {

    final static int DEPTH = 3;

    public static void main(String[] args) {

        EvaluationFunction evaluator = new EvaluationFunction();
        Board board = new Board();
        Minimax minimax = new Minimax(evaluator, board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        minimax.minimaxAlg(root, -1);
        minimax.getBestNode().getData().displayBoardGrid();


    }


}