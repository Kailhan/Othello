package AI.Tests;

import AI.EvaluationFunction;
import AI.GameTree;
import AI.Minimax;
import AI.Node;
import Core.Board;

import java.util.ArrayList;
import java.util.List;

public class MinimaxTest {

    final static int DEPTH = 3;
    final static int GAMES = 100;
    final static int SIZE  = 8;

    public static void main(String[] args) {
          //test1();
          test2();
    }

    //For testing if same results as MinimaxAB
    private static void test1(){

        Board board = new Board();
        Minimax minimax = new Minimax(DEPTH, board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        System.out.println("value" + minimax.minimaxAlg(root, board.getCurrentPlayer()));
    }

    //generic test
    private static void test2(){

        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        AI.Stupid s = new AI.Stupid();
        Board board = new Board();
        Minimax m = new Minimax(DEPTH, board);


        GenericTest.test(s, m, GAMES, SIZE);
        int minimaxWins = GenericTest.getPlayer2Wins();
        int stupidWins = GenericTest.getPlayer1Wins();
        System.out.println("Minimax wins: " + minimaxWins);
        System.out.println("Stupid wins: " + stupidWins);

    }
}
