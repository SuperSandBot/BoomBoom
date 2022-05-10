package application;

import application.Sound.GameSound;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MainMenu extends Parent{
    
    Scene scene;
    GameHandler gameHandler;
    GameMain gameMain;
    GameSound gs;

    int screenWidth;
    int screenHeight;

    Button playBtn;
    Button tutorialBtn;
    Button quitBtn;
    Button exitTutorialBtn;

    FadeTransition fadeScreenOut;
    FadeTransition fadeScreenIn;

    ImageView LoadingScreen;
    ImageView backGround;
    ImageView btnPlayImage;
    ImageView btnTutorialImage;
    ImageView tutorialImage;
    ImageView btnQuitImage;
    ImageView btnExitTutorial;
    ImageView btnExitImage;
    

    public MainMenu()
    {
        scene = new Scene(this,Color.BLACK);
    }

    public void setup()
    {
    	gs = new GameSound();
        LoadingScreen = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/loadingscreen.png")));
        backGround = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/GameMain.png")));

        btnPlayImage = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/Play_btn.png")));
        btnTutorialImage = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/tutorial_btn.png")));
        btnQuitImage = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/quit_btn.png")));
        btnExitImage = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/x_btn.png")));
        tutorialImage = new ImageView(new Image(getClass().getResourceAsStream("/application/Asset/UI/tutorial_sheet1.png")));

        playBtn = new Button("",btnPlayImage);
        playBtn.setOnMouseClicked(e -> playButtonClicked());
        tutorialBtn = new Button("",btnTutorialImage);
        tutorialBtn.setOnMouseClicked(e -> tutorialButtonClicked());
        quitBtn = new Button("",btnQuitImage);
        quitBtn.setOnMouseClicked(e -> quitButtonClicked());
        exitTutorialBtn = new Button("",btnExitImage);
        exitTutorialBtn.setOnMouseClicked(e -> exitTutorial());

        tutorialImage.setLayoutX(5);
        tutorialImage.setLayoutY(0);
        tutorialImage.setVisible(false);

        exitTutorialBtn.setLayoutX(900);
        exitTutorialBtn.setLayoutY(20);
        exitTutorialBtn.setVisible(false);

        playBtn.setLayoutX(64);
        playBtn.setLayoutY(200);

        tutorialBtn.setLayoutX(64);
        tutorialBtn.setLayoutY(320);

        quitBtn.setLayoutX(110);
        quitBtn.setLayoutY(440);

        this.getChildren().add(backGround);
        this.getChildren().add(playBtn);
        this.getChildren().add(tutorialBtn);
        this.getChildren().add(quitBtn);
        this.getChildren().add(tutorialImage);
        this.getChildren().add(exitTutorialBtn);
        this.getChildren().add(LoadingScreen);

        fadeScreenOut = new FadeTransition();
        fadeScreenOut.setNode(LoadingScreen);
        fadeScreenOut.setDuration(Duration.seconds(2));
        fadeScreenOut.setDelay(Duration.seconds(1));
        fadeScreenOut.setFromValue(1);
        fadeScreenOut.setToValue(0);
        fadeScreenOut.setOnFinished(p ->
        {
            LoadingScreen.setOpacity(0);
            LoadingScreen.setDisable(true);  
            gs.playOnBackGround(GameSound.GAMEMENU);
        });
        fadeScreenOut.play();

        fadeScreenIn = new FadeTransition();
        fadeScreenIn.setNode(LoadingScreen);
        fadeScreenIn.setDuration(Duration.seconds(2));
        fadeScreenIn.setFromValue(0);
        fadeScreenIn.setToValue(1);
        fadeScreenIn.setOnFinished(p ->
        {
            gameHandler = new GameHandler();
            gameHandler.gs = this.gs;
            gameHandler.setup();
            gameHandler.gameMain = gameMain;
            gameMain.gameStage.setScene(gameHandler.scene);
            
        });
        
    }

    private void playButtonClicked()
    {   
    	gs.playAudio(GameSound.BTNSOUND);
    	gs.backGroundMusic.stop();
        fadeScreenIn.play();
    }

    private void tutorialButtonClicked()
    {
    	gs.playAudio(GameSound.BTNSOUND);
        exitTutorialBtn.setVisible(true);
        tutorialImage.setVisible(true);
    }

    private void quitButtonClicked()
    {
    	gs.playAudio(GameSound.BTNSOUND);
        gameMain.gameStage.close();
    }

    private void exitTutorial()
    {
    	gs.playAudio(GameSound.BTNSOUND);
        tutorialImage.setVisible(false);
        exitTutorialBtn.setVisible(false);
    }
}
