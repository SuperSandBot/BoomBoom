package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import application.GameObject.*;
import application.GameObject.Object;
import application.GameObject.Block.blockTypes;
import application.GameObject.Item.itemTypes;
import application.Sound.GameSound;
import javafx.scene.canvas.GraphicsContext;

public class Level extends Object{
    
    public int worldWidth;
    public int worldHight;

    GameSound gs;
    
    GameHandler gh;
    Map map;
    Block[][] blocks;
    ArrayList<Player> playerlist;
    LinkedList<Boom> Booms = new LinkedList<Boom>();
    LinkedList<BoomSplase> boomSplases = new LinkedList<BoomSplase>();
    LinkedList<Item> items = new LinkedList<Item>();

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
        
        if(items.size() > 0)
        {
            for (Item item : items) {
                if(item != null) item.draw(gp);
            }
        }

        if(boomSplases.size() > 0)
        {
            for(BoomSplase splase : boomSplases) {
                splase.draw(gp);
            }
        }

        if(Booms.size() > 0)
        {
            for (Boom boom : Booms) {
                if(boom != null)  boom.draw(gp);
            }
        }
    
        for (Player player : playerlist) {
            player.draw(gp);
        }

        
    }

    public void update()
    {   

        if(!Booms.isEmpty())
        {
            for (Boom boom : Booms) {
                if(boom != null) boom.update();
            }
        }

        if(!items.isEmpty())
        {
            for (Item item : items) {
                if(item != null) item.update();
            }
        }

        for (Player player : playerlist) {
            player.update();
        }
        
    }

    public void Tic() {
        for (Boom boom : Booms) {
            boom.Tic();
        } 
    }

    public void RespawnPlayer(Player player)
    {
    	if(player.hideplayer) return;
    	player.hideplayer = true;
        gs.playAudio(GameSound.BOMBER_DIE);

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

        BackGroundExacutor.Scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                Block ramdomspawn = SpawnPoint.get(BackGroundExacutor.randomnum.nextInt(2));
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
        gs.playAudio(GameSound.BOMB);
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
        gs.playAudio(GameSound.BOMB_BANG);
        //Top
        BoomSplase[] splasesTop = new BoomSplase[boom.power + 1];
        splasesTop[0] = new BoomSplase(boom.pos,"top",false);
        splasesTop[0].setScreenX(boom.pos.getScreenX());
        splasesTop[0].setScreenY(boom.pos.getScreenY());
        boomSplases.add(splasesTop[0]);
        playerExplode(splasesTop[0].pos);
        for(int i = 1;i < splasesTop.length ; i++)
        {
            if(splasesTop[i-1].pos.top != null)
            {
                if(splasesTop[i-1].pos.top.bTypes == blockTypes.NONE)
                {
                    splasesTop[i] = new BoomSplase(splasesTop[i - 1].pos.top, "top", false);
                    splasesTop[i].setScreenX(splasesTop[i - 1].pos.top.getScreenX());
                    splasesTop[i].setScreenY(splasesTop[i - 1].pos.top.getScreenY());
                    boomSplases.add(splasesTop[i]);
                    if(i+1 >= splasesTop.length) splasesTop[i].End = true;

                    boomCheck(splasesTop[i].pos);
                    Item item = itemCheck(splasesTop[i].pos);
                    if(item != null) RemoveItem(item); 
                    playerExplode(splasesTop[i].pos);   
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
        splasesDown[0].setScreenX(boom.pos.getScreenX());
        splasesDown[0].setScreenY(boom.pos.getScreenY());
        boomSplases.add(splasesDown[0]);
        for(int i = 1 ;i < splasesDown.length ; i++)
        {
            if(splasesDown[i-1].pos.down != null)
            {
                if(splasesDown[i-1].pos.down.bTypes == blockTypes.NONE)
                {
                    splasesDown[i] = new BoomSplase(splasesDown[i-1].pos.down, "down", false);
                    splasesDown[i].setScreenX(splasesDown[i - 1].pos.down.getScreenX());
                    splasesDown[i].setScreenY(splasesDown[i - 1].pos.down.getScreenY());
                    boomSplases.add(splasesDown[i]);
                    if(i+1 >= splasesDown.length) splasesDown[i].End = true;
                    boomCheck(splasesDown[i].pos);
                    Item item = itemCheck(splasesDown[i].pos);
                    if(item != null) RemoveItem(item);   
                    playerExplode(splasesDown[i].pos);
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
        splasesLeft[0].setScreenX(boom.pos.getScreenX());
        splasesLeft[0].setScreenY(boom.pos.getScreenY());
        boomSplases.add(splasesLeft[0]);
        for(int i = 1;i < splasesLeft.length ; i++)
        {
            if(splasesLeft[i-1].pos.left != null)
            {
                if(splasesLeft[i-1].pos.left.bTypes == blockTypes.NONE)
                {
                    splasesLeft[i] = new BoomSplase(splasesLeft[i-1].pos.left, "left", false);
                    splasesLeft[i].setScreenX(splasesLeft[i - 1].pos.left.getScreenX());
                    splasesLeft[i].setScreenY(splasesLeft[i - 1].pos.left.getScreenY());
                    boomSplases.add(splasesLeft[i]);
                    if(i+1 >= splasesLeft.length) splasesLeft[i].End = true;
                    boomCheck(splasesLeft[i].pos);  
                    Item item = itemCheck(splasesLeft[i].pos);
                    if(item != null) RemoveItem(item);    
                    playerExplode(splasesLeft[i].pos);
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
        splasesRight[0].setScreenX(boom.pos.getScreenX());
        splasesRight[0].setScreenY(boom.pos.getScreenY());
        boomSplases.add(splasesRight[0]);
        for(int i = 1;i < splasesRight.length ; i++)
        {
            if(splasesRight[i-1].pos.right != null)
            {
                if(splasesRight[i-1].pos.right.bTypes == blockTypes.NONE)
                {
                    splasesRight[i] = new BoomSplase(splasesRight[i-1].pos.right, "right", false);
                    splasesRight[i].setScreenX(splasesRight[i - 1].pos.right.getScreenX());
                    splasesRight[i].setScreenY(splasesRight[i - 1].pos.right.getScreenY());
                    boomSplases.add(splasesRight[i]);
                    if(i+1 >= splasesRight.length) splasesRight[i].End = true;
                    boomCheck(splasesRight[i].pos);  
                    Item item = itemCheck(splasesRight[i].pos);
                    if(item != null) RemoveItem(item);    
                    playerExplode(splasesRight[i].pos);
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

    private void playerExplode(Block pos)
    {
        for (Player player : playerlist) {
            if(player.Pos == pos)
            {
                RespawnPlayer(player);
            }
        }
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
            if(block.bTypes == blockTypes.NONE)
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
            switch(BackGroundExacutor.randomnum.nextInt(7))
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
                case 7:
                    return itemTypes.POTION;
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
        if(block.bTypes == blockTypes.STONEBLOCK) return;
        if(block.bTypes != blockTypes.NONE)
        {
            block.bTypes = blockTypes.NONE;
            itemSpawn(RamdomItem(),block);
        }
    }

    public boolean playerTouchBlock(Player player, Block block)
    {
        if(player.playerHitBox.intersects(block.getScreenX() , block.getScreenY(), block.width +5, block.height+5))
        {
            for (Boom boom : Booms) {
                if(block.getScreenX() == boom.getScreenX() && block.getScreenY() == boom.getScreenY())  return false; 
            }  

            if(block.bTypes != blockTypes.NONE)
            {
                return false; 
            }
        }
        
        return true;
    }

    public boolean playerTouchMap(Player player)
    {

        if(player.playerHitBox.intersects(map.getScreenX() +32 , map.getScreenY() + 32, map.Width - 60, map.Hight-125))
        {  
            return true; 
        }
        return false;
    }

    public int distance(Player player, Block pos)
    {
        return (int) Math.sqrt(Math.pow( pos.getWorldX() - player.getWorldX(), 2) + Math.pow(pos.getWorldY() - player.getWorldY(), 2));
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
