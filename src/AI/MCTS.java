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

public class MCTS {

    private Board board;
    private GameTree gt;
    private int mctsMode;
    private int maxTurnsSimulated;
    private int treeDepth;
    private LinkedList<MCTSNode> nodeQueue = new LinkedList<MCTSNode>();
    public static final int DUMB = 0;
    public static final int WIN = 100;
    public static final int DRAW = 50;
    public static final int LOSS = 0;


    public MCTS(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    public MCTSNode findMove(Board board) {
        MCTSNode root = new MCTSNode(board);
        nodeQueue.addFirst(root);
        do{
            MCTSNode toBeChecked = nodeSelection(nodeQueue);
            playoutSimulation(toBeChecked);
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
        return potentialNodes.get(new Random().nextInt(potentialNodes.size())); //makes sure we pick a random node instead of for example the last one that has a highest score
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
        MCTSNode toBeChecked = (MCTSNode)nodeQueue.poll(); //retrieves and removes the first element of this list or returns null if this list is empty
        if(toBeChecked.getDepth() < treeDepth) createChildren(toBeChecked);
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
        Random rand = new Random();
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