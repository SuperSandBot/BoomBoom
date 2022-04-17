package Game.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoomSplase extends Object {
    
    public boolean End = false;
    public Block pos;
    Image imageBody;
    Image imageEnd;

    public BoomSplase(Block pos,String dir,boolean End)
    {
        super();
        this.pos = pos;
        this.End = End;
        
        setWorldX(pos.getWorldX());
        setWorldY(pos.getWorldY());

        switch(dir)
            {
                case "top":
                    imageEnd = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashEndTop.png"));
                    imageBody = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashBodyVertical.png"));
                    break;
                case "down":
                    imageEnd = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashEndDown.png"));
                    imageBody = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashBodyVertical.png"));
                    break;
                case "left":
                    imageEnd = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashEndLeft.png"));
                    imageBody = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashBodyHorizontal.png"));
                    break;
                case "right":  
                    imageEnd = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashEndRight.png"));
                    imageBody = new Image(getClass().getResourceAsStream("/Game/Asset/Boom/splashBodyHorizontal.png"));
                    break;
            }    
    }

    public void draw(GraphicsContext gp)
    {

        if(End)
        {
            gp.drawImage(imageEnd, getScreenX() , getScreenY());
        }
        else
        {
            gp.drawImage(imageBody, getScreenX() , getScreenY());
        }
       
    }

    public void update()
    {
        setScreenX(pos.getScreenX());
        setScreenY(pos.getScreenY());
    }
}
