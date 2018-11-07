package AI;

import Core.Board;
import Core.Logic;

import java.util.Random;

public class MCTS {

    private Board board;
    private GameTree gt;
    private int mctsMode;
    private int maxTurnsSimulated;
    private int totalMovesSim;
    public static final int DUMB = 0;

    public MCTS(int treeDepth) {
        this.totalMovesSim = treeDepth;
    }

    public Board findMove(Board board) {
        Board tmpCopyBoard =  new Board(board);
        Random rand = new Random();
        int currentPlayer = board.getCurrentPlayer();
        int x, y;
        int counterTotalMovesSim = 0;
        boolean firstMove = true;
        int startX, startY;
        do{
            x = rand.nextInt(board.getSize() + 1);
            y = rand.nextInt(board.getSize() + 1);
            if(Logic.isMovePossible(board)) {
                if(Logic.checkSquareAllowed(x, y, board)) {
                    if(firstMove) {
                        startX = x;
                        startY = y;
                    }
                    firstMove = false;
                    board.applyMove(x, y);
                    board.incrementTurn();
                    board.changePlayer();
                }
            }
            if(!Logic.isMovePossible(board)) {
                board.changePlayer();
                if(!Logic.isMovePossible(board)) { // No moves possible for either player so game has ended
                    break;
                }
            }
            counterTotalMovesSim++;
        } while(counterTotalMovesSim< totalMovesSim);
        if (board.getNrBlackSquares() > board.getNrWhiteSquares()) {
            //alert.setContentText("BLACK has won!!!");
        } else if(board.getNrBlackSquares() < board.getNrWhiteSquares()) {
            //alert.setContentText("WHITE has won!!!");
        } else {
            //alert.setContentText("BOTH win :)");
        }
        return tmpCopyBoard;
    }

}
