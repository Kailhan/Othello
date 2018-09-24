import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Application {

    private Stage window;
    private Board board;
    private int[][] boardGrid;
    private GridPane grid = new GridPane();
    private int windowSize = 600;
    private int gap = 5;
    private int tileSize;

    private File discBlack = new File("src/Assets/disc_blackBgr.png");
    private File discWhite = new File("src/Assets/disc_whiteBgr.png");
    private File bgr = new File("src/Assets/Bgr.png");
    private File discBlackMenu = new File("src/Assets/disc_blackMenu.png");
    private File discWhiteMenu = new File("src/Assets/disc_whiteMenu.png");
    private Image discBlackImg;
    private Image discWhiteImg;
    private Image bgrImg;
    private Image discBlackMenuImg;
    private Image discWhiteMenuImg;
    private Button button;
    private ImageView discBlackMenuView;
    private ImageView discWhiteMenuView;

    private Logic logic;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.tileSize = windowSize/ boardGrid[0].length;
        this.discBlackImg = new Image(discBlack.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteImg = new Image(discWhite.toURI().toString(), tileSize, tileSize, false,false);
        this.bgrImg = new Image(bgr.toURI().toString(), tileSize, tileSize, false,false);
        this.discBlackMenuImg = new Image(discBlackMenu.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteMenuImg = new Image(discWhiteMenu.toURI().toString(), tileSize, tileSize, false,false);
        this.discBlackMenuView = new ImageView(discBlackMenuImg);
        this.discWhiteMenuView = new ImageView(discWhiteMenuImg);
        this.button = new Button("Start a game");
        this.window = primaryStage;

        this.logic = new Logic(board);

        grid.setGridLinesVisible(false);
        redrawBoard();

        BorderPane bPane = new BorderPane();
        bPane.setCenter(grid); //can directly create scene from grid if borderpane layout is not gonna be used

        Scene scene = new Scene(bPane, windowSize + tileSize*3 + gap*(boardGrid.length+2), windowSize + gap*(boardGrid.length-1), Color.rgb(128, 128, 128));
        window.setTitle("Othello");
        window.setScene(scene);
        window.show();
    }

    public void redrawBoard (){
        grid.getChildren().clear();
        List<Button> toAdd = new ArrayList<>();
        for (int r = 0; r < boardGrid.length; r++) {
            for (int c = 0; c < boardGrid[r].length; c++) {
                if(boardGrid[r][c] == 0) {
                    toAdd.add(new Button(null, new ImageView(bgrImg)));
                }
                if(boardGrid[r][c] == 1) {
                    toAdd.add(new Button(null, new ImageView(discBlackImg)));
                }
                if(boardGrid[r][c] == 2) {
                    toAdd.add(new Button(null, new ImageView(discWhiteImg)));
                }
                toAdd.get(toAdd.size()-1).setId(r+","+c);
                toAdd.get(toAdd.size()-1).setOnAction((event) -> {
                    Button button = (Button)event.getSource();
                    updateBoard(button.getId());
                });
                GridPane.setConstraints(toAdd.get(toAdd.size()-1), r, c);
            }
        }

        GridPane.setConstraints(discBlackMenuView, boardGrid.length, 3);
        GridPane.setConstraints(discWhiteMenuView, boardGrid.length + 2, 3);
        GridPane.setConstraints(button, boardGrid.length, 5);

        Label blackDiscs = new Label(Integer.toString(board.getNumberOfBlackSquares()));
        GridPane.setConstraints(blackDiscs, boardGrid.length, 4);
        Label whiteDiscs = new Label(Integer.toString(board.getNumberOfWhiteSquares()));
        GridPane.setConstraints(whiteDiscs, boardGrid.length + 2, 4);

        grid.getChildren().addAll(toAdd);
        grid.getChildren().addAll(button, discBlackMenuView, discWhiteMenuView, blackDiscs, whiteDiscs);
    }

    public void updateBoard(String ID) {
        int x = Integer.parseInt(ID.split("\\,")[0]);
        int y = Integer.parseInt(ID.split("\\,")[1]);
        boardGrid = logic.applyMove(x, y).clone();
        redrawBoard();
    }
}