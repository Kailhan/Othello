package Core;

public class Settings {

    //values to return in the class object:
    private int AI1Level;
    private int AI2Level;
    private int boardSize;
    private Board board;
    private static String[] AIs = new String[]{"MCTS", "Stupid", "AB-Minimax","Minimax", "NegaMax","NegaScout", "MMAB Iterative deepening"};
    public static final int SIZE_SMALL = 4;
    public static final int SIZE_MEDIUM = 6;
    public static final int SIZE_LARGE = 8;


    public Settings() {
        this(1,1,SIZE_LARGE);
    }

    public Settings(int AI1Level, int AI2Level, int boardSize) {
        this(AI1Level, AI2Level, boardSize, new Board(boardSize));
    }

    public Settings(int AI1Level, int AI2Level, int boardSize, Board board) {
        this.AI1Level = AI1Level;
        this.AI2Level = AI2Level;
        this.boardSize = boardSize;
        if(board == null) {
            this.board = new Board(boardSize);
        } else {
            this.board = board;
        }
    }

    //getters & setters

    public int getAI1Level() {
        return AI1Level;
    }
    public void setAI1Level(int AI1Level){
        this.AI1Level = AI1Level;
    }
    public int getAI2Level() {
        return AI2Level;
    }
    public void setAI2Level(int AI2Level){
        this.AI2Level = AI2Level;
    }
    public int getBoardSize() {
        return boardSize;
    }
    public Board getBoard() {
        return board;
    }
    public static String[] getAIs(){
        return AIs;
    }

}
