package Game;

import java.util.ArrayList;

import Game.GameObject.*;
import javafx.scene.canvas.GraphicsContext;

public class Level {
    
    public int worldWidth;
    public int worldHight;

    GameHandler gh;
    Map map;
    Block[][] blocks;

    ArrayList<Block> SpawnPoint = new ArrayList<Block>();

    public Level(GameHandler gh,Map map , int worldHight, int worldWidth)
    {
        this.gh = gh;
        this.map = map;
        this.worldHight = worldHight;
        this.worldWidth = worldWidth;
    }

    public void draw(GraphicsContext gp)
    {
        map.draw(gp);

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].draw(gp);
            }
        } 
    }

    public void update()
    {
        /*
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

    public void setupLevel()
    {

        blocks = new Block[map.Col][map.Row];
        int[][] blockMap = map.blockMap;
        int x = 0;
        int y = 0;

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                // xac dinh vi tri
                blocks[i][j] = new Block();
                blocks[i][j].setWorldX((worldWidth - map.getScreenX())/2 + x + 32 );
                blocks[i][j].setWorldY((worldHight - map.getScreenY())/2 + y + 32 );
                blocks[i][j].setScreenX(map.getScreenX() + x);
                blocks[i][j].setScreenY(map.getScreenY() + y);

                // xac dinh the loai
                blocks[i][j].setBType(blockMap[i][j]);

                y += 64;
            }
            x += 64;
            y = 0;
        }

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].top = DFS(blocks,i,j - 1); // top
                blocks[i][j].down = DFS(blocks,i,j + 1); // down
                blocks[i][j].left = DFS(blocks,i - 1 ,j); // left
                blocks[i][j].right = DFS(blocks,i + 1 ,j); // right
            }
        }  

        SpawnPoint.add(blocks[0][0]);
        SpawnPoint.add(blocks[0][map.Row -1]);
        SpawnPoint.add(blocks[map.Col - 1][0]);
        SpawnPoint.add(blocks[map.Col - 1][map.Row -1 ]);

    }

    private Block DFS(Block[][] b , int i, int j)
    {
        if(i >= 0 && i < blocks.length && j >= 0 && j < blocks[0].length)
        {
            return blocks[i][j];
        }
        return null;
    }
}
