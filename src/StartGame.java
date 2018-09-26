import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class StartGame extends Application {
    public static void main(String[] args) {launch(args);}

    private Stage primaryStage;
    private SettingsScene settingsScene;
    private GameScene gameScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Othello Game - Settings");
        //gameScene = new GameScene(primaryStage);
        settingsScene = new SettingsScene(primaryStage);
        primaryStage.setScene(settingsScene.getSettingsScene());
        primaryStage.show();
    }
}


