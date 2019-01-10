package AI.Tests;

import AI.*;
import Core.Board;

public class MinimaxTest {

    final static int DEPTH = 3;
    final static int GAMES = 100;
    final static int SIZE  = 8;

    public static void main(String[] args) {
            //test1();
            test2();
        }

    //test if same value as minimaxAB
    private static void test1(){

        Board board = new Board();
        GameTree gameTree = new GameTree(DEPTH);
        Minimax minimax = new Minimax(DEPTH, board);
        Node<Board> root = gameTree.createTree();

        System.out.println("value" + minimax.minimaxAlg(root, board.getCurrentPlayer()));
    }

    //generic test
    private static void test2(){

        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        Stupid s = new Stupid();
        Board board = new Board();
        Minimax m = new Minimax(DEPTH, board);


        GenericTest.test(s, m, GAMES, SIZE);
        int minimaxWins = GenericTest.getPlayer2Wins();
        int stupidWins = GenericTest.getPlayer1Wins();
        System.out.println("Minimax wins: " + minimaxWins);
        System.out.println("Stupid wins: " + stupidWins);

    }
}
