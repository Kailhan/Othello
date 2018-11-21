package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MIN_VALUE;

public class MCTSNode extends Node {

    public static final double explorationParameter = 1.414;
    private int scoreTotal;
    private int simulations = 1; //otherwise divide by zero when calculating node score might need to change the order/def of method
    private Board board;

    public MCTSNode(Board board, int x, int y) {
        super(board);
        this.board = board;
    }

    public MCTSNode(Board board) {
        this(board, MIN_VALUE, MIN_VALUE);
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

    public List<MCTSNode> getChildren(Node root) {
        List<MCTSNode> mctsNodeList = new ArrayList();
        for(Object child : root.getChildren()) {
            mctsNodeList.add((MCTSNode)child);
        }
        return mctsNodeList;
    }

    public double getNodeScore() {
        MCTSNode node = this;
        while(node.getParent() != null) {
            node = (MCTSNode)node.getParent();
        }
        int totalSims = node.getSimulations();
        //double score = scoreTotal/simulations + (explorationParameter * Math.sqrt(Math.log(totalSims)/simulations));
        double score = scoreTotal/simulations + (explorationParameter * 0.5 * ((totalSims)/simulations));
        return score;
    }
}
