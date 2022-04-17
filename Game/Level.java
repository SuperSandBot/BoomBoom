package Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Game.GameObject.*;
import Game.GameObject.Object;
import Game.GameObject.Block.blockTypes;
import javafx.scene.canvas.GraphicsContext;

public class Level extends Object{
    
    public int worldWidth;
    public int worldHight;

    GameHandler gh;
    Map map;
    Block[][] blocks;
    ArrayList<Player> playerlist = new ArrayList<Player>();
    ArrayList<Boom> Booms = new ArrayList<Boom>();
    ArrayList<BoomSplase> boomSplases = new ArrayList<BoomSplase>();
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

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].draw(gp);
            }
        } 

        if(boomSplases.size() > 0)
        {
            for(BoomSplase splase : boomSplases)
            {
                splase.draw(gp);
            }
        }

        for (Boom boom : Booms) {
            boom.draw(gp);
        }
  
        for (Player player : playerlist) {
            player.draw(gp);
        }

    }

    public void update()
    {   
        map.setScreenX(this.getScreenX() + 64);
        map.setScreenY(this.getScreenY() + 64);
        
        for (Player player : playerlist) {
            player.update();
        }

        for (Boom boom : Booms) {
            boom.update();
        }

        for (BoomSplase splase : boomSplases)
        {
            splase.update();
        }
        
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

        this.setScreenX(getWorldX() - playerlist.get(0).getWorldX() + playerlist.get(0).getScreenX());
        this.setScreenY(getWorldY() - playerlist.get(0).getWorldY() + playerlist.get(0).getScreenY());
    }

    public void Tic() {
        for (Boom boom : Booms) {
            boom.Tic();
        }
    }

    public void playerPlantBoom(Player player,Block block)
    {
        Boom boom = new Boom(block.getWorldX(), block.getWorldY(), block.getScreenX(), block.getScreenY());
        boom.pos = block;
        boom.power = player.power;
        boom.level = this;
        Booms.add(boom);
    }

    public boolean checkBoomPos(Block block)
    {
        for (Boom boom : Booms) {
            if(boom.pos == block) return true;
        }
        return false;
    }

    public void boomExplode(Boom boom)
    {
        int power = 3;

        //Top
        BoomSplase[] splasesTop = new BoomSplase[power + 1];
        splasesTop[0] = new BoomSplase(boom.pos,"top",false);
        boomSplases.add(splasesTop[0]);
        for(int i = 1;i < splasesTop.length ; i++)
        {
            if(splasesTop[i-1].pos.top != null)
            {
                if(splasesTop[i-1].pos.top.bType == blockTypes.NONE)
                {
                    splasesTop[i] = new BoomSplase(splasesTop[i - 1].pos.top, "top", false);
                    boomSplases.add(splasesTop[i]);
                    if(i+1 >= splasesTop.length) splasesTop[i].End = true;

                    Boom b = boomCheck(splasesTop[i].pos);
                    if(b != null)
                    {          
                        if(b.explode != true) b.boomExplode();
                    }
                }
                else
                {
                    splasesTop[i-1].End = true;
                    splasesTop[i-1].pos.top.detroyBlock();
                    break;
                }
            }
            else
            {
                splasesTop[i-1].End = true;
                break;
            }
        }  
        
        //Down
        BoomSplase[] splasesDown = new BoomSplase[power + 1];
        splasesDown[0] = new BoomSplase(boom.pos,"down",false);
        boomSplases.add(splasesDown[0]);
        for(int i = 1 ;i < splasesDown.length ; i++)
        {
            if(splasesDown[i-1].pos.down != null)
            {
                if(splasesDown[i-1].pos.down.bType == blockTypes.NONE)
                {
                    splasesDown[i] = new BoomSplase(splasesDown[i-1].pos.down, "down", false);
                    boomSplases.add(splasesDown[i]);
                    if(i+1 >= splasesDown.length) splasesDown[i].End = true;

                    Boom b = boomCheck(splasesDown[i].pos);
                    if(b != null)
                    {          
                        if(b.explode != true) b.boomExplode();
                    }
                }
                else
                {
                    splasesDown[i-1].End = true;
                    splasesDown[i-1].pos.down.detroyBlock();
                    break;
                }
            }
            else
            {
                splasesDown[i-1].End = true;
                break;
            }
        } 

        //Left
        BoomSplase[] splasesLeft = new BoomSplase[power + 1];
        splasesLeft[0] = new BoomSplase(boom.pos,"left",false);
        boomSplases.add(splasesLeft[0]);
        for(int i = 1;i < splasesLeft.length ; i++)
        {
            if(splasesLeft[i-1].pos.left != null)
            {
                if(splasesLeft[i-1].pos.left.bType == blockTypes.NONE)
                {
                    splasesLeft[i] = new BoomSplase(splasesLeft[i-1].pos.left, "left", false);
                    boomSplases.add(splasesLeft[i]);
                    if(i+1 >= splasesLeft.length) splasesLeft[i].End = true;

                    Boom b = boomCheck(splasesLeft[i].pos);
                    if(b != null)
                    {          
                        if(b.explode != true) b.boomExplode();
                    }
                }
                else
                {
                    splasesLeft[i-1].End = true;
                    splasesLeft[i-1].pos.left.detroyBlock();
                    break;
                }
            }
            else
            {
                splasesLeft[i-1].End = true;
                break;
            }
        }  

        //Right
        BoomSplase[] splasesRight = new BoomSplase[power + 1];
        splasesRight[0] = new BoomSplase(boom.pos,"right",false);
        boomSplases.add(splasesRight[0]);
        for(int i = 1;i < splasesRight.length ; i++)
        {
            if(splasesRight[i-1].pos.right != null)
            {
                if(splasesRight[i-1].pos.right.bType == blockTypes.NONE)
                {
                    splasesRight[i] = new BoomSplase(splasesRight[i-1].pos.right, "right", false);
                    boomSplases.add(splasesRight[i]);
                    if(i+1 >= splasesRight.length) splasesRight[i].End = true;

                    Boom b = boomCheck(splasesRight[i].pos);
                    if(b != null)
                    {          
                        if(b.explode != true) b.boomExplode();
                    }
                }
                else
                {
                    splasesRight[i-1].End = true;
                    splasesRight[i-1].pos.right.detroyBlock();
                    break;
                }
            }
            else
            {
                splasesRight[i-1].End = true;
                break;
            }
        } 


        BackGroundExacutor.Scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                Booms.remove(boom);
                boomSplases.clear();
            }
        }, 200, TimeUnit.MILLISECONDS);
    }

    private Boom boomCheck(Block pos)
    {
        for (Boom boom : Booms) {
            if(boom.pos == pos) return boom;
        }
        return null;
    }

    private Player playerCheck(Block pos)
    {
        for(Player player : playerlist)
        {
            if(player.Pos == pos) return player;
        }
        return null;
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
