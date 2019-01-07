package AI.Tests;

import AI.MCTS;
import AI.MCTSNode;
import Core.Board;

public class MCTSSizeHeightTest {

    public static void main(String[] args) {
        MCTSNode node1 = new MCTSNode(new Board(), 1);
        MCTSNode node2 = new MCTSNode(new Board(), 1);
        MCTSNode node3 = new MCTSNode(new Board(), 1);
        MCTSNode node4 = new MCTSNode(new Board(), 1);
        MCTSNode node5 = new MCTSNode(new Board(), 1);
        MCTSNode node6 = new MCTSNode(new Board(), 1);
        MCTSNode node7 = new MCTSNode(new Board(), 1);
        MCTSNode node8 = new MCTSNode(new Board(), 1);
        MCTSNode node9 = new MCTSNode(new Board(), 1);
        MCTSNode node10 = new MCTSNode(new Board(), 1);
        MCTSNode node11 = new MCTSNode(new Board(), 1);
        MCTSNode node12 = new MCTSNode(new Board(), 1);
        node1.addChild(node2);
        node1.addChild(node3);
        node2.addChild(node4);
        node4.addChild(node6);
        node4.addChild(node7);
        node4.addChild(node8);
        node5.addChild(node9);
        node9.addChild(node10);
        node9.addChild(node11);
        node10.addChild(node12);
        System.out.println("treeHeight: " + node1.getHeight(node1));
        System.out.println("treeSize: " + node1.getTreeSize());
    }
}
