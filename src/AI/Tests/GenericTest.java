package AI.Tests;
import AI.AI;
import AI.Genetic_Algorithm.GA_MiniMaxAlph;
import Core.Board;
import Core.Logic;

import java.util.Scanner;

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

        for (int i = 0; i < gamesToBeSimmed; i++) {
            boolean gameFinished = false;
            board = new Board(boardSize);
            int[] move = new int[2];
            while (!gameFinished) {
                if (board.getCurrentPlayer() == BLACK) {
                    move = player1.getBestMove(board);
                    //System.out.println("mcts1 turn");
                }
                if (board.getCurrentPlayer() == WHITE) {
                    move = player2.getBestMove(board);
                    //System.out.println("mcts2 turn");
                }
                board.applyMove(move[0], move[1]);
                //board.displayBoardGrid();
                board.incrementTurn();
                //System.out.println("Turn: " + board.getTurn());
                board.changePlayer();
                if(!Logic.checkMovePossible(board)) {
                    //System.out.println("!Logic.checkMovePossible(board)");
                    //evaluator.evaluate(board);
                    board.incrementTurn();
                    board.changePlayer();
                    if(!Logic.checkMovePossible(board)) {
                        gameFinished = true;
                        if (board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                            player1Wins++;
                            //System.out.println("mcts1 win");
                        } else if (board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                            player2Wins++;
                            //System.out.println("mcts2 win");
                        } else {
                            draws++;
                        }
                    }
                }
            }
            //System.out.println("Board at end of test cycle");
            //board.displayBoardGrid();
            //System.out.println("Game finished: " + gameFinished);
            System.out.println("Games simulated: " + i);
        }
        System.out.println("Player1 wins: " + player1Wins);
        System.out.println("Player2 wins: " + player2Wins);
        System.out.println("draws: " + draws);
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
