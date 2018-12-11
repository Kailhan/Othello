package AI;

import Core.Board;

public class MCTSNodeTest {

    public static void main(String[] args) {
        MCTSNode mctsNode = new MCTSNode(new Board(4));
        for(int i = 0; i < 10; i++) {
            mctsNode.selectAction();
        }
        MCTSNode[] possibleMoves = mctsNode.getChilds();
        for(int i = 0; i < possibleMoves.length; i++) {
            System.out.println(possibleMoves[i].getScoreTotal());
            possibleMoves[i].getBoard().displayBoardGrid();
        }
    }
}
