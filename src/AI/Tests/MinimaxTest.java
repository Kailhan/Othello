package AI.Tests;

import AI.*;
import Core.Board;

public class MinimaxTest {

    final static int DEPTH = 3;
    final static int GAMES = 100;
    final static int SIZE  = 8;

    public static void main(String[] args) {
            //test1();
            long startTime = System.currentTimeMillis();
            test2();
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            System.out.println("runtime:  " + runTime);
            //test3();
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

        //GameTree gameTree = new GameTree(DEPTH);
        //Node<Board> root = gameTree.createTree();

        Stupid s = new Stupid();
        Board board = new Board();
        //Minimax m = new Minimax(DEPTH, board);
        long moveTime = 10;
        MMAB_IterativeDeepening mmab = new MMAB_IterativeDeepening(20);
        MMAB_moveOrdering mmab2 = new MMAB_moveOrdering(10);
        NS_moveOrdering ns_mo = new NS_moveOrdering(10);
        MCTS mcts = new MCTS(20, 1.414);

        GenericTest.test(mmab,mcts , GAMES, SIZE);
        int p1wins = GenericTest.getPlayer1Wins();
        int p2wins = GenericTest.getPlayer2Wins();
        System.out.println("p1_wins: " + p1wins);
        System.out.println("p2_wins: " + p2wins);

    }

    private static void test3(){

        MMAB_IterativeDeepening mmab = new MMAB_IterativeDeepening(1000);

        mmab.getBestMove(new Board());
    }
}
