package AI;

import Core.Board;
import Core.Logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static Core.Board.BLACK;
import static Core.Board.EMPTY;
import static Core.Board.WHITE;
import static java.lang.Integer.MIN_VALUE;

public class MCTSNode extends Node {

    public static final double explorationParameter = 1.414;
    private float scoreTotal;
    private int simulations = 1; //otherwise divide by zero when calculating node score might need to change the order/def of method
    private double exploreExploitScore;
    private static int totalSimulations = 1;
    private Board board;
    private MCTSNode[] children;
    private static Random rand = new Random();


    public MCTSNode(Board board) {
        super(board);
        this.board = board;
    }

    public MCTSNode(Board board, int x, int y) {
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

    public float getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(float scoreTotal) {
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
//        double totalSimulations = simulations;
//        MCTSNode node = this;
//        while(node.getParent() != null) {
//            node = (MCTSNode)node.getParent();
//        }
//        int totalSims = node.getSimulations();
//        //double score = scoreTotal/simulations + (explorationParameter * Math.sqrt(Math.log(totalSims)/simulations));
//        //double score = scoreTotal/simulations + (explorationParameter * 0.5 * ((totalSims)/simulations));
//        //System.out.println(scoreTotal);
//        System.out.println( 1.0/totalSimulations * explorationParameter + (scoreTotal/simulations));
//        return 1.0/totalSimulations * explorationParameter + (scoreTotal/simulations);
        return explorationParameter;
    }

    public void selectAction() {
        List<MCTSNode> visited = new LinkedList<MCTSNode>();
        MCTSNode current = this;
        visited.add(this);
        while(!current.isLeaf()){
            current = current.select();
            visited.add(current);
        }
        current.expand();
        MCTSNode newNode = current.select();
        visited.add(newNode);
        double value = newNode.playoutSimulation();
        for(MCTSNode node: visited) {
            node.nodePlayoutBackpropogation(value);
        }
    }

    public void expand() {
        int[][] possibleMoves = Logic.getPossibleMoves(board);
        this.children = new MCTSNode[possibleMoves.length];
        for(int i = 0; i < possibleMoves.length; i++) {
            Board tmpCopyBoard =  new Board(board);
            tmpCopyBoard.applyMove(possibleMoves[i]);
            MCTSNode tmpNode = new MCTSNode(tmpCopyBoard);
            this.children[i] = tmpNode;
        }
    }

    private MCTSNode select() {
        MCTSNode selected = null;
        List<MCTSNode> potential = new ArrayList<MCTSNode>();
        double bestValue = -1;
        for (MCTSNode c : children) {
            c.setExploreExploitScore(c.scoreTotal / (c.simulations) + Math.sqrt(Math.log(totalSimulations) / (c.simulations)));
            if (c.getExploreExploitScore() > bestValue) {
                bestValue = c.getExploreExploitScore();
            }
        }
        for (MCTSNode c : children) {
            if (c.getExploreExploitScore() >= bestValue) {
                potential.add(c);
            }
        }
        //return potential.get(rand.nextInt(potential.size()));
        return children[rand.nextInt(children.length)];
    }

    public double playoutSimulation() {
        boolean gameFinished = false;
        Board workingBoard = new Board(board);
        int currentPlayer = workingBoard.getCurrentPlayer();
        do{
            //System.out.print("Performing playout simulation -> game not finished yet");
            if(Logic.checkMovePossible(workingBoard)) {
                int[][] moves = Logic.getPossibleMoves(workingBoard);
                workingBoard.applyMove(moves[rand.nextInt(moves.length)]);
                workingBoard.incrementTurn();
                workingBoard.changePlayer();
            }
            if(!Logic.checkMovePossible(workingBoard)) {
                workingBoard.changePlayer();
                if(!Logic.checkMovePossible(workingBoard)) { // No moves possible for either player so game has ended
                    gameFinished = true;
                }
            }
        } while(!gameFinished);
        if(workingBoard.getNrSquares(BLACK) > workingBoard.getNrSquares(WHITE)) {
            if(currentPlayer == BLACK) {
                return 1;
            } else {
                return 0;
            }
        } else if(workingBoard.getNrSquares(BLACK) < workingBoard.getNrSquares(WHITE)) {
            if(currentPlayer == BLACK) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0.5;
        }
    }

    public void nodePlayoutBackpropogation(double value) {
        this.simulations++;
        this.scoreTotal += value;
    }

    public void setExploreExploitScore(double exploreExploitScore) {
        this.exploreExploitScore = exploreExploitScore;
    }

    public double getExploreExploitScore() {
        return exploreExploitScore;
    }

    public MCTSNode[] getChilds() {
        return this.children;
    }
}
