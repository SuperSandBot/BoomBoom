package Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Game.GameObject.*;
import javafx.animation.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameHandler extends Parent implements Runnable {

    final int TitleScale = 64;  //64x64

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = TitleScale * maxScreenCol; // 1024 pixel 
    final int screenHight = TitleScale * maxScreenRow; // 768 pixel

    final int maxWorldCol = 50;
    final int maxWorldRow = 50;
    final int worldWidth = TitleScale * maxWorldCol;
    final int worldHight = TitleScale * maxWorldRow;

    Scene scene;
    Level level;
    GameMain gameMain;

    boolean running = false;
    GraphicsContext gp;
    GraphicsContext gptext;
    Canvas canvas;
    Canvas textcanvas;

    ControlHandler controlHandler;
    int Timer = 180;

    boolean gamerunning;
    boolean GameStartAnimation;
    boolean GameOverAnimation;
    boolean TimerAnimation;
    boolean putScore;

    ImageView LoadingScreen;
    ImageView blackScreen;
    ImageView imageGS;
    ImageView imageGO;
    ImageView player1score;
    ImageView player2score;
    ImageView imageUI;
    ImageView scorePanel;
    ImageView btnimage;
    ImageView btnimagemainmenu;
    ImageView btnImageContinue;

    Button btnRetry;
    Button btnmainmenu;
    Button btnContinue;

    FadeTransition fadeScreenOut;
    FadeTransition fadeScreenIn;
    FadeTransition fade;
    TranslateTransition transition;
    FadeTransition fadebtn;

    ArrayList<Player> playerList;
    ImageManeger imageManeger;
    int i;

    public GameHandler()
    {
        //set up canvas side
        scene = new Scene(this,Color.LIGHTSKYBLUE);
        canvas = new Canvas(screenWidth,screenHight);
        textcanvas = new Canvas(screenWidth,screenHight);
       
    }

    public void setup()
    {
        LoadingScreen =  new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/loadingscreen.png")));
        imageGS = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/GameStart.png")));
        imageGO = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/GameOver.png")));
        scorePanel = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/panel.png")));
        imageUI = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/TimerUI.png")));
        player1score = new ImageView( new Image(getClass().getResourceAsStream("/Game/Asset/UI/player1score.png")));
        player2score = new ImageView( new Image(getClass().getResourceAsStream("/Game/Asset/UI/player2score.png")));
        btnimage = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/retry_button.png")));
        btnimagemainmenu = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/mainmenu_btn.png")));
        btnImageContinue = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/continue_btn.png")));
        blackScreen = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/black_sceen.png")));


        btnRetry = new Button("",btnimage);
        btnRetry.setOnMouseClicked(e -> RestartGame());
        btnmainmenu = new Button("",btnimagemainmenu);
        btnmainmenu.setOnMouseClicked(e -> BackToMain());
        btnContinue = new Button("",btnImageContinue);
        btnContinue.setOnMouseClicked(e -> onBtnContinueClicked());

        //layer
        this.getChildren().add(canvas);
        this.getChildren().add(imageGO);
        this.getChildren().add(imageGS); 
        this.getChildren().add(scorePanel);
        this.getChildren().add(imageUI);
        this.getChildren().add(player1score);
        this.getChildren().add(player2score);
        this.getChildren().add(blackScreen);
        this.getChildren().add(textcanvas);
        this.getChildren().add(btnRetry);
        this.getChildren().add(btnmainmenu);
        this.getChildren().add(btnContinue);
        this.getChildren().add(LoadingScreen);

        imageManeger = new ImageManeger();

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
        });

        fadeScreenIn = new FadeTransition();
        fadeScreenIn.setNode(LoadingScreen);
        fadeScreenIn.setDuration(Duration.seconds(2));
        fadeScreenIn.setFromValue(0);
        fadeScreenIn.setToValue(1);
        fadeScreenIn.setOnFinished(p ->
        {
            startGame();
        });

        //set up update and draw and thread need to be last
        gp = canvas.getGraphicsContext2D();
        gptext = textcanvas.getGraphicsContext2D();

        new Thread(this).start();

        BackGroundExacutor.Scheduler.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                Tic();     
            }
         
        }, 0, 1, TimeUnit.SECONDS);   

        startGame();
    }

    @Override
    public void run() {

        running = true;

        while(running)
        {
            Update();
            Draw();

            // need this thread.sleep or say good bye to CPU and GPU
            try {
                Thread.sleep(35);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void Draw() {

        if(gamerunning) gp.clearRect(0, 0,screenWidth, screenHight);
        gptext.clearRect(0, 0, screenWidth, screenHight);
        if(gamerunning) level.draw(gp);

        if(gamerunning) GameScore();
        if(GameStartAnimation) GameStartAnimation();  
        if(TimerAnimation) TimerAnimation();

        if(putScore == true)
        {
            if(playerList.get(0).score > playerList.get(1).score)
            {
                gptext.setFont(Font.font(60));
                gptext.strokeText("Player1 WIN" , screenWidth/2 - 145, screenHight/2+10);
                gptext.fillText("Player1 WIN" , screenWidth/2 - 145, screenHight/2+10);
            }
            else if(playerList.get(0).score < playerList.get(1).score)
            {
                gptext.setFont(Font.font(60));
                gptext.strokeText("Player2 WIN" , screenWidth/2 - 145, screenHight/2+10);
                gptext.fillText("Player2 WIN" , screenWidth/2 - 145, screenHight/2+10);
            }
            else
            {
                gptext.setFont(Font.font(70));
                gptext.strokeText("DRAW" , screenWidth/2 - 90, screenHight/2 +10);
                gptext.fillText("DRAW" , screenWidth/2 - 90, screenHight/2 +10);
            }
        }

    }

    private void GameScore()
    {
        player1score.setVisible(true);
        player2score.setVisible(true);
        gptext.setFont(new Font(20));
        gptext.strokeText( playerList.get(0).score + "", 64, 64);
        gptext.fillText(playerList.get(0).score + "", 64, 64);

        gptext.strokeText( playerList.get(1).score + "", screenWidth - player2score.getImage().getHeight() + 16,64);
        gptext.fillText(playerList.get(1).score + "", screenWidth - player2score.getImage().getHeight() + 16,64);
    }

    private void GameStartAnimation()
    {
        gptext.setFont(new Font(80));
        switch(i)
        {
            case 5:
            case 4:
                break;
            case 3:
            case 2:
            case 1:
                gptext.strokeText( i + "" , 500, screenHight/2);
                gptext.fillText( i + "" , 500, screenHight/2);
                break; 
            case 0:
                imageGS.setVisible(true);                       
            default:
                break;
        } 
    }

    private void GameOverAnimation()
    {
        imageGO.setVisible(true);
        imageGO.setOpacity(1);
        scorePanel.setLayoutY(screenHight);    
        fade.stop();  
        fade.setNode(imageGO);
        fade.setDuration(Duration.millis(400));
        fade.setDelay(Duration.seconds(2));
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(p ->
        {    
            scorePanel.setVisible(true);  
            transition.stop();
            transition.setNode(scorePanel);
            transition.setDuration(Duration.millis(500));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setFromX(0);
            transition.setToY(-500);
            transition.setOnFinished(a ->
            {  
                btnRetry.setVisible(true); 
                fadebtn.stop();
                fadebtn.setNode(btnRetry);
                fadebtn.setDuration(Duration.millis(200));
                fadebtn.setDelay(Duration.seconds(2));
                fadebtn.setFromValue(0);
                fadebtn.setToValue(1);
                fadebtn.play();
                btnmainmenu.setVisible(true);
                fadebtn.stop();
                fadebtn.setNode(btnmainmenu);
                fadebtn.setDuration(Duration.millis(200));
                fadebtn.setDelay(Duration.seconds(1));
                fadebtn.setFromValue(0);
                fadebtn.setToValue(1);
                fadebtn.play();
                putScore = true;

            });
            transition.play();
        });
        fade.play();
    }
    
    private void TimerAnimation()
    {
        imageUI.setVisible(true);
        gptext.setFont(Font.font(30));
        gptext.strokeText( Timer + "" , screenWidth/2, 41);
        gptext.fillText( Timer + "" , screenWidth/2, 41);
    }

    private void Update() {
        if(gamerunning)
        {
            level.update();
            controlHandler.update();
        }
    }

    private void Tic()
    {
        if(gamerunning)
        {
            if(Timer == 0)
            {
                EndGame();
                Timer = -1;
            }
        }
        
        if(gamerunning)
        {
            if(TimerAnimation) Timer--;
            level.Tic();
        } 

        if(GameStartAnimation)
        {
            if(i == 0)
            {
                imageGS.setVisible(false);
                GameStartAnimation = false;
                TimerAnimation = true;
                gamerunning = true;
                controlHandler.control = true;      
            }
            i--;
        }

    }

    private void RestartGame()
    {
        LoadingScreen.setDisable(false);
        LoadingScreen.setOpacity(0);
        fadeScreenIn.play();
    }

    private void BackToMain()
    {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setup();
        mainMenu.gameMain = gameMain;
        mainMenu.screenWidth = this.screenWidth;
        mainMenu.screenHeight = this.screenHight;
        gameMain.gameStage.setScene(mainMenu.scene);
    }

    private void EndGame()
    {
        i = 0;
        gamerunning = false;
        TimerAnimation = false;
        GameOverAnimation = true;
        imageUI.setVisible(false); 
        player1score.setVisible(false);
        player2score.setVisible(false);
        GameOverAnimation();
    }

    private void startGame()
    {    
        i = 7;
        playerList = new ArrayList<Player>();

        fade = new FadeTransition();
        transition = new TranslateTransition();
        fadebtn = new FadeTransition();

        gamerunning = false;
        GameStartAnimation = false;
        GameOverAnimation = false;
        TimerAnimation = false;
        putScore = false;

        imageGS.setVisible(false);
        imageGO.setVisible(false);
        scorePanel.setVisible(false);
        imageUI.setVisible(false); 
        player1score.setVisible(false);
        player2score.setVisible(false);
        btnRetry.setVisible(false);
        btnmainmenu.setVisible(false);
        btnContinue.setVisible(false);
        blackScreen.setVisible(false);
        //set up level
        Map map = new Map();
        map.setScreenX(96);
        map.setScreenY(64);
        level = new Level(this,map, worldHight, worldWidth);
        level.setWorldX(worldWidth/2);
        level.setWorldY(worldHight/2);
        level.setScreenX(0);
        level.setScreenY(0);
        level.playerlist = playerList;
        level.setupLevel();

        //setup controler
        controlHandler = new ControlHandler(scene);
        controlHandler.player1 = level.playerlist.get(0);
        controlHandler.player2 = level.playerlist.get(1);
        controlHandler.gameHandler = this;
        controlHandler.level = level;
        controlHandler.control = false;
    /////////////////////////
        imageGS.setLayoutX(screenWidth/2 - imageGS.getImage().getWidth()/2);
        imageGS.setLayoutY(screenHight/2- imageGS.getImage().getHeight()/2);

        imageUI.setLayoutX(screenWidth/2 - imageUI.getImage().getWidth()/2);
        imageUI.setLayoutY(5);

        player1score.setLayoutX(16);
        player1score.setLayoutY(10);

        player2score.setLayoutX(screenWidth - player2score.getImage().getWidth() - 32);
        player2score.setLayoutY(10);

        imageGO.setLayoutX(screenWidth/2 - imageGO.getImage().getWidth()/2);
        imageGO.setLayoutY(screenHight/2- imageGO.getImage().getHeight()/2);

        scorePanel.setLayoutX(screenWidth/2 - scorePanel.getImage().getWidth()/2 +10);
        scorePanel.setLayoutY(screenHight);
        scorePanel.setTranslateX(screenWidth/2 - scorePanel.getImage().getWidth()/2 +10);
        scorePanel.setTranslateY(screenHight);

        btnRetry.setLayoutX(screenWidth/2 -btnimage.getImage().getWidth()/2);
        btnRetry.setLayoutY(screenHight - 230);
        btnRetry.setContentDisplay(ContentDisplay.CENTER);
        btnRetry.setPrefWidth(btnimage.getImage().getWidth());
        btnRetry.setPrefHeight(btnimage.getImage().getHeight());

        btnmainmenu.setLayoutX(screenWidth/2 -btnimagemainmenu.getImage().getWidth()/2);
        btnmainmenu.setLayoutY(screenHight - 130);
        btnmainmenu.setContentDisplay(ContentDisplay.CENTER);
        btnmainmenu.setPrefWidth(btnimagemainmenu.getImage().getWidth());
        btnmainmenu.setPrefHeight(btnimagemainmenu.getImage().getHeight());

        btnContinue.setLayoutX(screenWidth/2 - btnImageContinue.getImage().getWidth()/2);
        btnContinue.setLayoutY(screenHight/2);

        blackScreen.setLayoutX(0);
        blackScreen.setLayoutY(0);
    //////////////////////////////////////
        level.update();
        level.draw(gp); 

        Timer = 180;
        fadeScreenOut.play();
        GameStartAnimation = true;

        
    }

    public void pauseGame(boolean p)
    {
        if(p)
        {
            gamerunning = false;
            blackScreen.setVisible(true);
            btnContinue.setVisible(true);
            btnmainmenu.setVisible(true);
        }
        else
        {
            gamerunning = true;
            blackScreen.setVisible(false);
            btnContinue.setVisible(false);
            btnmainmenu.setVisible(false);
        }
    }

    private void onBtnContinueClicked()
    {
        controlHandler.gamepause = false;
        gamerunning = true;
        blackScreen.setVisible(false);
        btnContinue.setVisible(false);
        btnmainmenu.setVisible(false);
    }
}
