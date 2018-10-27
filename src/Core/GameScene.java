package Core;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
    private Settings settings;
    private Stage primaryStage;
    private BorderPane bPane;
    private Scene scene;
    private Board board;
    private int[][] boardGrid;
    private GridPane grid = new GridPane();
    private int windowSize = 600;
    private int tileSize;

    private File discBlack = new File("src/Assets/disc_blackBgr.png");
    private File discWhite = new File("src/Assets/disc_whiteBgr.png");
    private File bgr = new File("src/Assets/Bgr.png");
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
    private Button goToMenuBut;
    private Button restartGameBut;
    private Logic logic;
    private boolean isMovePossible;

    public GameScene(Stage primaryStage, Settings settings) {
        this.primaryStage = primaryStage;
        this.board = new Board(settings.getBoardSize());
        this.boardGrid = board.getBoardGrid();
        this.tileSize = windowSize/ boardGrid[0].length;
        this.discBlackImg = new Image(discBlack.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteImg = new Image(discWhite.toURI().toString(), tileSize, tileSize, false,false);
        this.bgrImg = new Image(bgr.toURI().toString(), tileSize, tileSize, false,false);
        for(int i = 0; i < 9; i++) {
            String fileName = "src/Assets/" + Integer.toString(i+1) + ".png";
            flipped.add(new File(fileName));
            flippedImg.add(new Image(flipped.get(flipped.size()-1).toURI().toString(), tileSize, tileSize, false,false)); // Prepares images that show how many disks will be flipped
        }
        this.discBlackMenuImg = new Image(discBlackMenu.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteMenuImg = new Image(discWhiteMenu.toURI().toString(), tileSize, tileSize, false,false);
        this.discBlackMenuView = new ImageView(discBlackMenuImg);
        this.discWhiteMenuView = new ImageView(discWhiteMenuImg);
        this.discBlackMenuImgSel = new Image(discBlackMenuSel.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteMenuImgSel = new Image(discWhiteMenuSel.toURI().toString(), tileSize, tileSize, false,false);
        this.discBlackMenuViewSel = new ImageView(discBlackMenuImgSel);
        this.discWhiteMenuViewSel = new ImageView(discWhiteMenuImgSel);
        this.goToMenuBut = new Button("Menu");
        goToMenuBut.setOnAction(e -> { // Switch to settings
            SettingsScene settingsScene = new SettingsScene(primaryStage);
            Node source = (Node)e.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Othello Settings");
            this.primaryStage.setScene(settingsScene.getSettingsScene());
            this.primaryStage.show();
        });
        this.goToMenuBut.setWrapText(true);
        this.restartGameBut = new Button("Restart Game");
        restartGameBut.setOnAction(e -> { // Create a new game with the same setings
            GameScene gameScene = new GameScene(primaryStage, settings);
            Node source = (Node)e.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Othello Game");
            this.primaryStage.setScene(gameScene.getGameScene());
            this.primaryStage.show();
        });
        this.restartGameBut.setWrapText(true);
        this.logic = new Logic();

        grid.setGridLinesVisible(false);
        grid.setAlignment(Pos.CENTER);
        redrawBoard();

        bPane = new BorderPane();
        bPane.setCenter(grid); //can directly create scene from grid if borderpane layout is not gonna be used
        scene = new Scene(bPane);
    }

    public void redrawBoard (){
        turnCheck();
        board.incrementTurn();
        grid.getChildren().clear();
        List<TileButton> toAdd = new ArrayList<>();
        for (int r = 0; r < boardGrid.length; r++) {
            for (int c = 0; c < boardGrid[r].length; c++) {
                if(boardGrid[r][c] == Board.EMPTY) {
                    if (Logic.checkSquareAllowed(r, c, board, board.getCurrentPlayer())) {
                        int flippedNo = logic.getFlippedDisks(r, c, board, board.getCurrentPlayer()).length;
                        flippedNo = (flippedNo > 9 ? 10 : flippedNo); // if more than 9 discs can be flipped set flippedNo to 10, because we only have assets up to "9+"
                        toAdd.add(new TileButton(r, c, new ImageView(flippedImg.get(flippedNo-1)))); // Show image corresponding to amount of discs that will be flipped
                    } else {
                        toAdd.add(new TileButton(r, c, new ImageView(bgrImg))); // If we cannot apply a move here just show standard background
                    }
                }
                if(boardGrid[r][c] == Board.BLACK) {
                    toAdd.add(new TileButton(r, c, new ImageView(discBlackImg)));
                }
                if(boardGrid[r][c] == Board.WHITE) {
                    toAdd.add(new TileButton(r, c, new ImageView(discWhiteImg)));
                }
                toAdd.get(toAdd.size()-1).setStyle("-fx-background-color: #00CE00; "); // Wacky css stuff, because stylesheet has not been properly updated yet
                toAdd.get(toAdd.size()-1).setStyle("-fx-background-color: #007F3F; ");
                toAdd.get(toAdd.size()-1).setOnAction((event) -> {
                    TileButton button = (TileButton)event.getSource();
                    //        int x = Integer.parseInt(ID.split("\\,")[0]); // "Hacky" way of finding corresponding buttons and board cells
//        int y = Integer.parseInt(ID.split("\\,")[1]);
                    updateBoard(button.getX(), button.getY()); // Actual communication with board, says which button has been clicked and thus which board cell needs to be checked
                });
                GridPane.setConstraints(toAdd.get(toAdd.size()-1), r, c);
            }
        }
        if(board.getCurrentPlayer() == board.BLACK) { // Updates visual that indicates whose turn it is
            GridPane.setConstraints(discBlackMenuViewSel, boardGrid.length, 1);
            GridPane.setConstraints(discWhiteMenuView, boardGrid.length + 2, 1);
            grid.getChildren().addAll(discBlackMenuViewSel, discWhiteMenuView);
        } else {
            GridPane.setConstraints(discBlackMenuView, boardGrid.length, 1);
            GridPane.setConstraints(discWhiteMenuViewSel, boardGrid.length + 2, 1);
            grid.getChildren().addAll(discBlackMenuView, discWhiteMenuViewSel);
        }

        GridPane.setConstraints(goToMenuBut, boardGrid.length + 2, 3);
        GridPane.setConstraints(restartGameBut, boardGrid.length, 3);

        Label blackDiscs = new Label(Integer.toString(board.getNrBlackSquares()));
        GridPane.setConstraints(blackDiscs, boardGrid.length, 2);
        Label whiteDiscs = new Label(Integer.toString(board.getNrWhiteSquares()));
        GridPane.setConstraints(whiteDiscs, boardGrid.length + 2, 2);
        grid.getChildren().addAll(toAdd);
        grid.getChildren().addAll(goToMenuBut, restartGameBut, blackDiscs, whiteDiscs);
        for(Node aNode: grid.getChildren()) {
            GridPane.setHalignment(aNode, HPos.CENTER);
        }
    }
    
    public void turnCheck() {
        isMovePossible = false;
        for (int r = 0; r < boardGrid.length; r++) { //Check if move is possible for current player
            for (int c = 0; c < boardGrid[r].length; c++) {
                if (Logic.checkSquareAllowed(r, c, board, board.getCurrentPlayer())) {
                    isMovePossible = true;
                    break;
                }
            }
        }
        if(!isMovePossible) { //if no move is possible increment turn and look if a move is possible for the other player
            board.changePlayer();
            for (int r = 0; r < boardGrid.length; r++) {
                for (int c = 0; c < boardGrid[r].length; c++) {
                    if (Logic.checkSquareAllowed(r, c, board, board.getCurrentPlayer())) {
                        isMovePossible = true;
                        break;
                    }
                }
            }
        }
    }

    public void updateBoard(int x, int y) {
        board.applyMove(x, y);
        redrawBoard();
        if(!isMovePossible) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Finished");
            alert.setHeaderText(null);
            if (board.getNrBlackSquares() > board.getNrWhiteSquares()) {
                alert.setContentText("BLACK has won!!!");
            } else {
                alert.setContentText("WHITE has won!!!");
            }
            alert.showAndWait();
            goToMenuBut.fire();
        }
    }

    public Scene getGameScene() {
        return scene;
    }
}

