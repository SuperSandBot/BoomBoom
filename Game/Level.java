package Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Game.GameObject.*;
import Game.GameObject.Object;
import Game.GameObject.Block.blockTypes;
import Game.GameObject.Item.itemTypes;
import Game.Sound.GameSound;
import javafx.scene.canvas.GraphicsContext;

public class Level extends Object{
    
    public int worldWidth;
    public int worldHight;

    GameSound gs = new GameSound();
    
    GameHandler gh;
    Map map;
    Block[][] blocks;
    ArrayList<Player> playerlist;
    ArrayList<Boom> Booms = new ArrayList<Boom>();
    ArrayList<BoomSplase> boomSplases = new ArrayList<BoomSplase>();
    ArrayList<Item> items = new ArrayList<Item>();

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
        
        for (Item item : items) {
            if(item != null) item.draw(gp);
        }

        if(boomSplases.size() > 0)
        {
            for(BoomSplase splase : boomSplases)
            {
                splase.draw(gp);
            }
        }

        for (Boom boom : Booms) {
            if(boom != null)  boom.draw(gp);
        }
  
        for (Player player : playerlist) {
            player.draw(gp);
        }


    }

    public void update()
    {   

        for (Player player : playerlist) {
            player.update();
        }

        // xác định vị trí block trên màng hình
        int x = 0;
        int y = 0;

        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j].setScreenX(this.getScreenX() + x + 96);
                blocks[i][j].setScreenY(this.getScreenY() + y + 64);
                blocks[i][j].update();
                y += 64;
            }
            x += 64;
            y = 0;
        }

        if(!Booms.isEmpty())
        {
            for (Boom boom : Booms) {
                if(boom != null) boom.update();
            }
        }

        if(!boomSplases.isEmpty())
        {
            for (BoomSplase splase : boomSplases)
            {
                if(splase != null)
                splase.update();
            }
        }

        for (Item item : items) {
            if(item != null)
            item.update();
        }

        //this.setScreenX(getWorldX() - playerlist.get(0).getWorldX() + playerlist.get(0).getScreenX());
        //this.setScreenY(getWorldY() - playerlist.get(0).getWorldY() + playerlist.get(0).getScreenY());
    }

    public void Tic() {
        for (Boom boom : Booms) {
            boom.Tic();
        } 
    }

    public void RespawnPlayer(Player player)
    {
        gs.Audio(GameSound.BOMBER_DIE);

        if(player.playerItem.isEmpty() == false)
        {
            for(int i = 0; i < 3 ; i++)
            {    
                if(player.playerItem.isEmpty()) break;
                Item item = player.playerItem.get(0);
                itemSpawn(item.iTypes, randomBlock());
                player.playerRemoveItem(item);
                
                
            }
        }
        player.hideplayer = true;

        BackGroundExacutor.Scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                Block ramdomspawn = SpawnPoint.get(BackGroundExacutor.randomnum.nextInt(2));
                System.out.println(ramdomspawn);
                player.setWorldX(ramdomspawn.getWorldX());
                player.setWorldY(ramdomspawn.getWorldY());
                player.setScreenX(ramdomspawn.getScreenX());
                player.setScreenY(ramdomspawn.getScreenY());
                player.Pos = ramdomspawn;
                player.hideplayer = false;
            }
        }, 5, TimeUnit.SECONDS);
    }

    public void playerPlantBoom(Block block,int power)
    {
        gs.Audio(GameSound.BOMB);
        Boom boom = new Boom(block.getWorldX(), block.getWorldY(), block.getScreenX(), block.getScreenY());
        boom.pos = block;
        boom.power = power;
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
        gs.Audio(GameSound.BOMB_BANG);
        //Top
        BoomSplase[] splasesTop = new BoomSplase[boom.power + 1];
        splasesTop[0] = new BoomSplase(boom.pos,"top",false);
        boomSplases.add(splasesTop[0]);
        if( playerCheck(splasesTop[0].pos) != null) RespawnPlayer(playerCheck(splasesTop[0].pos)); 
        for(int i = 1;i < splasesTop.length ; i++)
        {
            if(splasesTop[i-1].pos.top != null)
            {
                if(splasesTop[i-1].pos.top.bType == blockTypes.NONE)
                {
                    splasesTop[i] = new BoomSplase(splasesTop[i - 1].pos.top, "top", false);
                    boomSplases.add(splasesTop[i]);
                    if(i+1 >= splasesTop.length) splasesTop[i].End = true;

                    boomCheck(splasesTop[i].pos);
                    Item item = itemCheck(splasesTop[i].pos);
                    if(item != null) RemoveItem(item); 
                    Player player = playerCheck(splasesTop[i].pos);   
                    if(player != null) RespawnPlayer(player);   
                }
                else
                {
                    splasesTop[i-1].End = true;
                    BlockCheck(splasesTop[i-1].pos.top);
                    break;
                }
            }
            else
            {
                
                splasesTop[i-1].End = true;
                Item item = itemCheck(splasesTop[i-1].pos);
                if(item != null) RemoveItem(item);   
                break;
            }
        }  
        
        //Down
        BoomSplase[] splasesDown = new BoomSplase[boom.power + 1];
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
                    boomCheck(splasesDown[i].pos);
                    Item item = itemCheck(splasesDown[i].pos);
                    if(item != null) RemoveItem(item);   
                    Player player = playerCheck(splasesDown[i].pos);    
                    if(player != null) RespawnPlayer(player);    
                }
                else
                {
                    splasesDown[i-1].End = true;
                    BlockCheck(splasesDown[i-1].pos.down);
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
        BoomSplase[] splasesLeft = new BoomSplase[boom.power + 1];
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
                    boomCheck(splasesLeft[i].pos); 
                    Item item = itemCheck(splasesLeft[i].pos);
                    if(item != null) RemoveItem(item);    
                    Player player = playerCheck(splasesLeft[i].pos);    
                    if(player != null) RespawnPlayer(player);  
                }
                else
                {
                    splasesLeft[i-1].End = true;
                    BlockCheck(splasesLeft[i-1].pos.left);
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
        BoomSplase[] splasesRight = new BoomSplase[boom.power + 1];
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
                    boomCheck(splasesRight[i].pos);  
                    Item item = itemCheck(splasesRight[i].pos);
                    if(item != null) RemoveItem(item);    
                    Player player = playerCheck(splasesRight[i].pos);    
                    if(player != null) RespawnPlayer(player);     
                }
                else
                {
                    splasesRight[i-1].End = true;
                    BlockCheck(splasesRight[i-1].pos.right);
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

    private void itemSpawn(itemTypes iTypes,Block pos)
    {
        if(iTypes != null)
        {
            Item item = new Item();
            item.setWorldX(pos.getWorldX());
            item.setWorldY(pos.getWorldY());
            item.setBType(iTypes);
            item.pos = pos;
            items.add(item);
        }
    }
    
    public Block randomBlock()
    {
        boolean checking = true;
        Block block;
        while(checking)
        {
            block = blocks[BackGroundExacutor.randomnum.nextInt(blocks.length)][BackGroundExacutor.randomnum.nextInt(blocks[0].length)];
            if(block.bType == blockTypes.NONE)
            {
                if(itemCheck(block) == null)
                {
                    checking = false;
                    return block;
                   
                }
            }
        }

        return null;
    }
    
    public itemTypes RamdomItem()
    {
        if(BackGroundExacutor.randomnum.nextInt(10) < 6)
        {
            switch(BackGroundExacutor.randomnum.nextInt(8))
            {
                case 0:
                case 1:
                    return itemTypes.GOLDCOIN;
                case 2:
                case 3:
                    return itemTypes.BOOMUPGRADE;
                case 4:
                case 5:
                    return itemTypes.BOOT;
                case 6:
                    return itemTypes.ENERGYDRINK;
                case 7:
                case 8:
                    return itemTypes.VILE;
                default:
                    break;
            }
        }
        return null;
    }
    
    public void RemoveItem(Item item)
    {
        item.itemImage = null;
        item.itemShadow = null;
        item.pos = null;
        items.remove(item);
    }
    
    public Item itemCheck(Block block)
    {
        if(items.isEmpty()) return null;
        for (Item item : items) {
            
            if(item.pos == block)
            {
                return item;
            }
        }
        return null;
    }
    
    private void BlockCheck(Block block)
    {
        if(block.bType == blockTypes.STONEBLOCK) return;
        if(block.bType != blockTypes.NONE)
        {
            block.bType = blockTypes.NONE;
            itemSpawn(RamdomItem(),block);
        }
    }

    private void boomCheck(Block pos)
    {
        for (Boom boom : Booms) {
            if(boom.pos == pos)
            {
                if(boom.explode != true) boom.boomExplode();
            }
        }
    }

    private Player playerCheck(Block pos)
    {
        for(Player player : playerlist)
        {
            if(player.Pos == pos && player.hideplayer == false) return player;
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
                blocks[i][j].setWorldX(this.getWorldX() + x + 128);
                blocks[i][j].setWorldY(this.getWorldY() + y + 96);
                blocks[i][j].setScreenX(this.getScreenX() + x + 96);
                blocks[i][j].setScreenY(this.getScreenY() + y + 64);

                // xac dinh the loai
                switch(blockMap[i][j])
                {
                    case 1:
                        blocks[i][j].setBType(blockTypes.DIRTBLOCK00);
                        break;
                    case 2:
                        blocks[i][j].setBType(blockTypes.DIRTBLOCK01);
                        break;
                    case 3:
                        blocks[i][j].setBType(blockTypes.GRASSBLOCK);
                        break;
                    case 4:
                        blocks[i][j].setBType(blockTypes.STONEBLOCK);
                        break;
                    default:
                    case 0:
                        blocks[i][j].setBType(blockTypes.NONE);
                        break;
                }
                

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

        SpawnPoint.add(blocks[0][map.Col /2]);
        SpawnPoint.add(blocks[map.Row -1][map.Col /2]);

        //add player1      
        Player player1 = new Player(0);
        player1.setWorldX(SpawnPoint.get(0).getWorldX());
        player1.setWorldY(SpawnPoint.get(0).getWorldY());
        player1.setScreenX(SpawnPoint.get(0).getScreenX());
        player1.setScreenY(SpawnPoint.get(0).getScreenY());
        player1.Pos = SpawnPoint.get(0);
        player1.level = this;
        playerlist.add(player1); 

        //add player2      
        Player player2 = new Player(1);
        player2.setWorldX(SpawnPoint.get(1).getWorldX());
        player2.setWorldY(SpawnPoint.get(1).getWorldY());
        player2.setScreenX(SpawnPoint.get(1).getScreenX());
        player2.setScreenY(SpawnPoint.get(1).getScreenY());
        player2.Pos = SpawnPoint.get(1);
        player2.level = this;
        playerlist.add(player2);

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
