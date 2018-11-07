package AI;

import Core.Board;

public class GameTreeTest {

    public static void main(String[] args){

        GameTree T = new GameTree(6);
        Node<Board> root = T.createTree();
        T.displayChildNodes(root);
    }
}
