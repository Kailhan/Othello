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
    public static void main(String[] args) {

        /*


        Minimax minimax = new Minimax(DEPTH, board);
        GameTree gameTree = new GameTree(DEPTH, board);
        Node<Board> root = gameTree.createTree();

        System.out.println(minimax.minimaxAlg2(root));
        Node<Board> child1 = minimax.selectMove(root);
        */

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

        /*
        List<Node<Board>> children1 = new ArrayList<Node<Board>>();
        children1.add(node1);
        children1.add(node2);
        children1.add(node3);
        */

        root.addChild(node1);
        root.addChild(node2);
        root.addChild(node3);

        //root.addChildren(children1);

        //List<Node<Board>> children2 = new ArrayList<Node<Board>>();
        //children2.add(node4);
        //children2.add(node5);

        node1.addChild(node4);
        node1.addChild(node5);

        node2.addChild(node6);
        node2.addChild(node7);

        node3.addChild(node8);
        node3.addChild(node9);

        //List<Node<Board>> children3 = new ArrayList<Node<Board>>();
        //children3.add(node6);
        //children3.add(node7);

        //List<Node<Board>> children4 = new ArrayList<Node<Board>>();
        //children4.add(node8);
        //children4.add(node9);



        //node1.addChildren(children2);
        //node2.addChildren(children3);
        //node3.addChildren(children4);


        Minimax m = new Minimax(1, board);
        m.minimaxAlg2(root, board.getCurrentPlayer());

        System.out.println(root.getValue());

    }
}
