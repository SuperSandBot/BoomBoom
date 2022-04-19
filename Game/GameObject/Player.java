package Game.GameObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Game.BackGroundExacutor;
import Game.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Object
{
    public int score = 0;
    private int boomlevel = 1;
    private int powerlevel = 1;
    public int bootlevel = 1;
    private int currentboom;
    public Block Pos;
    public Level level;
    public boolean hideplayer = false;
    public boolean oncrack = false;

    public ArrayList<Item> playerItem = new ArrayList<Item>();
    ArrayList<Image> playerwalk = new ArrayList<Image>();

    public Player()
    {
        super();
        playerwalk.add(new Image(getClass().getResourceAsStream("/Game/Asset/64x64.png")));

        currentboom = boomlevel;
    }

    public void draw(GraphicsContext gp) 
    {
        if(hideplayer != true)
        {
            gp.drawImage(playerwalk.get(0), getScreenX() - playerwalk.get(0).getWidth()/2, getScreenY() - playerwalk.get(0).getHeight()/2);
        }
    }

    public void update() 
    {
        
    }

    public int getSpeed()
    {
        
        switch(bootlevel)
        {
            case 1:
                return 16;
            case 2:
                return 8;
            default:
                case 3:
                return 4;
        }
    }

    public int getpower()
    {
        return powerlevel;
    }

    public void playerPlantBoom()
    {
        if(currentboom <= 0) return;
        if(level.checkBoomPos(Pos)) return;

        currentboom--;
        BackGroundExacutor.Scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                
                if(currentboom < boomlevel) currentboom++;
            }
        }, 2500, TimeUnit.MILLISECONDS);

        level.playerPlantBoom(Pos,getpower());
    
    }

    public void playerRemoveItem(Item item)
    {
        playerItem.remove(item);
        switch(item.iTypes)
        {
            case GOLDCOIN:
                score--;
                break;
            case BOOMUPGRADE:
                if(boomlevel >= 1)
                {
                    boomlevel--;
                }
                break;
            case BOOT:
                bootlevel--;
                break;
            case ENERGYDRINK:
                break;
            case VILE:
                powerlevel--;
                break;
            default:
            break;
        }
    }

    public void playerPickItem(Item item)
    {
        playerItem.add(item);
        switch(item.iTypes)
        {
            case GOLDCOIN:
                score++;
                break;
            case BOOMUPGRADE:
                if(boomlevel < 6)
                {
                    boomlevel++;
                    currentboom++;
                }
                break;
            case BOOT:
                bootlevel++;
                break;
            case ENERGYDRINK:
                if(oncrack == false)
                {
                    bootlevel += 2;
                    powerlevel += 2;
                    BackGroundExacutor.Scheduler.schedule(new Runnable() {

                        @Override
                        public void run() {
                            
                            oncrack = true;
                            bootlevel -= 2;
                            powerlevel -= 2;
                        }
                    }, 5000, TimeUnit.MILLISECONDS);
                }
                break;
            case VILE:
                powerlevel++;
                break;
            default:
            break;
        }
    }
}
