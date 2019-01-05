package AI;

import Core.Board;
import Core.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Core.Board.BLACK;
import static Core.Board.WHITE;

public class MCTSNode {


    public static final int WIN = 1;
    public static final int DRAW = 0;
    public static final int LOSS = 0;

    public static int totalSims;

    private double wins;
    private double sims;

    private Board board;
    private static Random rand = new Random();

    private MCTSNode parentNode;
    //private MCTSNode[] childNodes = new MCTSNode[8 * 8]; // size of board hardcoded so that we notice immediately if we're maing a stupid amount of nodes
    private List<MCTSNode> childNodes = new ArrayList<MCTSNode>();

    public MCTSNode(Board board) {
        this.board = board;
        this.parentNode = null;
        this.wins = 0;
        this.sims = 0;
    }

    public int getRow() {
        int row = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = new Board(parentNode.getData());
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = new Board(this.getData());
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
//        System.out.println("getX parentBoard");
//        parentBoard.displayBoardGrid();
//        System.out.println("getX currentBoard");
//        currentBoard.displayBoardGrid();
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
        //System.out.println("getY parentBoard");
        //parentBoard.displayBoardGrid();
        //System.out.println("getY currentBoard");
        //currentBoard.displayBoardGrid();
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
        double exploitationScore = (sims == 0) ? 0 : (double)(wins/sims);
        double explorationScore = (wins == 0) ? 0 : MCTS.EXPLORATION_PARAMETER * (double)(Math.sqrt(Math.log(totalSims)/wins));
        if(sims != 0) {
            System.out.println("ploit: " + exploitationScore);
            System.out.println("plore: " + explorationScore);
        }
        double selectionScore = exploitationScore + explorationScore;

//        double selectionScore = (sims == 0) ? 0 : 1/sims;

        return selectionScore;
    }

    public void playoutSimulation(int currentPlayer) {
        Board startBoard = new Board(board);
        Board simulationBoard = new Board(board);
        boolean gameFinished = false;
        int[] move;
        while(!gameFinished) {
            if(Logic.checkMovePossible(simulationBoard)) {
                move = selectMove(simulationBoard, startBoard);
                simulationBoard.applyMove(move);
                simulationBoard.incrementTurn();
                simulationBoard.changePlayer();
            } else {
                simulationBoard.incrementTurn();
                simulationBoard.changePlayer();
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
        }
        if(currentPlayer == WHITE) {
            if(numberOfBlackCoins > numberOfWhiteCoins) gameState = LOSS;
            if(numberOfBlackCoins < numberOfWhiteCoins) gameState = WIN;
        }

        setWins(wins + gameState);
        setSims(sims + 1);
        MCTSNode.totalSims++;
        MCTSNode currentNode = this;

        while(currentNode.getParentNode() != null) {
            if(currentNode.getParentNode().getData().getCurrentPlayer() == currentPlayer) {
                currentNode.getParentNode().setWins(currentNode.getParentNode().getSims() + gameState);
                //System.out.println("node does belong to same player ");
            } else {
                //System.out.println("node does not belong to same player ");
            }
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
        double maxScore = -1;
        for(MCTSNode childNode : childNodes) {
            double childNodeScore = childNode.getSelectionScore();
            if(childNodeScore > maxScore) maxScore = childNodeScore;
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
            double childNodeScore = (childNode.getSims() == 0) ? 0 : (double)childNode.getWins()/childNode.getSims();
            if(childNodeScore > maxScore) maxScore = childNodeScore;
        }
        for(MCTSNode childNode : childNodes) {
            double childNodeScore = (childNode.getSims() == 0) ? 0 : (double)childNode.getWins()/childNode.getSims();
            if(childNodeScore >= maxScore) potentialChildren.add(childNode);
            //System.out.println(childNode.getWins());
            //System.out.println(childNode.getSims());
            //System.out.println(childNodeScore);
        }
        return potentialChildren.get(rand.nextInt(potentialChildren.size()));
    }

    public MCTSNode getBestLeafNode() {
        MCTSNode bestLeafNode = null;
        MCTSNode currentNode = this;
        //if(currentNode.childNodes.isEmpty()) System.out.println("childNodes in getBestLeafNode empty");
        while(!currentNode.childNodes.isEmpty()) {
            currentNode = currentNode.getBestSelectionChildNode();
        }
        currentNode.createChildren();
        return currentNode;
    }

    public void createChildren() {
        int[][] possibleMoves = Logic.getPossibleMoves(board);
        for(int i = 0; i < possibleMoves.length; i++){
            Board possibleBoard = new Board(board);
            possibleBoard.applyMove(possibleMoves[i]);
            possibleBoard.incrementTurn();
            possibleBoard.changePlayer();
            MCTSNode possibleNode = new MCTSNode(possibleBoard);
            possibleNode.setParentNode(this);
            childNodes.add(possibleNode);
        }
    }

    public MCTSNode getRandomChild() {
        return childNodes.get(rand.nextInt(childNodes.size()));
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
}
