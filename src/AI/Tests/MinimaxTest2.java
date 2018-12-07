package AI.Tests;

import AI.*;
import Core.Board;
import Core.Logic;

import java.util.Scanner;

import static Core.Board.BLACK;
import static Core.Board.WHITE;

public class MinimaxTest2{

    public static void main(String[] args) {
        /*
        System.out.println("Enter totalSims of MCTS 1");
        Scanner scanner = new Scanner(System.in);
        int totalSims1 = scanner.nextInt();
        System.out.println("Enter totalSims of MCTS 2");
        scanner = new Scanner(System.in);
        int totalSims2 = scanner.nextInt();
        //System.out.println("Amount of games to be simulated");
        //scanner = new Scanner(System.in);
        //int games = scanner.nextInt();
        */
        int games = 1000;
        System.out.println("board size");
        //scanner = new Scanner(System.in);
        //int size = scanner.nextInt();

        Board board = new Board(8);
        Minimax m1 = new Minimax(1,board);
        Minimax m2 = new Minimax(1,board);

        //MCTS mcts1 = new MCTS(totalSims1);
        //MCTS mcts2 = new MCTS(totalSims2);


        //MCTSNode node = new MCTSNode(board);
        Node<Board> node = m1.getRoot();
        EvaluationFunction evaluator = new EvaluationFunction(board);
       // EvaluationFunction evaluator = new EvaluationFunction();
        int mcts1Wins = 0;
        int mcts2Wins = 0;
        int draws = 0;

        for (int i = 0; i < games; i++) {
            boolean gameFinished = false;
            board = new Board(8);
            while (!gameFinished) {
                if (board.getCurrentPlayer() == BLACK) {
                    node = m1.selectMove(node);
                    //System.out.println("mcts1 turn");
                }
                if (board.getCurrentPlayer() == WHITE) {
                    node = m2.selectMove(node);
                    //System.out.println("mcts2 turn");
                }
                board.setBoardGrid(node.getData().getBoardGrid());
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
            //System.out.println("Board at end of test cycle");
            //board.displayBoardGrid();
            //System.out.println("Game finished: " + gameFinished);
            System.out.println("Games simulated: " + i);
        }
        System.out.println("MCTS1 wins: " + mcts1Wins);
        System.out.println("MCTS2 wins: " + mcts2Wins);
        System.out.println("MCTS draws: " + draws);
    }
}

