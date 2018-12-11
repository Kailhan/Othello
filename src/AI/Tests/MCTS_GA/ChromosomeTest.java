package AI.Tests.MCTS_GA;

import AI.EvaluationFunction;
import AI.MiniMaxAlph;
import AI.Stupid;
import AI.Tests.GenericTest;

public class ChromosomeTest {

    public static void main(String[] args) {
        int games = 1000;
        //double[] chromosome = {-638.2467089,377.936951,-470.8216715,-280.0405299,1920.731736,947.0323143,20.09849724,144.5824839,850.0535637,130.7802043,-1106.398786,44.35520982,-21.45856893,859.9369023,-598.4475435,637.3359287,6091.090244,4703.354456,5257.854224,6185.186622,4754.899827,6178.029636,4983.211247,4680.043758,4288.654551,4519.103317,4536.155648,4692.298142,5883.750501,5418.477587,5496.245341,6406.876556,5046.513266,5027.687097,5360.148754,5286.871554,5486.57782,5651.550298,5118.521855,5551.142948,5037.830647,4604.937221,4286.233131,4289.551299,4643.164724,4805.201739,6224.106713,4923.377134,6175.085781,5995.664085,4647.489719,6192.409308};
        double[] chromosome = {-638.2496995,377.8472856,-470.8007708,-279.9784528,1920.648706,20.04041853,946.9908125,144.6536393,850.0467756,130.7063168,-1106.365444,44.32198388,-21.51860205,859.9905457,-598.5572706,637.3455214,6091.109376,4703.346675,5257.898225,6185.119821,4754.914744,6178.024512,4983.213553,4680.040042,4288.636338,4519.10441,4536.195587,4692.296214,5883.783309,5418.504262,5496.22377,6406.878209,5046.508033,5027.666822,5360.251958,5286.926589,5486.567851,5651.559683,5118.521826,5551.163141,5037.799054,4604.920583,4286.222102,4289.497713,4643.175921,4805.29071,6224.063715,4923.342502,6175.101978,5995.652457,4647.493912,6192.480334};
        EvaluationFunction eval = new EvaluationFunction(chromosome);
        MiniMaxAlph alphabeta = new MiniMaxAlph(3, eval);
        GenericTest.test(eval, new Stupid(), games, eval.getBoard().getSize());
        double winRatio = GenericTest.getPlayer1Wins();
        System.out.println("GenericTest.getPlayer1Wins()" + GenericTest.getPlayer1Wins() + " GenericTest.getPlayer2Wins()" + GenericTest.getPlayer2Wins());
        GenericTest.test(new Stupid(), eval, games, eval.getBoard().getSize());
        System.out.println("GenericTest.getPlayer1Wins()" + GenericTest.getPlayer1Wins() + " GenericTest.getPlayer2Wins()" + GenericTest.getPlayer2Wins());
        winRatio += GenericTest.getPlayer2Wins();
        winRatio /= (games*2);
        System.out.println(winRatio);
    }
}
