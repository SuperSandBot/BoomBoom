package Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Game.GameObject.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    Canvas canvas;

    ControlHandler controlHandler;
    int Timer;
    boolean gamerunning;
    boolean GameStartAnimation;
    boolean GameOverAnimation;
    boolean TimerAnimation;
    
    Image imageGS;
    Image imageGO;
    Image imageUI;
    Image imageBoarder;
    ArrayList<Player> playerList = new ArrayList<Player>();
    int i;

    public GameHandler()
    {
        //set up canvas side
        scene = new Scene(this,Color.GREENYELLOW);
        canvas = new Canvas(screenWidth,screenHight);
        this.getChildren().add(canvas);

        imageGS = new Image(getClass().getResourceAsStream("/Game/Asset/UI/GameStart.png"));
        imageGO = new Image(getClass().getResourceAsStream("/Game/Asset/UI/GameOver.png"));
        imageBoarder =  new Image(getClass().getResourceAsStream("/Game/Asset/UI/boarder_02.png"));
        imageUI =  new Image(getClass().getResourceAsStream("/Game/Asset/UI/TimerUI.png"));
        gamerunning = true;
        GameStartAnimation = false;
        GameOverAnimation = false;
        TimerAnimation = false;
        
        startGame();

        //set up update and draw and thread need to be last
        gp = canvas.getGraphicsContext2D();

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
        level.draw(gp);

        if(GameStartAnimation) GameStartAnimation();
        if(GameOverAnimation)  GameOverAnimation();
        if(TimerAnimation) TimerAnimation();

    }

    private void GameStartAnimation()
    {
        gp.setFont(new Font(80));
        switch(i)
        {
            case 1:
            case 2:
            case 3:
                gp.strokeText( i + "" , 500, screenHight/2);
                gp.fillText( i + "" , 500, screenHight/2);
                break;
            
            default:
                case 0:
                gp.drawImage(imageGS,screenWidth/2 - imageGS.getWidth()/2 +64,screenHight/2- imageGS.getHeight()/2);
                break;
        } 
    }

    private void GameOverAnimation()
    {
        gp.drawImage(imageGO,screenWidth/2 - imageGO.getWidth()/2 +32,screenHight/2- imageGO.getHeight()/2);
    }
    
    private void TimerAnimation()
    {

        gp.drawImage(imageUI,screenWidth/2 - 32 , 0);
        gp.setFont(Font.font(30));
        gp.strokeText( Timer + "" , screenWidth/2 + 36 , 36);
        gp.fillText( Timer + "" , screenWidth/2 + 36, 36);
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
    
        Timer = 180;
    }
}
