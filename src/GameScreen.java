import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Application {

    private Stage window;
    private int[][] board;
    private GridPane grid = new GridPane();
    private int windowSize = 600;
    private int gap = 5;

    File discBlack = new File("src/Assets/disc_blackBgr.png");
    File discWhite = new File("src/Assets/disc_whiteBgr.png");
    File bgr = new File("src/Assets/Bgr.png");

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {

        this.board = new Board().getBoardGrid();

        this.window = primaryStage;
        window.setTitle("Othello");

        //grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setGridLinesVisible(true);
        grid.setVgap(gap);
        grid.setHgap(gap);

        redrawBoard();

        Scene scene = new Scene(grid, windowSize + gap*(board[0].length-1), windowSize + gap*(board.length-1));
        window.setScene(scene);
        window.show();
    }

    public void redrawBoard (){
        Image discBlackImg = new Image(discBlack.toURI().toString(), windowSize/board[0].length, windowSize/board.length, false,false);
        Image discWhiteImg = new Image(discWhite.toURI().toString(), windowSize/board[0].length, windowSize/board.length, false,false);
        Image bgrImg = new Image(bgr.toURI().toString(), windowSize/board[0].length, windowSize/board.length, false,false);
        List<ImageView> toAdd = new ArrayList<>();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if(board[r][c] == 0) {
                    toAdd.add(new ImageView(bgrImg));
                    GridPane.setRowIndex(toAdd.get(toAdd.size()-1), r);
                    GridPane.setColumnIndex(toAdd.get(toAdd.size()-1), c);
                }
                if(board[r][c] == 1) {
                    toAdd.add(new ImageView(discBlackImg));
                    GridPane.setRowIndex(toAdd.get(toAdd.size()-1), r);
                    GridPane.setColumnIndex(toAdd.get(toAdd.size()-1), c);
                }
                if(board[r][c] == 2) {
                    toAdd.add(new ImageView(discWhiteImg));
                    GridPane.setRowIndex(toAdd.get(toAdd.size()-1), r);
                    GridPane.setColumnIndex(toAdd.get(toAdd.size()-1), c);
                }
            }
        }
        grid.getChildren().addAll(toAdd);
    }
}