package Game.GameObject;

import java.util.ArrayList;

import Game.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Object
{
    public int power = 1;
    public int speed = 16;
    public Block Pos;
    public Level level;

    ArrayList<Image> playerwalk = new ArrayList<Image>();

    public Player()
    {
        super();
        playerwalk.add(new Image(getClass().getResourceAsStream("/Game/Asset/64x64.png")));
    }

    public void draw(GraphicsContext gp) 
    {
        gp.drawImage(playerwalk.get(0), getScreenX() - playerwalk.get(0).getWidth()/2, getScreenY() - playerwalk.get(0).getHeight()/2);
    }

    public void update() 
    {

    }

    public void playerPlantBoom()
    {
        if(level.checkBoomPos(Pos))
        {
            return;
        }
        else
        {
            level.playerPlantBoom(this,Pos);
        }
    }
}
