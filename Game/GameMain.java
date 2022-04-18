package Game;

import Game.Sound.GameSound;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameMain extends Application {
    
    final int screenWidth = 1024;
    final int screenHight = 768;
    
    Scene scene;
    GameHandler gameHandler;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        gameHandler = new GameHandler();
        // GameSound gs = new GameSound();
        // gs.putAudio(GameSound.BOMB);
        primaryStage.setTitle("BoomBoom");
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHight);
        primaryStage.setResizable(false);
        primaryStage.setScene(gameHandler.scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
