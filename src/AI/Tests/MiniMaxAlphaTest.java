package AI.Tests;

import AI.*;
import Core.Board;

public class MiniMaxAlphaTest {

    final static int DEPTH = 4;
    final static int GAMES = 100;
    final static int SIZE  = 8;


    public static void main(String[] args) {

        test1();
        test2();

    }

    //value test
    private static void test1() {

        Board board = new Board();
        MiniMaxAlph minimax = new MiniMaxAlph(DEPTH, board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        System.out.println("value: " + minimax.miniMaxAB(root,Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer()));
    }

    // generic test
    private static void test2(){

        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        Stupid s = new Stupid();
        Board board = new Board();
        MiniMaxAlph m = new MiniMaxAlph(DEPTH, board);

        GenericTest.test(s, m, GAMES, SIZE);
        int minimaxAB_Wins = GenericTest.getPlayer2Wins();
        int stupidWins = GenericTest.getPlayer1Wins();
        System.out.println(": " + minimaxAB_Wins);
        System.out.println("Stupid wins: " + stupidWins);

    }

}