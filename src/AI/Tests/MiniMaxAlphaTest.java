package AI.Tests;

import AI.*;
import Core.Board;

import javax.swing.*;

public class MiniMaxAlphaTest {

    final static int DEPTH = 4;
    final static int GAMES = 1000;
    final static int SIZE  = 8;
    final static long MS1 = 5;
    final static int MS2 = 5;
    static int winsFirstMove;
    static int winsSecondMove;



    public static void main(String[] args) {

//        test1();
//        test2();
        test3(GAMES);
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

    private static void test3(int gamesToBeSimmed){

        MCTS mcts = new MCTS(MS1,MCTS.STANDARD_EXPLORATION_PARAMETER);
        MMAB_moveOrdering mMove = new MMAB_moveOrdering(MS2);


        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1: gamesToBeSimmed;
        GenericTest.test(mMove,mcts, gamesToBeSimmed/2, 8);
        winsFirstMove = GenericTest.getPlayer1Wins();
        int secondMovewins = GenericTest.getPlayer2Wins();
        int draws = GenericTest.getDraws();
        GenericTest.test(mcts, mMove, gamesToBeSimmed/2, 8);
        winsSecondMove = GenericTest.getPlayer2Wins();
        int firstMovewins = GenericTest.getPlayer1Wins();
        draws = draws + GenericTest.getDraws();


        System.out.println("Wins firstMove Mini: " + winsFirstMove);
        System.out.println("Wins secondMove Mini: " + winsSecondMove);
        System.out.println("Wins firstMove MCTS: " + firstMovewins);
        System.out.println("Wins secondMove MCTS: " + secondMovewins);

        System.out.println("Total draws: " + draws);
        System.out.println("Total wins mini: " + (winsSecondMove+winsFirstMove));
        System.out.println("Total wins MCTS: " + (firstMovewins+secondMovewins));
        System.out.println("---------------------------------------");
        JOptionPane.showMessageDialog(null, "test done");

    }

}