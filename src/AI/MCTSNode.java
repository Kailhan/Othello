package AI;

import Core.Board;
import Core.Logic;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static AI.MCTS.currentPlayer;
import static Core.Board.BLACK;
import static Core.Board.WHITE;

public class MCTSNode {


    public static final int WIN = 1;
    public static final int DRAW = 0;
    public static final int LOSS = 0;

    private double wins;
    private double sims;
    private double explorationParameter;
    private boolean terminalNode;

    private Board board;
    private static Random rand = new Random();

    private MCTSNode parentNode;
    private List<MCTSNode> childNodes = new ArrayList<MCTSNode>();

    public MCTSNode(Board board, double explorationParameter) {
        this.board = board;
        this.parentNode = null;
        this.wins = 0;
        this.sims = 0;
        this.explorationParameter = explorationParameter;
        this.terminalNode = false;
    }

    public int getRow() {
        int row = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = new Board(parentNode.getData());
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = new Board(this.getData());
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == 0 && currentBoardGrid[r][c] != 0) row = r;
            }
        }
        return row;
    }

    public int getColumn() {
        int column = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = new Board(parentNode.getData());
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = new Board(this.getData());
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == 0 && currentBoardGrid[r][c] != 0) column = c;
            }
        }
        return column;
    }

    public Board getData() {
        return board;
    }

    public double getSelectionScore() {
        //double exploitationScore = (sims == 0) ? 1 : ((double)(wins/sims));
        //double explorationScore = ((wins == 0) || (MCTS_TreeReuse.totalSims == 0)) ? 1 : (explorationParameter * (double)(Math.sqrt(Math.log(MCTS_TreeReuse.totalSims)/wins)));
        if(sims != 0) {
            //System.out.println("ploit: " + exploitationScore);
            //System.out.println("plore: " + explorationScore);
        }
//        System.out.println("ploit: " + exploitationScore);
//        System.out.println("plore: " + explorationScore);
        //double selectionScore = exploitationScore + explorationScore;

        double selectionScore = (sims == 0) ? 1 : 1/sims;
        return selectionScore;
    }

    public void playoutSimulation() {
        Board startBoard = new Board(board);
        Board simulationBoard = new Board(board);
        boolean gameFinished = false;
        int[] move;
        while(!gameFinished) {
            if(Logic.checkMovePossible(simulationBoard)) {
                move = selectMove(simulationBoard, startBoard);
                simulationBoard.applyMove(move);
            } else {
                simulationBoard.applyMove();
                if(!Logic.checkMovePossible(simulationBoard)) {
                    gameFinished = true;
                }
            }
        }

        int gameState = DRAW;
        int numberOfBlackCoins = simulationBoard.getNrSquares(BLACK);
        int numberOfWhiteCoins = simulationBoard.getNrSquares(WHITE);
        //System.out.println("numberOfWhiteCoins: " + numberOfWhiteCoins + " numberOfBlackCoins: " + numberOfBlackCoins + " total: " + (numberOfBlackCoins + numberOfWhiteCoins));
        if(currentPlayer == BLACK) {
            if(numberOfBlackCoins > numberOfWhiteCoins) gameState = WIN;
            if(numberOfBlackCoins < numberOfWhiteCoins) gameState = LOSS;
        } else if(currentPlayer == WHITE) {
            if(numberOfBlackCoins > numberOfWhiteCoins) gameState = LOSS;
            if(numberOfBlackCoins < numberOfWhiteCoins) gameState = WIN;
        }

        wins += gameState;
        sims++;
        MCTS.totalSims++;
        MCTSNode currentNode = this;

        while(currentNode.getParentNode() != null) {
            if(currentNode.getParentNode().getData().getCurrentPlayer() != currentPlayer) currentNode.getParentNode().setWins(currentNode.getParentNode().getWins() + gameState);
            currentNode.getParentNode().setSims(currentNode.getParentNode().getSims() + 1);
            currentNode = currentNode.getParentNode();
        }
    }

    public int[] selectMove(Board simulationBoard, Board startBoard) {
        int[][] possibleMoves = Logic.getPossibleMoves(simulationBoard);
        return possibleMoves[rand.nextInt(possibleMoves.length)];
    }


    public MCTSNode getBestSelectionChildNode() {
        List<MCTSNode> potentialChildren = new ArrayList<MCTSNode>();
        double maxScore = Integer.MIN_VALUE;
        for(MCTSNode childNode : childNodes) {
            double childNodeScore = childNode.getSelectionScore();
            if(childNodeScore >= maxScore) maxScore = childNodeScore;
        }
        for(MCTSNode childNode : childNodes) {
            double childNodeScore = childNode.getSelectionScore();
            if(childNodeScore >= maxScore) potentialChildren.add(childNode);
        }
        return potentialChildren.get(rand.nextInt(potentialChildren.size()));
    }

    public MCTSNode getBestSimulationChildNode() {
        List<MCTSNode> potentialChildren = new ArrayList<MCTSNode>();
        double maxScore = -1;
        for(MCTSNode childNode : childNodes) {
            double childNodeScore = (childNode.getSims() == 0) ? 0 : (double)childNode.getWins()/(double)childNode.getSims();
            if(childNodeScore >= maxScore) maxScore = childNodeScore;
        }
        for(MCTSNode childNode : childNodes) {
            double childNodeScore = (childNode.getSims() == 0) ? 0 : (double)childNode.getWins()/childNode.getSims();
            if(childNodeScore >= maxScore) potentialChildren.add(childNode);
        }
        return potentialChildren.get(rand.nextInt(potentialChildren.size()));
    }

    public MCTSNode getBestLeafNode() {
        MCTSNode currentNode = this;
        while(!currentNode.getChildNodes().isEmpty()) {
            currentNode = currentNode.getBestSelectionChildNode();
        }
        currentNode.createChildren();
        return currentNode;
    }

    public void createChildren() {
        Board board = new Board(this.board);
        int[][] possibleMoves;
        if(Logic.checkMovePossible(board)) {
            possibleMoves = Logic.getPossibleMoves(board);
            if(getChildNodes().isEmpty()) {
                for(int i = 0; i < possibleMoves.length; i++){
                    Board possibleBoard = new Board(board);
                    possibleBoard.applyMove(possibleMoves[i]);
                    MCTSNode possibleNode = new MCTSNode(possibleBoard, explorationParameter);
                    possibleNode.setParentNode(this);
                    childNodes.add(possibleNode);
                }
            } else {
                for(int i = 0; i < possibleMoves.length; i++){
                    Board possibleBoard = new Board(board);
                    possibleBoard.applyMove(possibleMoves[i]);
                    boolean alreadyHasBoard = false;
                    for(MCTSNode childNode : getChildNodes()) {
                        if(childNode.getData().isSameBoard(possibleBoard)) alreadyHasBoard = true;
                    }
                    if(!alreadyHasBoard) {
                        MCTSNode possibleNode = new MCTSNode(possibleBoard, explorationParameter);
                        possibleNode.setParentNode(this);
                        childNodes.add(possibleNode);
                    }
                }
            }
        } else {
            board.applyMove();
            if(Logic.checkMovePossible(board)) {
                if(getChildNodes().isEmpty()) {
                    Board possibleBoard = new Board(board);
                    MCTSNode possibleNode = new MCTSNode(possibleBoard, explorationParameter);
                    possibleNode.setParentNode(this);
                    childNodes.add(possibleNode);
                } else {
                    Board possibleBoard = new Board(board);
                    boolean alreadyHasBoard = false;
                    for(MCTSNode childNode : getChildNodes()) {
                        if(childNode.getData().isSameBoard(possibleBoard)) alreadyHasBoard = true;
                    }
                    if(!alreadyHasBoard) {
                        MCTSNode possibleNode = new MCTSNode(possibleBoard, explorationParameter);
                        possibleNode.setParentNode(this);
                        childNodes.add(possibleNode);
                    }
                }
            } else {
                terminalNode = true;
            }
        }
    }

    public MCTSNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(MCTSNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<MCTSNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<MCTSNode> childNodes) {
        this.childNodes = childNodes;
    }

    public double getWins() {
        return wins;
    }

    public void setWins(double wins) {
        this.wins = wins;
    }

    public double getSims() {
        return sims;
    }

    public void setSims(double sims) {
        this.sims = sims;
    }

    public boolean hasChildren() {
        return !getChildNodes().isEmpty();
    }

    public void addChild(MCTSNode childNode) {
        childNodes.add(childNode);
    }

    public int getTreeSize() {
        int treeSize = 1;
        List<MCTSNode> queue = new ArrayList<MCTSNode>();
        queue.add(this);
        while(!queue.isEmpty()){
            MCTSNode node = queue.remove(0);
            queue.addAll(node.getChildNodes());
            treeSize += node.getChildNodes().size();
        }
        return treeSize;
    }

    public int getHeight(MCTSNode rootNode){
        int height = 0;
        for(MCTSNode node : rootNode.getChildNodes()){
            height = Math.max(height, getHeight(node));
        }
        return height+1;
    }
}
