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
        Minimax minimax = new Minimax(evaluator);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        minimax.minimaxAlg(root, 2);
        minimax.getBestNode().getData().displayBoardGrid();


    }


}
