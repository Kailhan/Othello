package AI.Tests;

import AI.*;
import Core.Board;

import javax.swing.*;

public class MinimaxTest {

    final static int DEPTH = 7;
    final static int GAMES = 800;
    final static int SIZE  = 8;
    static int gamesToBeSimmed = 1000;
    static int winsFirstMove;
    static int winsSecondMove;

    public static void main(String[] args) {
            //test1();
           // long startTime = System.currentTimeMillis();
        //for(int i=0; i<11; i++)
        test2();
            //long endTime = System.currentTimeMillis();
            //long runTime = endTime - startTime;
           // System.out.println("runtime:  " + runTime);
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

        MiniMaxAlph mini = new MiniMaxAlph(DEPTH,board);

        MMAB_moveOrdering mmab2 = new MMAB_moveOrdering(40);
        NS_moveOrdering ns_mo = new NS_moveOrdering(5);
        //MCTS_TreeReuse mcts = new MCTS_TreeReuse(moveTime, 1.414);
        MCTS mcts = new MCTS(40, MCTS.STANDARD_EXPLORATION_PARAMETER);

        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1: gamesToBeSimmed;
        GenericTest.test(mmab2,mcts, gamesToBeSimmed/2, 8);
        winsFirstMove = GenericTest.getPlayer1Wins();
        int draws = GenericTest.getDraws();
        GenericTest.test(mcts, mmab2, gamesToBeSimmed/2, 8);
        winsSecondMove = GenericTest.getPlayer2Wins();
        draws = draws + GenericTest.getDraws();


        System.out.println("Wins firstMove: " + winsFirstMove);
        System.out.println("Wins secondMove: " + winsSecondMove);
        System.out.println("Total draws: " + draws);
        System.out.println("Total wins: " + (winsSecondMove+winsFirstMove));
        System.out.println("---------------------------------------");
        JOptionPane.showMessageDialog(null, "test done");
    }

    private static void test3(){

        MMAB_IterativeDeepening mmab = new MMAB_IterativeDeepening(1000);

        mmab.getBestMove(new Board());
    }
}
