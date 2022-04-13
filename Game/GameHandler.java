package Game;

import java.util.ArrayList;

import Game.GameObject.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameHandler extends Pane implements Runnable {

    final int TitleScale = 64;  //64x64

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = TitleScale * maxScreenCol; // 860 pixel 
    final int screenHight = TitleScale * maxScreenRow; // 640 pixel

    final int maxWorldCol = 50;
    final int maxWorldRow = 50;
    final int worldWidth = TitleScale * maxWorldCol;
    final int worldHight = TitleScale * maxWorldRow;

    Level level;

    boolean running = false;
    GraphicsContext gp;
    Canvas canvas;
    ArrayList<Player> playerlist = new ArrayList<Player>();


    public GameHandler()
    {
        //set up canvas side
        this.setPrefSize(screenWidth, screenHight);
        canvas = new Canvas(screenWidth,screenHight);
        this.getChildren().add(canvas);

        //set up level
        Map map = new Map();
        map.setScreenX(0);
        map.setScreenY(0);
        level = new Level(this,map, worldHight, worldWidth);
        level.setupLevel();

        //add player
        Player player = new Player();
        player.setWorldX(worldWidth/2);
        player.setWorldY(worldHight/2);
        player.setScreenX(screenWidth/2);
        player.setScreenY(screenHight/2);
        playerlist.add(player);

        int i = 0;
        for (Player p : playerlist) {
            
            p.setWorldX(level.SpawnPoint.get(i).getWorldX());
            p.setWorldY(level.SpawnPoint.get(i).getWorldY());
            i++;
        }

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
    }
}
