package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MCTS extends AI {

    private int maxSims;
    private int simsCounter = 0;
    private Random rand = new Random();
    private double explorationMultiplier = 1;
    private double explorationParameter = 1.414 * explorationMultiplier;
    private MCTSNode rootNode;

    public MCTS(int maxSims) {
        this.maxSims = maxSims;
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

    public int[] getBestMove(Board board) {
        int[] move = new int[2];
        MCTSNode node = findMove(board);
        move[0] = node.getRow();
        move[1] = node.getColumn();
        return move;
    }

    public MCTSNode findMove(Board board) {
        MCTSNode.totalSims = 0;
        MCTSNode currentNode = new MCTSNode(new Board(board), explorationParameter);
        rootNode = currentNode;
        MCTSNode moveNode = null;
        boolean reachedThreshold = false;
        int currentAmountOfSims = 0;
        while(!reachedThreshold) {
            MCTSNode bestLeafNode = currentNode.getBestLeafNode();
            //System.out.println(currentNode.getChildNodes());
            bestLeafNode.playoutSimulation(board.getCurrentPlayer());
            //System.out.println("bestleafnode wins: " + bestLeafNode.getWins() + " sims: " + bestLeafNode.getSims());
            currentAmountOfSims++;
            if(currentAmountOfSims >= maxSims) reachedThreshold = true; //to enable otherways of thresholds eg time
        }
        moveNode = currentNode.getBestSimulationChildNode();
        //System.out.println("bestmovenode wins: " + moveNode.getWins() + " sims: " + moveNode.getSims());
        //System.out.println("Found move using: " + MCTSNode.totalSims + " sims");
        //System.out.println();
        return moveNode;
        }

    public double getExplorationParameter() {
        return explorationParameter;
    }

    public MCTSNode getRootNode() {
        return rootNode;
    }
}
