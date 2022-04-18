package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameMain extends Application {
    
    final int screenWidth = 1024;
    final int screenHight = 768;
    
    Scene scene;
    GameHandler gameHandler;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        gameHandler = new GameHandler();

        String musicFile = "Tranceition.mp4";     // For example

        Media sound = new Media(getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

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
