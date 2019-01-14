package AI;

import Core.Board;
import Core.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MCTS_TreeReuse extends AI {

    private int maxSims;
    private int simsCounter = 0;
    private Random rand = new Random();
    private double explorationMultiplier = 0.0;
    private double explorationParameter = 1.414 * explorationMultiplier;
    private MCTSNode rootNode;
    private MCTSNode previousNode;

    public MCTS_TreeReuse(int maxSims) {
        this.maxSims = maxSims;
    }

    public MCTS_TreeReuse(int maxSims, double explorationParameter) {
        this.maxSims = maxSims;
        this.explorationParameter = explorationParameter;
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
        MCTSNode currentNode = findCurrentNode(board);
        MCTSNode moveNode = null;
        boolean reachedThreshold = false;
        int currentAmountOfSims = 0;
        while(!reachedThreshold) {
            MCTSNode bestLeafNode = currentNode.getBestLeafNode();
            bestLeafNode.playoutSimulation(board.getCurrentPlayer());
            currentAmountOfSims++;
            if(currentAmountOfSims >= maxSims) reachedThreshold = true; //to enable otherways of thresholds eg time
        }

        moveNode = currentNode.getBestSimulationChildNode();
        previousNode = moveNode;

        return moveNode;
    }

    public MCTSNode findCurrentNode(Board board) {
        MCTSNode currentNode = null;
        if((previousNode == null) || (board.getTurn() == 1) || (board.getTurn() == 0)) {
            previousNode = new MCTSNode(new Board(board), explorationParameter);
            currentNode = previousNode;
        }
//        System.out.println("previusnode currently finding node for");
//        System.out.println("boardturn: " + previousNode.getData().getTurn());
//        System.out.println("boardplayer: " + previousNode.getData().getCurrentPlayer());
//        System.out.println("possiblemoves: " + Logic.getPossibleMoves(previousNode.getData()).length);
//        previousNode.getData().displayBoardGrid();
//
//        System.out.println("board currently finding node for");
//        System.out.println("boardturn: " + board.getTurn());
//        System.out.println("boardplayer: " + board.getCurrentPlayer());
//        System.out.println("possiblemoves: " + Logic.getPossibleMoves(board).length);
//        board.displayBoardGrid();

        //currentNode.createChildren();

        previousNode.createChildren();
        List<MCTSNode> subTreeQueue = new ArrayList<MCTSNode>();
        subTreeQueue.add(previousNode);
        while (!subTreeQueue.isEmpty()) {
            MCTSNode nodeToBeChecked = subTreeQueue.remove(0);
            nodeToBeChecked.createChildren();
            subTreeQueue.addAll(nodeToBeChecked.getChildNodes());
            Board childBoard = nodeToBeChecked.getData();
            if (childBoard.isSameBoard(board)) {
                currentNode = nodeToBeChecked;
                break;
            }
        }

//        if(previousNode.getData().isSameBoard(board)) {
//            currentNode = previousNode;
//        } else {
//            for(MCTSNode childNode : previousNode.getChildNodes()) {
//                Board childBoard = childNode.getData();
//                if(childBoard.isSameBoard(board)) currentNode = childNode;
//            }
//        }
//        currentNode.createChildren();
        return currentNode;
    }

    public double getExplorationParameter() {
        return explorationParameter;
    }

    public MCTSNode getRootNode() {
        MCTSNode currentNode = previousNode;
        while(currentNode.getParentNode() != null) {
            currentNode = currentNode.getParentNode();
        }
        return currentNode;
    }
}
