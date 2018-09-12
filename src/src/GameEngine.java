public class GameEngine {
    //player 1 is black, player 2 is white
    private int turn = 1; //player 1 begins


    public int getTurn(){
        return turn;
    }

    public void setTurn(int newTurn){
        turn = newTurn;
    }

}