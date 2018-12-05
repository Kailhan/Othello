package Core;

import AI.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Core.Board.BLACK;
import static Core.Board.EMPTY;
import static Core.Board.WHITE;

public class GameScene extends BorderPane {
    private Settings settings;
    private Stage primaryStage;
    private BorderPane bPane;
    private Scene scene;
    private Board board;
    private GridPane grid = new GridPane();
    private int windowSize = 600;
    private int tileSize;
    private int AI1Level;
    private int AI2Level;
    private static String[] AIs;
    private static final int MINIMAX_DEPTH = 7;

    private File discBlack = new File("src/Assets/disc_blackBgr.png");
    private File discWhite = new File("src/Assets/disc_whiteBgrFthr.png");
    private File bgr = new File("src/Assets/Bgr.png");
    private File discBlackMenu = new File("src/Assets/disc_blackMenu.png");
    private File discWhiteMenu = new File("src/Assets/disc_whiteMenuFthr.png");
    private File discBlackMenuSel = new File("src/Assets/disc_blackMenuSel.png");
    private File discWhiteMenuSel = new File("src/Assets/disc_whiteMenuSelFthr.png");
    private ArrayList<File> flipped = new ArrayList<>();
    private ArrayList<Image> flippedImg = new ArrayList<>();
    private List<TileButton> toAdd = new ArrayList<>();
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
    private Button loadBoardBut;
    private Button saveBoardBut;
    private Button playAI;
    private ComboBox<String> AI1;
    private ComboBox<String> AI2;
    private boolean movePossible;
    private boolean blackIsBot;
    private boolean whiteIsBot;

    public GameScene(Stage primaryStage, Settings settings) {
        this.settings = settings;
        this.primaryStage = primaryStage;
        this.board = new Board(settings.getBoard());
        this.tileSize = windowSize/ board.getSize();
        this.AIs = settings.getAIs();

        this.discBlackImg = new Image(discBlack.toURI().toString(), tileSize, tileSize, false,false);
        this.discWhiteImg = new Image(discWhite.toURI().toString(), tileSize, tileSize, false,false);
        this.bgrImg = new Image(bgr.toURI().toString(), tileSize, tileSize, false,false);
        for(int i = 0; i < 10; i++) {
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

        this.saveBoardBut = new Button("Save this board");
        saveBoardBut.setOnAction(e -> { // Save current board
            try {
                File recordsDir = new File(System.getProperty("user.home"), ".Othello/boards");
                if (!recordsDir.exists()) {
                    recordsDir.mkdirs();
                }
                String fileName = JOptionPane.showInputDialog(null,"Enter a file name for the current board");
                FileOutputStream fileOutputStream = new FileOutputStream(new File(System.getProperty("user.home"), ".Othello/boards/" + fileName + ".txt"));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(board);
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
        this.saveBoardBut.setWrapText(true);

        this.playAI = new Button("Let AI play current turn");
        playAI.setOnAction(e -> {
            botMove();
        });
        this.playAI.setWrapText(true);

        AI1 = new ComboBox<>();
        AI1.getItems().addAll(AIs);
        AI1.setPromptText("Change first AI");

        AI1.setOnAction(e -> {
            for (int i = 0; i < AIs.length; i++){
                if (AI1.getValue() == AIs[i]){
                    settings.setAI1Level(i);
                }
            }
        });

        AI2 = new ComboBox<>();
        AI2.getItems().addAll(AIs);
        AI2.setPromptText("Change second AI");

        AI2.setOnAction(e -> {
            for (int i = 0; i < AIs.length; i++){
                if (AI2.getValue() == AIs[i]){
                    settings.setAI2Level(i);
                }
            }
        });


        grid.setGridLinesVisible(false);
        grid.setAlignment(Pos.CENTER);
        redrawBoard();

        bPane = new BorderPane();
        bPane.setCenter(grid); //can directly create scene from grid if borderpane layout is not gonna be used
        scene = new Scene(bPane);
    }

    public void redrawBoard (){
        grid.getChildren().clear();
        createTiles();
        if(board.getCurrentPlayer() == BLACK) { // Updates visual that indicates whose turn it is
            GridPane.setConstraints(discBlackMenuViewSel, board.getSize(), 0);
            GridPane.setConstraints(discWhiteMenuView, board.getSize() + 2, 0);
            grid.getChildren().addAll(discBlackMenuViewSel, discWhiteMenuView);
        } else {
            GridPane.setConstraints(discBlackMenuView, board.getSize(), 0);
            GridPane.setConstraints(discWhiteMenuViewSel, board.getSize() + 2, 0);
            grid.getChildren().addAll(discBlackMenuView, discWhiteMenuViewSel);
        }

        Label blackDiscs = new Label(Integer.toString(board.getNrSquares(BLACK)));
        Label whiteDiscs = new Label(Integer.toString(board.getNrSquares(WHITE)));

        GridPane.setConstraints(goToMenuBut, board.getSize() + 2, 2);
        GridPane.setConstraints(restartGameBut, board.getSize(), 2);
        GridPane.setConstraints(AI1, board.getSize(), 3);
        GridPane.setConstraints(AI2, board.getSize() + 2, 3);
        GridPane.setConstraints(saveBoardBut, board.getSize(), 4);
        GridPane.setConstraints(playAI, board.getSize() + 2, 4);

        GridPane.setConstraints(blackDiscs, board.getSize(), 1);
        GridPane.setConstraints(whiteDiscs, board.getSize() + 2, 1);

        grid.getChildren().addAll(toAdd);
        grid.getChildren().addAll(goToMenuBut, restartGameBut, saveBoardBut, playAI, AI1, AI2, blackDiscs, whiteDiscs);
        for(Node aNode: grid.getChildren()) {
            GridPane.setHalignment(aNode, HPos.CENTER);
        }
    }

    public void createTiles(){
        toAdd.clear();
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if(board.checkTile(r, c, EMPTY)) {
                    if(Logic.checkSquareAllowed(r, c, board)) {
                        int flippedNo = Logic.getFlippedDisks(r, c, board).length;
                        flippedNo = (flippedNo > 9 ? 10 : flippedNo); // if more than 9 discs can be flipped set flippedNo to 10, because we only have assets up to "9+"
                        toAdd.add(new TileButton(r, c, new ImageView(flippedImg.get(flippedNo-1)))); // Show image corresponding to amount of discs that will be flipped
                    } else {
                        toAdd.add(new TileButton(r, c, new ImageView(bgrImg))); // If we cannot apply a move here just show standard background
                    }
                }
                if(board.checkTile(r, c, BLACK)) {
                    toAdd.add(new TileButton(r, c, new ImageView(discBlackImg)));
                }
                if(board.checkTile(r, c, WHITE)) {
                    toAdd.add(new TileButton(r, c, new ImageView(discWhiteImg)));
                }
                toAdd.get(toAdd.size()-1).setStyle("-fx-background-color: #00CE00; "); // Wacky css stuff, because stylesheet has not been properly updated yet
                toAdd.get(toAdd.size()-1).setStyle("-fx-background-color: #007F3F; ");
                toAdd.get(toAdd.size()-1).setOnAction((event) -> {
                    TileButton button = (TileButton)event.getSource();
                    playerMove(button.getX(), button.getY()); // Actual communication with board, says which button has been clicked and thus which board cell needs to be checked
                });
                GridPane.setConstraints(toAdd.get(toAdd.size()-1), r, c);
            }
        }
    }

    public void playerMove(int x, int y)
    {
        if(Logic.checkSquareAllowed(x, y, board))
            updateBoard(x, y);
    }

    public void botMove()
    {
        int AILevel;
        AILevel = settings.getAI1Level();
        if (board.getCurrentPlayer() == WHITE)
            AILevel = settings.getAI2Level();
        int[] move = new int[2];
        switch(AILevel)
        {
            case 0:
                MCTS mcts = new MCTS(100);
                move = mcts.getBestMove(board);
                updateBoard(move[0], move[1]);

            case 1:
                Stupid stupid = new Stupid();
                move = stupid.getBestMove(board);
                updateBoard(move[0], move[1]);
            case 2:
//                Minimax minimax = new Minimax(3, board);
//                move = minimax.getBestMove(board);
//                updateBoard(move[0], move[1]);
                MiniMaxAlph minimax = new MiniMaxAlph(MINIMAX_DEPTH, board);
                move = minimax.getBestMove(board);
                updateBoard(move[0], move[1]);
        }
    }

    public void updateBoard(int r, int c)
    {
        board.applyMove(r, c);
        board.incrementTurn();
        board.changePlayer();
        if(!Logic.checkMovePossible(board)) {
            board.incrementTurn();
            board.changePlayer();
            if(!Logic.checkMovePossible(board)) {
                redrawBoard();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Finished");
                alert.setHeaderText(null);
                if (board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                    alert.setContentText("BLACK has won!!!");
                } else if(board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                    alert.setContentText("WHITE has won!!!");
                } else {
                    alert.setContentText("BOTH win :)");
                }
                alert.showAndWait();
                goToMenuBut.fire();
            }
        }
        redrawBoard();
    }

    public void updateBoard(Board board){
        this.board = board;
        this.board.incrementTurn();
//        this.board.changePlayer();
        if(!Logic.checkMovePossible(board))
        {
            this.board.incrementTurn();
            this.board.changePlayer();
            if(!Logic.checkMovePossible(board))
            {
                redrawBoard();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Finished");
                alert.setHeaderText(null);
                if (board.getNrSquares(BLACK) > board.getNrSquares(WHITE)) {
                    alert.setContentText("BLACK has won!!!");
                } else if(board.getNrSquares(BLACK) < board.getNrSquares(WHITE)) {
                    alert.setContentText("WHITE has won!!!");
                } else {
                    alert.setContentText("BOTH win :)");
                }
                alert.showAndWait();
                goToMenuBut.fire();
            }
        }
        redrawBoard();

    }

    public Scene getGameScene() {
        return scene;
    }
}

