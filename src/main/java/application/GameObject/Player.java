package application.GameObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import application.BackGroundExacutor;
import application.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

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
    public int team;
    public int facing = 0;
    public int width,height;
    public Rectangle playerHitBox;

    public ArrayList<Item> playerItem = new ArrayList<Item>();
    ArrayList<Image> playerblue = new ArrayList<Image>();
    ArrayList<Image> playerred = new ArrayList<Image>();

    public Player(int team)
    {
        super();
        this.team = team;
        playerblue.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerup_blue.png")));
        playerblue.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerdown_blue.png")));
        playerblue.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerleft_blue.png")));
        playerblue.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerright_blue.png")));

        playerred.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerup_red.png")));
        playerred.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerdown_red.png")));
        playerred.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerleft_red.png")));
        playerred.add(new Image(getClass().getResourceAsStream("/application/Asset/Player/playerright_red.png")));

        currentboom = boomlevel;

        width = (int)playerblue.get(0).getWidth();
        height = (int) playerblue.get(0).getHeight();
        playerHitBox = new Rectangle();
        
    }

    public void update()
    {
        playerHitBox.setX(getScreenX() +20);
        playerHitBox.setY(getScreenY() + height - 50);
        playerHitBox.setWidth(width -20);
        playerHitBox.setHeight(45);
    }

    public void draw(GraphicsContext gp) 
    {
        if(hideplayer != true)
        {
            if(team == 0)
            {
                switch(facing)
                {
                    case 0:
                        gp.drawImage(playerblue.get(0), getScreenX()+ 10, getScreenY()-12);
                        break;
                    case 1:
                        gp.drawImage(playerblue.get(1), getScreenX()+ 10, getScreenY()-12);
                        break;
                    case 2:
                        gp.drawImage(playerblue.get(2), getScreenX()+ 10, getScreenY()-12);
                        break;
                    case 3:
                        gp.drawImage(playerblue.get(3), getScreenX()+ 10, getScreenY()-12);
                        break;
                }
            }
            else
            {
                switch(facing)
                {
                    case 0:
                        gp.drawImage(playerred.get(0), getScreenX() + 10, getScreenY()- 12);
                        break;
                    case 1:
                        gp.drawImage(playerred.get(1), getScreenX()+ 10, getScreenY()- 12);
                        break;
                    case 2:
                        gp.drawImage(playerred.get(2), getScreenX()+ 10, getScreenY()- 12);
                        break;
                    case 3:
                        gp.drawImage(playerred.get(3), getScreenX()+ 10, getScreenY()- 12);
                        break;
                }
            }
        }

        if(!hideplayer)
        {
            gp.setFont(new Font(20));

            if(team == 0)
            {
                gp.strokeText("Player1", getScreenX(), getScreenY() -5);
                gp.fillText("Player1", getScreenX(), getScreenY() -5);
            }
            else
            {
                gp.strokeText("Player2", getScreenX(), getScreenY() -5);
                gp.fillText("Player2", getScreenX(), getScreenY() -5);
            }
        }
    }

    public int getSpeed()
    {
        
        switch(bootlevel)
        {
            case 1:
                return 6;
            case 2:
                return 8;
            case 3:
                return 9;
            default:
                case 4:
                return 10;
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
        }, 3700, TimeUnit.MILLISECONDS);

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
            case POTION:
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
            case POTION:
                powerlevel++;
                break;
            default:
            break;
        }
    }
}
