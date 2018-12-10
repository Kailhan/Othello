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

public class MCTS extends AI {

    private int treeDepth;
    private int totalSims;
    private int simsCounter = 0;
    private Random rand = new Random();
    private LinkedList<MCTSNode> nodeQueue = new LinkedList<MCTSNode>();
    public static final int DUMB = 0;
    public static final float WIN = 1;
    public static final float DRAW = 0.5f;
    public static final float LOSS = 0;


    public MCTS(int totalSims) {
        this.totalSims = totalSims;
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
        simsCounter = 0;
        nodeQueue.clear();
        MCTSNode root = new MCTSNode(board);
        nodeQueue.addFirst(root);
        while(nodeQueue.size() > 0 && simsCounter < totalSims) {
            MCTSNode toBeChecked = nodeSelection(nodeQueue);
            toBeChecked.setVisited();
            playoutSimulation(toBeChecked);
            simsCounter++;
        }
        float maxScore = MIN_VALUE;
        ArrayList<MCTSNode> potentialNodes = new ArrayList<MCTSNode>();
        for (MCTSNode node : root.getChildren(root)) {
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
            double tmpNodeScore = tmpNode.getNodeScore();
            if(tmpNodeScore >= nodeScore) {
                nodeScore = tmpNode.getNodeScore();
            }
        }
        for(Object node : nodeQueue) {
            MCTSNode tmpNode = (MCTSNode) node;
            if(tmpNode.getNodeScore() >= nodeScore) {
                potentialNodes.add(tmpNode);
            }
        }
//        for(Object node : nodeQueue) {
//            MCTSNode tmpNode = (MCTSNode) node;
//            if(tmpNode.getNodeScore() > nodeScore) {
//                nodeScore = tmpNode.getNodeScore();
//                potentialNodes.add(tmpNode);
//            }
//        }
        MCTSNode toBeChecked = potentialNodes.get(rand.nextInt(potentialNodes.size()));
        nodeQueue.remove(toBeChecked);
        createChildren(toBeChecked);
        return toBeChecked;
    }

    public void playoutBackpropogation(MCTSNode node, int winner, int currentPlayer) {
        //if(node.getParent() == null) System.out.println("nodedaddynull");
        while(node.getParent() != null) {
            Board board = node.getBoard();
            if(winner == EMPTY) node.setScoreTotal(node.getScoreTotal() + DRAW);
            if(board.getCurrentPlayer() == currentPlayer) {
                if(currentPlayer == winner) node.setScoreTotal(node.getScoreTotal() + WIN);
            }
            //System.out.println("nodegetsims" + node.getSimulations());
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
                int[][] moves = Logic.getPossibleMoves(board);
                board.applyMove(moves[rand.nextInt(moves.length)]);
                board.incrementTurn();
                board.changePlayer();
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
