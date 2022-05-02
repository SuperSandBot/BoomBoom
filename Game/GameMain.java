package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameMain extends Application {
    
    final int screenWidth = 1024;
    final int screenHight = 768;
    
    Stage gameStage;
    Scene scene;
    GameHandler gameHandler;
    MainMenu mainMenu;
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        mainMenu = new MainMenu();
        mainMenu.setup();
        mainMenu.gameMain = this;
        mainMenu.screenWidth = this.screenWidth;
        mainMenu.screenHight = this.screenHight;
        gameStage = primaryStage;

        gameStage.setTitle("BoomBoom");
        gameStage.setWidth(screenWidth);
        gameStage.setHeight(screenHight);
        gameStage.setScene(mainMenu.scene);
        gameStage.setResizable(false);
        gameStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
