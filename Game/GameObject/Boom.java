package Game.GameObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Boom extends Object{

    ArrayList<Image> boomImage = new ArrayList<Image>();
    public int power = 1;
    public int tic = 0;
    public Block block;

    public Boom(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom00.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom01.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom02.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom03.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom04.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom05.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom06.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/Game/Asset/Boom/boom07.png")));
        
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
        
    }

    public void boomSetup()
    {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                tic++;
            }
        }, 1000);

    }

    public void boomExplode()
    {

    }

}
