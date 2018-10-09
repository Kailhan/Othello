package Core;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends BorderPane {
    private Stage primaryStage;
    private BorderPane bPane;
    private Scene scene;
    private Board board;
    private int[][] boardGrid;
    private GridPane grid = new GridPane();
    private int windowSize = 600;
    private int gap = 5;
    private int tileSize;

    private File discBlack = new File("src/Assets/disc_blackBgr.png");
    private File discWhite = new File("src/Assets/disc_whiteBgr.png");
    private File bgr = new File("src/Assets/Bgr.png");
    private File bgr1 = new File("src/Assets/Bgr1.png");
    private File discBlackMenu = new File("src/Assets/disc_blackMenu.png");
    private File discWhiteMenu = new File("src/Assets/disc_whiteMenu.png");
    private File discBlackMenuSel = new File("src/Assets/disc_blackMenuSel.png");
    private File discWhiteMenuSel = new File("src/Assets/disc_whiteMenuSel.png");
    private ArrayList<File> flipped = new ArrayList<>();
    private ArrayList<Image> flippedImg = new ArrayList<>();
    private Image discBlackImg;
    private Image discWhiteImg;
    private Image bgrImg;
    private Image bgrImg1;
    private Image discBlackMenuImg;
    private Image discWhiteMenuImg;
    private ImageView discBlackMenuView;
    private ImageView discWhiteMenuView;
    private Image discBlackMenuImgSel;
    private Image discWhiteMenuImgSel;
    private ImageView discBlackMenuViewSel;
    private ImageView discWhiteMenuViewSel;
    private Button button;


    private Logic logic;

    public GameScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.tileSize = windowSize/ boardGrid[0].length;
        this.discBlackImg = new Image(discBlack.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteImg = new Image(discWhite.toURI().toString(), tileSize, tileSize, false,false);
        this.bgrImg = new Image(bgr.toURI().toString(), tileSize, tileSize, false,false);
        for(int i = 0; i < 9; i++) {
            String fileName = "src/Assets/" + Integer.toString(i+1) + ".png";
            flipped.add(new File(fileName));
            flippedImg.add(new Image(flipped.get(flipped.size()-1).toURI().toString(), tileSize, tileSize, false,false));
        }
        this.discBlackMenuImg = new Image(discBlackMenu.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteMenuImg = new Image(discWhiteMenu.toURI().toString(), tileSize, tileSize, false,false);
        this.discBlackMenuView = new ImageView(discBlackMenuImg);
        this.discWhiteMenuView = new ImageView(discWhiteMenuImg);
        this.discBlackMenuImgSel = new Image(discBlackMenuSel.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteMenuImgSel = new Image(discWhiteMenuSel.toURI().toString(), tileSize, tileSize, false,false);
        this.discBlackMenuViewSel = new ImageView(discBlackMenuImgSel);
        this.discWhiteMenuViewSel = new ImageView(discWhiteMenuImgSel);
        this.button = new Button("Start a game");
        button.setOnAction(e -> {
            SettingsScene settingsScene = new SettingsScene(primaryStage);
            Node source = (Node)e.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Othello Core.Settings");
            this.primaryStage.setScene(settingsScene.getSettingsScene());
            this.primaryStage.show();
        });
        this.button.setWrapText(true);
        this.logic = new Logic();

        grid.setGridLinesVisible(false);
        redrawBoard();

        bPane = new BorderPane();
        bPane.setCenter(grid); //can directly create scene from grid if borderpane layout is not gonna be used
        scene = new Scene(bPane);
    }

    public void redrawBoard (){
        boolean isMovePossible = false;
        do {
            for (int r = 0; r < boardGrid.length; r++) {
                for (int c = 0; c < boardGrid[r].length; c++) {
                    if(Logic.checkSquareAllowed(r, c, board, board.getCurrentPlayer())) {
                        isMovePossible = true;
                    }
                }
            }
        } while (!isMovePossible);
        if(!isMovePossible) {
            board.incrementTurn();
            redrawBoard();
        }
        grid.getChildren().clear();
        List<Button> toAdd = new ArrayList<>();
        for (int r = 0; r < boardGrid.length; r++) {
            for (int c = 0; c < boardGrid[r].length; c++) {
                if(boardGrid[r][c] == Board.EMPTY) {
                    if(Logic.checkSquareAllowed(r, c, board, board.getCurrentPlayer())) {
                        int flippedNo = logic.getFlippedDisks(r, c, board, board.getCurrentPlayer()).length;
                        flippedNo = (flippedNo > 10 ? 10 : flippedNo);
                        toAdd.add(new Button(null, new ImageView(flippedImg.get(flippedNo-1))));
                    } else {
                        toAdd.add(new Button(null, new ImageView(bgrImg)));
                    }
                }
                if(boardGrid[r][c] == Board.BLACK) {
                    toAdd.add(new Button(null, new ImageView(discBlackImg)));
                }
                if(boardGrid[r][c] == Board.WHITE) {
                    toAdd.add(new Button(null, new ImageView(discWhiteImg)));
                }
                toAdd.get(toAdd.size()-1).setId(r+","+c);
                toAdd.get(toAdd.size()-1).setStyle("-fx-background-color: #00CE00; ");
                toAdd.get(toAdd.size()-1).setStyle("-fx-background-color: #007F3F; ");
                toAdd.get(toAdd.size()-1).setOnAction((event) -> {
                    Button button = (Button)event.getSource();
                    updateBoard(button.getId());
                });

                GridPane.setConstraints(toAdd.get(toAdd.size()-1), r, c);
            }
        }
        if(board.getTurn() % 2 == 0) {
            GridPane.setConstraints(discBlackMenuViewSel, boardGrid.length, 3);
            GridPane.setConstraints(discWhiteMenuView, boardGrid.length + 2, 3);
            grid.getChildren().addAll(discBlackMenuViewSel, discWhiteMenuView);
        } else {
            GridPane.setConstraints(discBlackMenuView, boardGrid.length, 3);
            GridPane.setConstraints(discWhiteMenuViewSel, boardGrid.length + 2, 3);
            grid.getChildren().addAll(discBlackMenuView, discWhiteMenuViewSel);
        }

        GridPane.setConstraints(button, boardGrid.length, 5);

        Label blackDiscs = new Label(Integer.toString(board.getNrBlackSquares()));
        GridPane.setConstraints(blackDiscs, boardGrid.length, 4);
        Label whiteDiscs = new Label(Integer.toString(board.getNrWhiteSquares()));
        GridPane.setConstraints(whiteDiscs, boardGrid.length + 2, 4);

        grid.getChildren().addAll(toAdd);
        grid.getChildren().addAll(button, blackDiscs, whiteDiscs);
    }

    public void updateBoard(String ID) {
        int x = Integer.parseInt(ID.split("\\,")[0]);
        int y = Integer.parseInt(ID.split("\\,")[1]);
        board.applyMove(x, y);
        redrawBoard();
        if(board.getNrEmptySquares() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Finished");
            alert.setHeaderText(null);
            if (board.getNrBlackSquares() > board.getNrWhiteSquares()) {
                alert.setContentText("BLACK has won!!!");
            } else {
                alert.setContentText("WHITE has won!!!");
            }
            alert.showAndWait();
            button.fire();
        }
    }

    public Scene getGameScene() {
        return scene;
    }
}
