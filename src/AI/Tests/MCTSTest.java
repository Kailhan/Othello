package AI.Tests;

import AI.MCTS;
import AI.MCTSNode;
import Core.Board;
import Core.Logic;

import java.util.Scanner;

import static Core.Board.BLACK;
import static Core.Board.WHITE;

public class MCTSTest {
    public static void main(String[] args) {
        System.out.println("Enter tree depth of MCTS 1");
        Scanner scanner = new Scanner(System.in);
        int depth1 = scanner.nextInt();
        System.out.println("Enter tree depth of MCTS 2");
        scanner = new Scanner(System.in);
        int depth2 = scanner.nextInt();
        //System.out.println("Amount of games to be simulated");
        //scanner = new Scanner(System.in);
        //int games = scanner.nextInt();
        int games = 1;
        System.out.println("board size");
        scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        MCTS mcts1 = new MCTS(depth1);
        MCTS mcts2 = new MCTS(depth2);

        Board board = new Board(size);
        MCTSNode node = new MCTSNode(board);
        int mcts1Wins = 0;
        int mcts2Wins = 0;
        int draws = 0;

        for (int i = 0; i < games; i++) {
            boolean gameFinished = false;
            while (!gameFinished) {
                if (board.getCurrentPlayer() == BLACK) {
                    node = mcts1.findMove(board);
                    System.out.println("mcts1 turn");
                }
                if (board.getCurrentPlayer() == WHITE) {
                    node = mcts2.findMove(board);
                    System.out.println("mcts2 turn");
                }

                board.setBoardGrid(node.getBoard().getBoardGrid());
                board.displayBoardGrid();
                board.incrementTurn();
                //System.out.println("Turn: " + board.getTurn());
                board.changePlayer();
                if(!Logic.checkMovePossible(board)) {
                    //System.out.println("!Logic.checkMovePossible(board)");
                    board.incrementTurn();
                    board.changePlayer();
                    if(!Logic.checkMovePossible(board)) {
                        gameFinished = true;
                        if (board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                            mcts1Wins++;
                            //System.out.println("mcts1 win");
                        } else if (board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                            mcts2Wins++;
                            //System.out.println("mcts2 win");
                        } else {
                            draws++;
                        }
                    }
                }
            }
            board.displayBoardGrid();
        }
        System.out.println("MCTS1 wins: " + mcts1Wins);
        System.out.println("MCTS2 wins: " + mcts2Wins);
        System.out.println("MCTS draws: " + draws);
    }
}
