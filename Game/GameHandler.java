package Game;

import java.util.ArrayList;

import Game.GameObject.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameHandler implements Runnable {

    final int TitleScale = 64;  //48 x 48

    final int maxScreenCol = 14;
    final int maxScreenRow = 11;
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
    ArrayList<Player> playerlist;


    public GameHandler()
    {
        //set up level
        level = new Level(this, worldHight, worldWidth);
        Map map = new Map();
        level.setupLevel(map);

        //set up canvas side
        canvas = new Canvas(screenWidth,screenHight);

        // else 
        Player player = new Player(worldWidth/2, worldHight/2, screenWidth/2, screenHight/2);

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
        //player.draw(gp);
    }
    private void Update() {
        
        level.update(gp);
        /*player.update();
        gamePanel.update();

        gamePanel.screenX = gamePanel.worldX - player.worldX + player.screenX;
        gamePanel.screenY = gamePanel.worldY - player.worldY + player.screenY;
        
        int x = 0;
        int y = 0;

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].setScreenX(gamePanel.screenX + x );
                blocks[i][j].setScreenY(gamePanel.screenY + y );
                y += 64;
            }
            x += 64;
            y = 0;
        }*/

    }
}
