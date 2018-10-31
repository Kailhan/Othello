package Core;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartGame extends Application {
    public static void main(String[] args) {launch(args);}

    private Stage primaryStage;
    private SettingsScene settingsScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Othello Game - Settings");
        //gameScene = new Core.GameScene(primaryStage);
        settingsScene = new SettingsScene(primaryStage);
        primaryStage.setScene(settingsScene.getSettingsScene());
        primaryStage.show();
    }
}


