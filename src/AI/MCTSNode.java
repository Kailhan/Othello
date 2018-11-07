package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.List;

public class MCTSNode extends Node {

    private int scoreTotal;
    private int simulations;
    private Board board;

    public MCTSNode(Board board) {
        super(board);
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public int getSimulations() {
        return simulations;
    }

    public void setSimulations(int simulations) {
        this.simulations = simulations;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public List<MCTSNode> getChildren() {
        List<MCTSNode> mctsNodeList = new ArrayList();
        for(Object child : this.getChildren()) {
            mctsNodeList.add((MCTSNode)child);
        }
        return mctsNodeList;
    }
}
