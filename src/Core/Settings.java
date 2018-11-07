package Core;

public class Settings {

    //values to return in the class object:
    private int difficultyLevel;
    private int gameMode;
    private int boardSize;
    private Board board;
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int HvH = 0;
    public static final int HvA = 1;
    public static final int AvA = 2;
    public static final int SIZE_SMALL = 4;
    public static final int SIZE_MEDIUM = 6;
    public static final int SIZE_LARGE = 8;


    public Settings() {
        this(EASY,HvH,SIZE_LARGE);
    }

    public Settings(int difficultyLevel, int gameMode, int boardSize) {
        this(difficultyLevel, gameMode, boardSize, new Board(boardSize));
    }

    public Settings(int difficultyLevel, int gameMode, int boardSize, Board board) {
        this.difficultyLevel = difficultyLevel;
        this.gameMode = gameMode;
        this.boardSize = boardSize;
        if(board == null) {
            this.board = new Board(boardSize);
        } else {
            this.board = board;
        }
    }

    //getters & setters

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    public int getGameMode() {
        return gameMode;
    }
    public int getBoardSize() {
        return boardSize;
    }
    public Board getBoard() {
        return board;
    }

}
