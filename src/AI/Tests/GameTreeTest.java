package AI.Tests;

import AI.GameTree;
import AI.Node;
import Core.Board;

public class GameTreeTest {

    public static void main(String[] args){
        GameTree T = new GameTree(2);
        Node<Board> root = T.createTree();
        T.displayChildNodes(root);
    }
}
