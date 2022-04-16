package Game;

import java.util.ArrayList;

import Game.GameObject.*;
import Game.GameObject.Object;
import javafx.scene.canvas.GraphicsContext;

public class Level extends Object{
    
    public int worldWidth;
    public int worldHight;

    GameHandler gh;
    Map map;
    Block[][] blocks;
    ArrayList<Player> playerlist = new ArrayList<Player>();
    ArrayList<Boom> Booms = new ArrayList<Boom>();
    ArrayList<Block> SpawnPoint = new ArrayList<Block>();

    public Level(GameHandler gh,Map map , int worldHight, int worldWidth)
    {
        super();
        this.gh = gh;
        this.map = map;
        this.worldHight = worldHight;
        this.worldWidth = worldWidth;

    }

    public void draw(GraphicsContext gp)
    {
        map.draw(gp);

        for (Player player : playerlist) {
            player.draw(gp);
        }

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].draw(gp);
            }
        } 

        for (Boom boom : Booms) {
            boom.draw(gp);
        }

    }

    public void update()
    {   
        for (Player player : playerlist) {
            player.update();
        }

        //xác định vị trí máp trên màng hình
        map.setScreenX(this.getScreenX() +64);
        map.setScreenY(this.getScreenY() +64);
        
        // xác định vị trí block trên màng hình
        int x = 0;
        int y = 0;

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].setScreenX(this.getScreenX() + x + 64);
                blocks[i][j].setScreenY(this.getScreenY() + y + 64);
                blocks[i][j].update();
                y += 64;
            }
            x += 64;
            y = 0;
        }

        for (Boom boom : Booms) {
            boom.setScreenX(boom.block.getScreenX());
            boom.setScreenY(boom.block.getScreenY());
        }

        this.setScreenX(this.getWorldX() - playerlist.get(0).getWorldX() + playerlist.get(0).getScreenX());
        this.setScreenY(this.getWorldY() - playerlist.get(0).getWorldY() + playerlist.get(0).getScreenY());
    }

    public void playerPlantBoom(Player player,Block block)
    {
        Boom boom = new Boom(block.getWorldX(), block.getWorldY(), block.getScreenX(), block.getScreenY());
        boom.block = block;
        boom.power = player.power;
        Booms.add(boom);
    }

    public boolean checkBoomPos(Block block)
    {
        for (Boom boom : Booms) {
            if(boom.block == block) return true;
        }
        return false;
    }

    public void setupLevel()
    {

        blocks = new Block[map.Row][map.Col];
        int[][] blockMap = map.blockMap;
        int x = 0;
        int y = 0;
        
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                // xac dinh vi tri
                blocks[i][j] = new Block();
                blocks[i][j].setWorldX(this.getWorldX() + x + 96);
                blocks[i][j].setWorldY(this.getWorldY() + y + 96);

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
        SpawnPoint.add(blocks[0][map.Col -1]);
        SpawnPoint.add(blocks[map.Row - 1][0]);
        SpawnPoint.add(blocks[map.Row - 1][map.Col -1 ]);


         //add player      
         Player player = new Player();
         player.setWorldX(SpawnPoint.get(0).getWorldX());
         player.setWorldY(SpawnPoint.get(0).getWorldY());
         player.Pos = SpawnPoint.get(0);
         player.setScreenX(gh.screenWidth/2);
         player.setScreenY(gh.screenHight/2);
         player.level = this;
         playerlist.add(player);
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
