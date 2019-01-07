package AI.Tests.MCTS_GA;

import AI.MCTS;
import AI.MCTSNode;
import Core.Board;
import Core.Logic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static Core.Board.BLACK;
import static Core.Board.EMPTY;
import static Core.Board.WHITE;

public class MCTSTestCyle {
    public static void main(String[] args) {
        System.out.println("Enter max depth of MCTS 1");
        Scanner scanner = new Scanner(System.in);
        int depth1 = scanner.nextInt();
        System.out.println("Enter max depth of MCTS 2");
        scanner = new Scanner(System.in);
        int depth2 = scanner.nextInt();
        System.out.println("Amount of games to be simulated");
        scanner = new Scanner(System.in);
        int games = scanner.nextInt();
        //int games = 1;
        System.out.println("board size");
        scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        MCTS mcts1 = new MCTS(0);
        MCTS mcts2 = new MCTS(0);

        Board board = new Board(size);
        MCTSNode node = new MCTSNode(board);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("MCTSDepthTest.csv");
            writer.append("depth1,depth2,mcts1Wins,mcts2Wins,draws");
            writer.append("\n");
            int winner = 0;

            for (int i = 1; i < depth1; i++) {
                //System.out.println("depth1: " + i);
                mcts1 = new MCTS(i);
                for(int j = 1; j < depth2; j++){
                    //System.out.println("depth2: " + j);
                    mcts2 = new MCTS(j);
                    int mcts1Wins = 0;
                    int mcts2Wins = 0;
                    int draws = 0;
                    for(int k = 0; k < games; k++) {
                        boolean gameFinished = false;
                        while (!gameFinished) {
                            //System.out.println("Entering while loop");
                            if (board.getCurrentPlayer() == BLACK) {
                                //board.displayBoardGrid();
                                //System.out.println("mcts1 turn");
                                node = mcts1.findMove(board);

                            }
                            if (board.getCurrentPlayer() == WHITE) {
                                //board.displayBoardGrid();
                                //System.out.println("mcts2 turn");
                                node = mcts2.findMove(board);
                            }
                            //System.out.println("Setting board to board found by MCTS");
                            board = new Board(node.getData());
                            //board.displayBoardGrid();
                            board.incrementTurn();
                            //System.out.println("Turn: " + board.getTurn());
                            board.changePlayer();
                            if(!Logic.checkMovePossible(board)) {
                                //System.out.println("!Logic.checkMovePossible(board)");
                                board.incrementTurn();
                                board.changePlayer();
                                if(!Logic.checkMovePossible(board)) {
                                    //System.out.println("!Logic.checkMovePossible(board) after changing player");
                                    //System.out.println("Game is finished");
                                    gameFinished = true;
                                    if (board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                                        winner = BLACK;
                                        mcts1Wins++;
                                        System.out.println("mcts1 win");
                                    } else if (board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                                        winner = WHITE;
                                        mcts2Wins++;
                                        System.out.println("mcts2 win");
                                    } else {
                                        winner = EMPTY;
                                        draws++;
                                    }
                                    board = new Board(size);
                                }
                            }
                        }
                    }
                    writer.append(Integer.toString(i));
                    writer.append(",");
                    writer.append(Integer.toString(j));
                    writer.append(",");
                    writer.append(Integer.toString(mcts1Wins));
                    writer.append(",");
                    writer.append(Integer.toString(mcts2Wins));
                    writer.append(",");
                    writer.append(Integer.toString(draws));
                    writer.append("\n");
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
