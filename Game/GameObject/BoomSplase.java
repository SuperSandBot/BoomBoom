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
                    imageEnd = ImageManeger.getSplashEnd(dir);
                    imageBody = ImageManeger.getSplashBodyVertical();
                    break;
                case "down":
                    imageEnd = ImageManeger.getSplashEnd(dir);
                    imageBody = ImageManeger.getSplashBodyVertical();
                    break;
                case "left":
                    imageEnd = ImageManeger.getSplashEnd(dir);
                    imageBody = ImageManeger.getSplashBodyHorizontal();
                    break;
                case "right":  
                    imageEnd = ImageManeger.getSplashEnd(dir);
                    imageBody = ImageManeger.getSplashBodyHorizontal();
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
}
