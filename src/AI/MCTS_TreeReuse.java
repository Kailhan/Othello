package AI;

import Core.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MCTS_TreeReuse extends AI {

    private int maxSims;
    private long timeForMoveInMs;

    private MCTSNode_TreeReuse rootNode;
    private MCTSNode_TreeReuse previousNode;
    private Random rand;

    private double explorationParameter;

    public static int totalSims;
    public static int currentPlayer;

    public MCTS_TreeReuse(long timeForMoveInMs, double explorationParameter ) {
        System.out.println("using time as threshold");
        this.timeForMoveInMs = timeForMoveInMs;
        this.maxSims = 0;
        this.explorationParameter = explorationParameter;
        this.rand = new Random();
    }

    public MCTS_TreeReuse(int maxSims, double explorationParameter) {
        this.maxSims = maxSims;
        this.timeForMoveInMs = 0;
        this.explorationParameter = explorationParameter;
        this.rand = new Random();
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

    /**
     * Finds best move based for board on MCTS algorithm
     * @param board board for which best move needs to be found
     * @return best move
     */
    public int[] getBestMove(Board board) {
        int[] move = new int[2];
        MCTSNode_TreeReuse node = findMove(board);
        move[0] = node.getRow();
        move[1] = node.getColumn();
        return move;
    }

    /**
     * Gets best node using MCTS algorithm for board
     * @param board board for which best move needs to be found
     * @return best node
     */
    public MCTSNode_TreeReuse findMove(Board board) {
        MCTSNode_TreeReuse currentNode = findCurrentNode(board);
        MCTSNode_TreeReuse moveNode = null;
        boolean reachedThreshold = false;
        int currentAmountOfSims = 0;
        currentPlayer = board.getCurrentPlayer();
        long startTime = System.nanoTime();
        while(!reachedThreshold) {
            MCTSNode_TreeReuse bestLeafNode = currentNode.getBestLeafNode();
            bestLeafNode.playoutSimulation();
            currentAmountOfSims++;
            if((currentAmountOfSims >= maxSims) && (maxSims !=0)) {
                reachedThreshold = true; //to enable otherways of thresholds eg time
            }
            if(((System.nanoTime() - startTime)/1000000 >= timeForMoveInMs) && (timeForMoveInMs !=0)) {
                System.out.println("time elapsed: " +(System.nanoTime() - startTime)/1000);
                System.out.println("timeForMoveInMS: " + timeForMoveInMs);
                reachedThreshold = true; //to enable otherways of thresholds eg time
            }
        }

        moveNode = currentNode.getBestSimulationChildNode();
        previousNode = moveNode;

        return moveNode;
    }

    public MCTSNode_TreeReuse findCurrentNode(Board board) {
        MCTSNode_TreeReuse currentNode = null;
        if((previousNode == null) || (board.getTurn() == 1) || (board.getTurn() == 0)) {
            totalSims = 0;
            previousNode = new MCTSNode_TreeReuse(new Board(board), explorationParameter);
            currentNode = previousNode;
        }
        previousNode.createChildren();
        List<MCTSNode_TreeReuse> subTreeQueue = new ArrayList<MCTSNode_TreeReuse>();
        subTreeQueue.add(previousNode);
        while (!subTreeQueue.isEmpty()) {
            MCTSNode_TreeReuse nodeToBeChecked = subTreeQueue.remove(0);
            nodeToBeChecked.createChildren();
            subTreeQueue.addAll(nodeToBeChecked.getChildNodes());
            Board childBoard = nodeToBeChecked.getData();
            if (childBoard.isSameBoard(board)) {
                currentNode = nodeToBeChecked;
                break;
            }
        }
        return currentNode;
    }

    public double getExplorationParameter() {
        return explorationParameter;
    }

    public MCTSNode_TreeReuse getRootNode() {
        MCTSNode_TreeReuse currentNode = previousNode;
        do{
            currentNode = currentNode.getParentNode();
        } while(currentNode.getParentNode() != null);
        rootNode = currentNode;
        return currentNode;
    }
}