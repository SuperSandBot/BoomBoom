package Game.GameObject;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Object
{
    public int speed = 3;

    ArrayList<Image> playerwalk = new ArrayList<Image>();;

    public Player()
    {
        super();

        playerwalk.add(new Image(getClass().getResourceAsStream("/Game/Asset/girlwalk_01.png")));
    }

    public void draw(GraphicsContext gp) 
    {
        gp.drawImage(playerwalk.get(0), getScreenX() - playerwalk.get(0).getWidth()/2, getScreenY() - playerwalk.get(0).getHeight()/2);
    }

    public void update() 
    {

    }
}
