package AI;

import Core.Board;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MIN_VALUE;

public class MCTSNode extends Node {

    public static final double explorationParameter = 1.414;
    private int scoreTotal;
    private int simulations = 1; //otherwise divide by zero when calculating node score might need to change the order/def of method
    private Board board;
    private boolean checked;

    public MCTSNode(Board board, int x, int y) {
        super(board);
        this.checked = false;
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
        int currentScoreTotal = scoreTotal;
        int currentSimulations = simulations;
        System.out.println("node.simulations");
        //System.out.println(node.simulations);
        MCTSNode parentNode = (MCTSNode)this.getParent();
        if(parentNode == null) {
            System.out.println("parent node null in getnodescore");
            return currentScoreTotal/currentSimulations + (explorationParameter * Math.sqrt(Math.log(currentSimulations)/currentSimulations));
        } else {
            while (parentNode.getParent() != null) {
                parentNode = (MCTSNode)this.getParent();
            }
            int totalSims = parentNode.getSimulations();
            System.out.println(currentScoreTotal);
            System.out.println(currentSimulations);
            return currentScoreTotal/currentSimulations + (explorationParameter * Math.sqrt(Math.log(totalSims)/currentSimulations));
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
