package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameMain extends Application {
    
    final int screenWidth = 1024;
    final int screenHight = 768;
    
    Scene scene;
    GameHandler gameHandler;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        gameHandler = new GameHandler();

        scene = new Scene(gameHandler,Color.BLACK); 

        primaryStage.setTitle("BoomBoom");
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHight);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
