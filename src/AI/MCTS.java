package AI;

import Core.Board;
import Core.Logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static Core.Board.BLACK;
import static Core.Board.EMPTY;
import static Core.Board.WHITE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Integer.max;

public class MCTS implements AI {

    private int treeDepth;
    private int totalSims;
    private int simsCounter = 0;
    private Random rand = new Random();
    private LinkedList<MCTSNode> nodeQueue = new LinkedList<MCTSNode>();
    public static final int DUMB = 0;
    public static final int WIN = 100;
    public static final int DRAW = 50;
    public static final int LOSS = 0;


    public MCTS(int totalSims) {
        this.totalSims = totalSims;
    }

    public int[] getMoves(Board board) {
        int[] move = new int[2];
        MCTSNode node = findMove(board);
        move[1] = node.getRow();
        move[2] = node.getColumn();
        return move;
    }
    public MCTSNode findMove(Board board) {
        MCTSNode root = new MCTSNode(board);
        nodeQueue.addFirst(root);
        do{
            MCTSNode toBeChecked = nodeSelection(nodeQueue);
            playoutSimulation(toBeChecked);
            System.out.println("nodequeueueuesize: " + nodeQueue.size());
        } while(nodeQueue.size() > 0);
        int maxScore = MIN_VALUE;
        ArrayList<MCTSNode> potentialNodes = new ArrayList<MCTSNode>();
        for (MCTSNode node : root.getChildren(root)) {
            //System.out.println("scores of children of roots (possible moves");
            //System.out.println(node.getScoreTotal()/node.getSimulations());
            if(node.getScoreTotal()/node.getSimulations() > maxScore) {
                maxScore = node.getScoreTotal()/node.getSimulations();
            }
        }
        for (MCTSNode node : root.getChildren(root)) {
            if(node.getScoreTotal()/node.getSimulations() >= maxScore) {
                potentialNodes.add(node);
            }
        }
        simsCounter = 0;
        System.out.println("potentialnodessize: " + potentialNodes.size());
        return potentialNodes.get(rand.nextInt(potentialNodes.size())); //makes sure we pick a random node instead of for example the last one that has a highest score
    }

    public void createChildren(MCTSNode parentNode) {
        Board board = parentNode.getBoard();
        for(int r = 0; r < board.getSize(); r++) {
            for(int c = 0; c < board.getSize(); c++) {
                if(Logic.checkSquareAllowed(r, c, board)) {
                    Board tmpCopyBoard =  new Board(board);
                    tmpCopyBoard.applyMove(r, c);
                    MCTSNode tmpNode = new MCTSNode(tmpCopyBoard, r, c);
                    parentNode.addChild(tmpNode);
                    nodeQueue.add(tmpNode);
                }
            }
        }
    }

    public MCTSNode nodeSelection(LinkedList nodeQueue) {
        double nodeScore = -1;

        ArrayList<MCTSNode> potentialNodes = new ArrayList<MCTSNode>();
        for(Object node : nodeQueue) {
            MCTSNode tmpNode = (MCTSNode) node;
            if(tmpNode.getNodeScore() > nodeScore) {
                nodeScore = tmpNode.getNodeScore();
                potentialNodes.add(tmpNode);
            }
        }
        MCTSNode toBeChecked = potentialNodes.get(rand.nextInt(potentialNodes.size()));
        nodeQueue.remove((Object) toBeChecked);
//        MCTSNode toBeChecked = (MCTSNode)nodeQueue.poll(); //retrieves and removes the first element of this list or returns null if this list is empty
//        if(toBeChecked.getDepth() < treeDepth) createChildren(toBeChecked);
        if(simsCounter < totalSims) createChildren(toBeChecked);
        simsCounter++;
        System.out.println("simsCounter: " + simsCounter);
        return toBeChecked;
    }

    public void playoutBackpropogation(MCTSNode node, int winner, int currentPlayer) {
        while(node.getParent() != null) {
            Board board = node.getBoard();
            if(winner == EMPTY) node.setScoreTotal(node.getScoreTotal() + DRAW);
            if(board.getCurrentPlayer() == currentPlayer) {
                if(currentPlayer == winner) node.setScoreTotal(node.getScoreTotal() + WIN);
            }
            node.setSimulations(node.getSimulations()+1);
            node = (MCTSNode)node.getParent();
        }
    }

    public void playoutSimulation(MCTSNode node) {
        Board board = new Board(node.getBoard());
        boolean gameFinished = false;
        int currentPlayer = board.getCurrentPlayer();
        do{
            //System.out.print("Performing playout simulation -> game not finished yet");
            if(Logic.checkMovePossible(board)) {
                int x = rand.nextInt(board.getSize());
                int y = rand.nextInt(board.getSize());
                if(Logic.checkSquareAllowed(x, y, board)) {
                    board.applyMove(x, y);
                    board.incrementTurn();
                    board.changePlayer();
                }
            }
            if(!Logic.checkMovePossible(board)) {
                board.changePlayer();
                if(!Logic.checkMovePossible(board)) { // No moves possible for either player so game has ended
                    if(board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                        playoutBackpropogation(node, BLACK, currentPlayer);
                    } else if(board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                        playoutBackpropogation(node, WHITE, currentPlayer);
                    } else {
                        playoutBackpropogation(node, EMPTY, currentPlayer);
                    }
                    gameFinished = true;
                }
            }
        } while(!gameFinished);
    }
}