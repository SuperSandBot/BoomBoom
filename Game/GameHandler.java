package Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.text.rtf.RTFEditorKit;

import Game.GameObject.*;
import javafx.animation.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameHandler extends Parent implements Runnable {

    final int TitleScale = 64;  //64x64

    final int maxScreenCol = 15;
    final int maxScreenRow = 12;
    final int screenWidth = TitleScale * maxScreenCol; // 1024 pixel 
    final int screenHight = TitleScale * maxScreenRow; // 768 pixel

    final int maxWorldCol = 50;
    final int maxWorldRow = 50;
    final int worldWidth = TitleScale * maxWorldCol;
    final int worldHight = TitleScale * maxWorldRow;

    Scene scene;
    Level level;

    boolean running = false;
    GraphicsContext gp;
    GraphicsContext gptext;
    Canvas canvas;
    Canvas textcanvas;

    ControlHandler controlHandler;
    int Timer;
    boolean gamerunning;
    boolean GameStartAnimation;
    boolean GameOverAnimation;
    boolean TimerAnimation;
    
    ImageView imageGS;
    ImageView imageGO;
    ImageView player1score;
    ImageView player2score;
    ImageView imageUI;
    ImageView scorePanel;

    Button btnRetry;

    ArrayList<Player> playerList = new ArrayList<Player>();
    int i;

    public GameHandler()
    {
        //set up canvas side
        scene = new Scene(this,Color.GREENYELLOW);
        canvas = new Canvas(screenWidth,screenHight);
        textcanvas = new Canvas(screenWidth,screenHight);

        imageGS = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/GameStart.png")));
        imageGO = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/GameOver.png")));
        scorePanel = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/panel.png")));
        imageUI = new ImageView(new Image(getClass().getResourceAsStream("/Game/Asset/UI/TimerUI.png")));
        player1score = new ImageView( new Image(getClass().getResourceAsStream("/Game/Asset/UI/player1score.png")));
        player2score = new ImageView( new Image(getClass().getResourceAsStream("/Game/Asset/UI/player2score.png")));

        //layer
        this.getChildren().add(canvas);
        this.getChildren().add(imageGS);
        imageGS.setVisible(false);
        this.getChildren().add(imageGO);
        imageGO.setVisible(false);
        this.getChildren().add(scorePanel);
        scorePanel.setVisible(false);
        this.getChildren().add(imageUI);
        imageUI.setVisible(false);
        this.getChildren().add(player1score);
        player1score.setVisible(false);
        this.getChildren().add(player2score);
        player2score.setVisible(false);
        this.getChildren().add(textcanvas);

        gamerunning = true;
        GameStartAnimation = false;
        GameOverAnimation = false;
        TimerAnimation = false;
        
        startGame();

        //set up update and draw and thread need to be last
        gp = canvas.getGraphicsContext2D();
        gptext = textcanvas.getGraphicsContext2D();
        new Thread(this).start();

        BackGroundExacutor.Scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                Tic();     
            }
            
        }, 0, 1, TimeUnit.SECONDS);
        i= 4;
        GameStartAnimation = true;
        gamerunning = false;
    }

    @Override
    public void run() {

        running = true;

        while(running)
        {
            Update();
            Draw();

            // need this thread.sleep or say good by to CPU and GPU
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void Draw() {

        gp.clearRect(0, 0,screenWidth, screenHight);
        gptext.clearRect(0, 0, screenWidth, screenHight);
        level.draw(gp);

        GameScore();
        if(GameStartAnimation) GameStartAnimation();
        if(GameOverAnimation)  GameOverAnimation();
        if(TimerAnimation) TimerAnimation();

    }

    private void GameScore()
    {
        
        gptext.setFont(new Font(20));
        gptext.strokeText( playerList.get(0).score + "", 50, 54);
        gptext.fillText(playerList.get(0).score + "", 50, 54);

        gptext.strokeText( playerList.get(1).score + "", screenWidth - player2score.getImage().getHeight() + 50,54);
        gptext.fillText(playerList.get(1).score + "", screenWidth - player2score.getImage().getHeight() + 50,54);
    }

    private void GameStartAnimation()
    {
        gptext.setFont(new Font(80));
        switch(i)
        {
            case 1:
            case 2:
            case 3:
                gptext.strokeText( i + "" , 500, screenHight/2);
                gptext.fillText( i + "" , 500, screenHight/2);
                break;
            case 0:
                imageGS.setVisible(true);
        
            default:
                case -1:
                imageGS.setVisible(false);
                break;
        } 
    }

    private void GameOverAnimation()
    {

        
    }
    
    private void TimerAnimation()
    {
        if(Timer <= 60)
        {
            gptext.setFill(Color.RED);
        }
        
        gptext.setFont(Font.font(30));
        gptext.strokeText( Timer + "" , screenWidth/2 + 36 , 36);
        gptext.fillText( Timer + "" , screenWidth/2 + 36, 36);
    }

    private void Update() {
        if(gamerunning)
        {
            level.update();
            controlHandler.update();
        }
        
        if(Timer == 0)
        {
            EndGame();
        }
    }

    private void Tic()
    {
        Timer--;
        if(gamerunning) level.Tic();

        if(GameStartAnimation)
        {
            if(i == 0)
            {
                GameStartAnimation = false;
                TimerAnimation = true;
                imageUI.setVisible(true);
                gamerunning = true;
                
            }
            i--;
        }
        
        if(GameOverAnimation)
        {
            i--;
        }
        
    }

    private void EndGame()
    {
        i = 0;
        gamerunning = false;
        TimerAnimation = false;
        GameOverAnimation = true;
    }

    private void startGame()
    {
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
        controlHandler = new ControlHandler();
        controlHandler.player1 = level.playerlist.get(0);
        controlHandler.player2 = level.playerlist.get(1);
        scene.setOnKeyPressed(controlHandler);
        controlHandler.level = level;
    
        imageGS.setLayoutX(screenWidth/2 - imageGS.getImage().getWidth()/2 +64);
        imageGS.setLayoutY(screenHight/2- imageGS.getImage().getHeight()/2);
        imageUI.setLayoutX(screenWidth/2 - 32 );
        imageUI.setLayoutY(0);
        player1score.setLayoutX(0);
        player1score.setLayoutY(0);
        player2score.setLayoutX(screenWidth - player2score.getImage().getWidth());
        player2score.setLayoutY(0);

        player1score.setVisible(true);
        player2score.setVisible(true);
        

        Timer = 20;
    }
}
