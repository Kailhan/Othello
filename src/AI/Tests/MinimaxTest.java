package AI.Tests;

import AI.*;
import Core.Board;

import java.util.ArrayList;
import java.util.List;

public class MinimaxTest {

    final static int DEPTH = 3;
    public static void main(String[] args) {

//   For testing if same results as MinimaxAB
//
//        Board board = new Board();
//
//        Minimax minimax = new Minimax(DEPTH, board);
//        GameTree gameTree = new GameTree(DEPTH, board);
//        Node<Board> root = gameTree.createTree();
//
//        System.out.println("value" + minimax.minimaxAlg(root, board.getCurrentPlayer()));
//        Node<Board> child1 = minimax.selectMove(root);

//        */
        /*

        Board board = new Board();
        Node<Board> root = new Node(board);
        Node<Board> node1 = new Node(board);
        //node1.getData().setCurrentPlayer(-1);
        Node<Board> node2 = new Node(board);
        //node2.getData().setCurrentPlayer(-1);
        Node<Board> node3 = new Node(board);
        //node3.getData().setCurrentPlayer(-1);
        Node<Board> node4 = new Node(board);
        node4.setValue(10);
        Node<Board> node5 = new Node(board);
        node5.setValue(-30);
        Node<Board> node6 = new Node(board);
        node6.setValue(-10);
        Node<Board> node7 = new Node(board);
        node7.setValue(20);
        Node<Board> node8 = new Node(board);
        node8.setValue(15);
        Node<Board> node9 = new Node(board);
        node9.setValue(-5);

        root.addChild(node1);
        root.addChild(node2);
        root.addChild(node3);

        node1.addChild(node4);
        node1.addChild(node5);

        node2.addChild(node6);
        node2.addChild(node7);

        node3.addChild(node8);
        node3.addChild(node9);

        */


        GameTree gameTree = new GameTree(4);
        Node<Board> root = gameTree.createTree();

        /*
        root.getChildren().get(0).getChildren().get(0).setValue(10);
        root.getChildren().get(0).getChildren().get(1).setValue(-30);
        root.getChildren().get(0).getChildren().get(2).setValue(5);

        root.getChildren().get(1).getChildren().get(0).setValue(-10);
        root.getChildren().get(1).getChildren().get(1).setValue(20);
        root.getChildren().get(1).getChildren().get(2).setValue(2);

        root.getChildren().get(2).getChildren().get(0).setValue(15);
        root.getChildren().get(2).getChildren().get(1).setValue(-5);
        root.getChildren().get(2).getChildren().get(2).setValue(-15);

        root.getChildren().get(3).getChildren().get(0).setValue(-2);
        root.getChildren().get(3).getChildren().get(1).setValue(-7);
        root.getChildren().get(3).getChildren().get(2).setValue(10);
        */

        //Minimax m = new Minimax(4, root.getData());
        //m.minimaxAlg2(root, 1);

        //System.out.println(root.getValue());
        //System.out.println(m.selectMove(root).getValue());


                int games = 1000;
                int totalSims1 = 1;
                int totalSims2 = 1;
                int size = 8;



                //GenericTest gt = new GenericTest();
                Stupid s = new Stupid();
                //gt.test(m,s, 5,8);
                Board board = new Board();
                Minimax m = new Minimax(4, board);
                MiniMaxAlph ma = new MiniMaxAlph(4, board);
                MCTS mcts = new MCTS(1000);
                MMAB_IterativeDeepening mai = new MMAB_IterativeDeepening(1000000, board);

                GenericTest.test(s, ma, games, size);
                int p1wins = GenericTest.getPlayer1Wins();
                int p2wins = GenericTest.getPlayer2Wins();
                System.out.println("p1 wins: "+ p1wins);
                System.out.println("p2 wins: "+ p2wins);


            }
        }
