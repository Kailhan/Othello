package AI.Tests;

import AI.EvaluationFunction;
import AI.Stupid;
import Core.Board;

public class EvaluationFunctionTest {


    public static void main(String[] args){

        Board board = new Board();
        GenericTest generic = new GenericTest();
        Stupid stupid = new Stupid();
        EvaluationFunction evaluator = new EvaluationFunction(board) ;



        //for(int i = 0; i <100; i++) {
            generic.test(evaluator, stupid, 1000, 8);

            System.out.println("Player 1 wins: " + (generic.getPlayer1Wins()));
            System.out.println("Player 2 wins: " + generic.getPlayer2Wins());
            System.out.println("Draws: " + generic.getDraws());
        //}
    }
}
