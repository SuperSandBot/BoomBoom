package Game;

import java.util.ArrayList;

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
    ArrayList<Player> playerlist = new ArrayList<Player>();
    ControlHandler controlHandler;


    public GameHandler()
    {
        //set up canvas side
        scene = new Scene(this,Color.BLACK);
        canvas = new Canvas(screenWidth,screenHight);
        this.getChildren().add(canvas);

        //set up level
        Map map = new Map();
        map.setScreenX(64);
        map.setScreenY(64);
        level = new Level(this,map, worldHight, worldWidth);
        level.setWorldX(0);
        level.setWorldY(0);
        level.setupLevel();

        //add player
        Player player = new Player();
        player.setWorldX(screenWidth/2);
        player.setWorldY(screenHight/2);
        player.setWorldX(level.SpawnPoint.get(0).getWorldX());
        player.setWorldY(level.SpawnPoint.get(0).getWorldY());
        player.setScreenX(screenWidth/2);
        player.setScreenY(screenHight/2);
        playerlist.add(player);

        //setup controler
        controlHandler = new ControlHandler();
        controlHandler.player = playerlist.get(0);
        scene.setOnKeyPressed(controlHandler);

      

        //set up update and draw and thread need to be last
        gp = canvas.getGraphicsContext2D();

        new Thread(this).start();
        
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
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void Draw() {

        gp.clearRect(0, 0,screenWidth, screenHight);

        level.draw(gp);

        for (Player player : playerlist) {
            player.draw(gp);
        }
      
    }

    private void Update() {
        
        level.update();
        for (Player player : playerlist) {
            player.update();
        }

        level.setScreenX(level.getWorldX() - playerlist.get(0).getWorldX() + playerlist.get(0).getScreenX());
        level.setScreenY(level.getWorldY() - playerlist.get(0).getWorldY() + playerlist.get(0).getScreenY());
    }
}
