package Game;

import Game.GameObject.*;

import javafx.scene.canvas.GraphicsContext;

public class Level {
    
    public int worldWidth;
    public int worldHight;

    GameHandler gh;

    Block[][] blocks;

    public Level(GameHandler gh, int worldHight, int worldWidth)
    {
        this.gh = gh;
        this.worldHight = worldHight;
        this.worldWidth = worldWidth;
    }

    public void draw(GraphicsContext gp)
    {
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].draw(gp);
            }
        } 
    }

    public void update(GraphicsContext gp)
    {

    }

    public void setupLevel(Map map)
    {
        blocks = new Block[map.Col][map.Row];
        int x = 0;
        int y = 0;

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j] = new Block();
                blocks[i][j].setWorldX((worldWidth - map.getScreenX())/2 + x + 32 );
                blocks[i][j].setWorldY((worldHight - map.getScreenY())/2 + y + 32 );
                blocks[i][j].setScreenX(map.getScreenX() + x);
                blocks[i][j].setScreenY(map.getScreenY() + y);

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
