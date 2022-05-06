package Game.GameObject;

import java.util.ArrayList;
import Game.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Boom extends Object{

    ArrayList<Image> boomImage = new ArrayList<Image>();
    public Level level;
    public int power = 1;
    public int tic = 0;
    public int expoldeTic = 0;
    public Block pos;
    public boolean explode = false;
    public int width,height;

    public Boom(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

        boomImage = ImageManeger.getBoomImage();
    }

    public void draw(GraphicsContext gp)
    {
        if(boomImage.get(tic) != null)
        {
            gp.drawImage(boomImage.get(tic), getScreenX() -16 , getScreenY() - 16);
        }
       
    }

    public void update()
    {
        setScreenX(pos.getScreenX());
        setScreenY(pos.getScreenY());
    }

    public void Tic() {

        if(expoldeTic < 3)
        {
            expoldeTic++;
        }
        else
        {
            boomExplode();
            
        }  

        if(explode == false)
        {
            switch(expoldeTic)
            {
                case 1:
                case 2:
                    if(tic < 3)
                    {
                        tic++;
                    } 
                    else
                    {
                        tic = 1;
                    }
                    break;
                case 3:
                    if(tic == 7)
                    {
                        boomExplode();
                        break;
                    }
                    tic++;
                    break;
            }
        }
    }

    public void boomExplode()
    {
        explode = true;
        tic = 7;
        level.boomExplode(this);
    }

   
}
