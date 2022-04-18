package Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Game.GameObject.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    boolean running = false;
    GraphicsContext gp;
    Canvas canvas;

    ControlHandler controlHandler;
    int Timer = 0;
    boolean gamerunning = true;

    ArrayList<Player> playerList = new ArrayList<Player>();

    public GameHandler()
    {
        //set up canvas side
        scene = new Scene(this,Color.BLACK);
        canvas = new Canvas(screenWidth,screenHight);
        this.getChildren().add(canvas);

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

      
    }

    private void Update() {
        
        if(gamerunning)
        {
            level.update();
            controlHandler.update();
        }
        
        if(Timer == 180)
        {
            EndGame();
        }
    }

    private void Tic()
    {
        Timer++;
        if(gamerunning) level.Tic();
        
    }

    private void EndGame()
    {
        gamerunning = false;
    }

    private void startGame()
    {
        //set up level
        Map map = new Map();
        map.setScreenX(64);
        map.setScreenY(64);
        level = new Level(this,map, worldHight, worldWidth);
        level.setWorldX(0);
        level.setWorldY(0);
        level.playerlist = playerList;
        level.setupLevel();

        //setup controler
        controlHandler = new ControlHandler();
        controlHandler.player = level.playerlist.get(0);
        scene.setOnKeyPressed(controlHandler);

        controlHandler.level = level;
    }
}
