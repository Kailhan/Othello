package AI.Tests;
import AI.AI;
import Core.Board;
import Core.Logic;

import static Core.Board.BLACK;
import static Core.Board.WHITE;

public class GenericTest {
    private static int player1Wins = 0;
    private static int player2Wins = 0;
    private static int draws = 0;
    private static Board board;

    public static void test(AI player1, AI player2, int gamesToBeSimmed, int boardSize) {
        player1Wins = 0;
        player2Wins = 0;
        draws = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < gamesToBeSimmed; i++) {
            //if((i % (gamesToBeSimmed/5.0)) == 0) System.out.println("GTIter: " + i);
            boolean gameFinished = false;
            board = new Board(boardSize);
            int[] move = new int[2];
            while (!gameFinished) {
                if(Logic.checkMovePossible(board)) {
                    if (board.getCurrentPlayer() == BLACK) {
                        move = player1.getBestMove(board);
                    }
                    if (board.getCurrentPlayer() == WHITE) {
                        move = player2.getBestMove(board);
                    }
                    board.applyMove(move[0], move[1]);
                } else {
                    board.applyMove();
                    if(!Logic.checkMovePossible(board)) {
                        gameFinished = true;
                        if (board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                            player1Wins++;
                        } else if (board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                            player2Wins++;
                        } else {
                            draws++;
                        }
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        //System.out.println("Simulated one generic test cycle in generic test in ms: " + (endTime-startTime)/1000000);
    }

    public static int getPlayer1Wins() {
        return player1Wins;
    }

    public static int getPlayer2Wins() {
        return player2Wins;
    }

    public static int getDraws() {
        return draws;
    }
}
